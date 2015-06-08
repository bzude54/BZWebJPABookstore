package edu.uwpce.bzbookstore.dao;

import edu.uwpce.bzbookstore.model.*;

import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Repository("bookDao")
public class JPABookDao extends AbstractBookDao implements BookDao {

  static final Logger log = LoggerFactory.getLogger(JPABookDao.class);

  @PersistenceContext
  private EntityManager em;
  

  public JPABookDao() {}


  @Override
  public void createBook(Book book) {
    if (book.getId() == null)  { //insert
      em.persist(book);
    } else { //update
      em.merge(book);
    }
    em.flush();
    log.info("saved new book: "+ book.getId());
  }

 
    
  @Override
  public Book findById(Integer id) {
    return em.find(Book.class, id);
  }
  

  @SuppressWarnings("unchecked")
  @Override
  public Book findByISBN(String isbn) {
	List<Book> list = (List<Book>)em.createQuery(
	            "SELECT b FROM Book b WHERE b.isbn LIKE :isbn")
	            .setParameter("isbn", isbn)
                .setHint("org.hibernate.cacheable", true) //enable cache
	            .getResultList();
	if (!list.isEmpty()) {
		return list.get(0);
	} else {
		return null;
	}
  }

  
  
  
  
  @Override
  public Book updateBook(Book book) {
	    if (book.getId() != null)  { //update
	        em.merge(book);
	      } else { //insert
	        em.persist(book);
	      }
	      em.flush();
	      log.info("updated book: "+ book.getId());

      return (Book)em.find(Book.class, book.getId());
  }


  @Override
  public boolean deleteBook(Book book) {
	  	boolean deleteSuccess = false;
        Book targetBook = em.find(Book.class, book.getId());
	    if ((book.getId() != null) && (targetBook != null))  {
            em.remove(targetBook);
		    em.flush();
		    log.info("deleted book: " + book.getId());
	        return deleteSuccess = true;
	    } 
/*	    em.flush();
	    log.info("deleted book: "+ book.getId());
*/	    
	    return deleteSuccess;
 }
  

    @Override
    public void refreshAllBooks(){
        this.findAll();
    }


  @Override
  public List<Book> findAll() {
      return em.createQuery("FROM Book")
              .setHint("org.hibernate.cacheable", true) //enable cache
              .getResultList();  // also " SELECT u FROM User u"""
  }


 @SuppressWarnings("unchecked")
 @Override
 @Cacheable("booksByGenre")
 public List<Book> findByGenre(Genre genre) {
	    return (List<Book>)em.createQuery(
	            "SELECT b FROM Book b WHERE b.genre = :genre", Book.class)
	            .setParameter("genre", genre)
	            .getResultList();
  }  
  

  @Override
  public int countBooks() {
	List<Book> books = this.findAll();
	if (books != null) {
		return books.size();
	} else {
		return 0;
	}
  }

    @Override
    public List<Object[]> findGenreBookCounts() {
        return em.createQuery("SELECT b.genre.name, COUNT(b.genre.name) FROM Book b GROUP BY b.genre.name HAVING COUNT(b.genre.name) > 0 ORDER BY COUNT(b.genre.name) DESC")
                .getResultList();

    }

    @Override
    public List<Book> searchBooksByExample(Book book) {
        Session session = (Session) em.getDelegate();

        Example bookexample = Example.create(book).enableLike(MatchMode.ANYWHERE).ignoreCase();

        Criteria criteria = session.createCriteria(Book.class).add(bookexample);
        List<Book> books = (List<Book>)criteria.list();
        return books;
    }


    @Override
    public List<Genre> searchGenres(Genre genre) {
        Session session = (Session) em.getDelegate();

        Example examplegenre = Example.create(genre);

        Criteria criteria = session.createCriteria(Genre.class).add(examplegenre);
        List<Genre> genres = (List<Genre>)criteria.list();
        return genres;
    }



    @Override
    public List<Book> searchBooksByCriteria(BookSearchForm searchbook) {
//        String isbn = searchbook.getIsbn();
//        String genre = searchbook.getGenre();
//        String title = "%" + searchbook.getTitle().toLowerCase() + "%";
//        String description = "%" + searchbook.getDescription().toLowerCase() + "%";
        BigDecimal lowprice = searchbook.getLowPrice();
        BigDecimal highprice = searchbook.getHighPrice();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        Root<Book> book = cq.from(Book.class);
        cq.select(book);

        Predicate where = cb.conjunction();


        if (lowprice !=  null && highprice != null && (lowprice.doubleValue() > 0.0) && (highprice.compareTo(lowprice) > 0)) {
            where = cb.between(book.<BigDecimal>get("price"), lowprice, highprice);
        }


        if (StringUtils.isNotBlank(searchbook.getIsbn())) {
            String isbn = "%" + searchbook.getIsbn() + "%";
            where = cb.and(where, cb.like(book.<String>get("isbn"), isbn));
        }


        if (StringUtils.isNotBlank(searchbook.getGenre())) {
            String genre = "%" + searchbook.getGenre().toLowerCase() + "%";
            where = cb.and(where, cb.like(book.<String>get("genre").get("name"), genre));
        }



        if (StringUtils.isNotBlank(searchbook.getTitle())) {
            String title = "%" + searchbook.getTitle().toLowerCase() + "%";
            where = cb.and(where, cb.like(cb.lower(book.<String>get("title")), title));
        }



        if (StringUtils.isNotBlank(searchbook.getDescription())) {
            String description = "%" + searchbook.getDescription().toLowerCase() + "%";
            where = cb.and(where, cb.like(cb.lower(book.<String>get("description")), description));
        }


        cq.where(where);

        TypedQuery<Book> query = em.createQuery(cq);
        query.setHint("org.hibernate.cacheable", true); //enable cache
        return query.getResultList();

    }



/*
    @Override
    public List<Author> getAuthorsByBook(String title) {
        List<Author> authorlist = (List<Author>)em.createQuery(
                        "SELECT a FROM Author a, Book b WHERE b.authors.id = a.id AND b.title LIKE :title", Author.class)
                .setParameter("title", title)
                .getResultList();
        return authorlist;
    }
*/


