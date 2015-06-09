package edu.uwpce.bzbookstore.dao;

import edu.uwpce.bzbookstore.model.*;


import java.util.List;

public class AbstractBookDao implements BookDao {


	public void createBook(Book book) {	throw new UnsupportedOperationException();	}

	public Book findByISBN(String isbn) { throw new UnsupportedOperationException(); }

	public Book updateBook(Book book)  { throw new UnsupportedOperationException(); }

	public boolean deleteBook(Book book) { throw new UnsupportedOperationException(); }

	public void refreshAllBooks() { throw new UnsupportedOperationException(); }

	public List<Book> findByGenre(Genre genre)  { throw new UnsupportedOperationException(); }

	public List<Book> findAll() { throw new UnsupportedOperationException(); }

	public int countBooks() { throw new UnsupportedOperationException(); }

	public List<Object[]> findGenreBookCounts() { throw new UnsupportedOperationException(); }

	public List<Book> searchBooksByExample(Book examplebook)  { throw new UnsupportedOperationException(); }


	public List<Genre> searchGenres(Genre genre) { throw new UnsupportedOperationException(); }

	public List<Book> searchBooksByCriteria(BookSearchForm searchbook) { throw new UnsupportedOperationException(); }

	public List<Author> getAuthorsByBook(String booktitle) { throw new UnsupportedOperationException(); }

	public List<Book> getBooksByAuthor(String lastname, String firstname) { throw new UnsupportedOperationException(); }


	public List<Author> addAuthorToBook(Author newauthor, String booktitle) { throw new UnsupportedOperationException(); }

	public Book findById(Integer id) { throw new UnsupportedOperationException(); }

	public List<Review> findAllReviews(String isbn) { throw new UnsupportedOperationException(); }

	public List<Review> findAllReviews(Integer bookid) { throw new UnsupportedOperationException(); }

	public List<Review> addReview(Review review) {
		return null;
	}

	public org.hibernate.stat.Statistics getHibernateStatistics() { throw new UnsupportedOperationException(); }

}