    @Override
    public List<Author> getAuthorsByBook(String booktitle) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Author> cq = cb.createQuery(Author.class);

        Metamodel m = em.getMetamodel();
        EntityType<Author> Author_ = m.entity(Author.class);

        Root<Author> author = cq.from(Author_);
        Join<Author, Book> book = author.join(Author_.getSet("books", Book.class));

        cq.select(author);

        Predicate where = cb.conjunction();

        if (StringUtils.isNotBlank(booktitle)) {
            String title = "%" + booktitle.toLowerCase() + "%";
            where = cb.and(where, cb.like(cb.lower(book.<String>get("title")), title));
        }

        cq.where(where);

        TypedQuery<Author> query = em.createQuery(cq);
        return query.getResultList();

    }


    @Override
    public List<Book> getBooksByAuthor(String lastname, String firstname) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        Metamodel m = em.getMetamodel();
        EntityType<Book> Book_ = m.entity(Book.class);

        Root<Book> book = cq.from(Book_);
        Join<Book, Author> author = book.join(Book_.getSet("authors", Author.class));

        cq.select(book);

        Predicate where = cb.conjunction();

        if (StringUtils.isNotBlank(lastname)) {
            String querylastname = "%" + lastname.toLowerCase() + "%";
            where = cb.and(where, cb.like(cb.lower(author.<String>get("lastName")), querylastname));
        }

        if (StringUtils.isNotBlank(firstname)) {
            String queryfirstname = "%" + firstname.toLowerCase() + "%";
            where = cb.and(where, cb.like(cb.lower(author.<String>get("firstName")), queryfirstname));
        }


        cq.where(where);

        TypedQuery<Book> query = em.createQuery(cq);
        query.setHint("org.hibernate.cacheable", true); //enable cache
        return query.getResultList();

    }

    @Override
    public List<Author> addAuthorToBook(Author newauthor, String booktitle) {
        List<Author> newauthorlist = null;
        Book targetbook = null;
        BookSearchForm targetbookform = new BookSearchForm();
        targetbookform.setTitle(booktitle);
        List<Book> targetbooklist = this.searchBooksByCriteria(targetbookform);
        if (targetbooklist.size() == 1){
            targetbook = targetbooklist.get(0);
            targetbook.addAuthor(newauthor);
            em.merge(targetbook);
        }
        return this.getAuthorsByBook(booktitle);
    }


    public List<Review> findAllReviews(String isbn) {
        List<Review> reviewlist = (List<Review>)em.createQuery(
                "SELECT r FROM Review r WHERE r.bookIsbn LIKE :isbn ORDER BY r.timeStamp DESC")
                .setParameter("isbn", isbn)
                .setHint("org.hibernate.cacheable", true) //enable cache
                .getResultList();
         return reviewlist;
    }


    public List<Review> addReview(String isbn, Review review) {
        return null;
    }



    public   org.hibernate.stat.Statistics getHibernateStatistics() {
        Session session  = (Session)em.getDelegate();
        SessionFactory sessionFactory = session.getSessionFactory();
        Statistics stats = sessionFactory.getStatistics();
        System.out.println("Hibernate Cache Stats ->" + stats);


        String regions[] = stats.getSecondLevelCacheRegionNames();

        for(String regionName:regions) {
            SecondLevelCacheStatistics stat2 = stats.getSecondLevelCacheStatistics(regionName);
            log.info("2nd Level Cache(" +regionName+") Put Count: "+stat2.getPutCount());
            log.info("2nd Level Cache(" +regionName+") HIt Count: "+stat2.getHitCount());
            log.info("2nd Level Cache(" +regionName+") Miss Count: "+stat2.getMissCount());
        }
        return stats;
    }

    public   void  printEhcacheStatistics() {
        CacheManager cacheManager = CacheManager.getInstance();
        String[] cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            net.sf.ehcache.Statistics statistics = cacheManager.getCache(cacheName).getStatistics();
            log.info(cacheName + " - " + statistics.toString());
        }
    }

}
