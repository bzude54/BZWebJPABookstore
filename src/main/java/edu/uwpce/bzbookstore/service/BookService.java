package edu.uwpce.bzbookstore.service;


import edu.uwpce.bzbookstore.model.Author;
import edu.uwpce.bzbookstore.model.Book;
import edu.uwpce.bzbookstore.model.Genre;
import edu.uwpce.bzbookstore.model.Review;
import org.hibernate.stat.Statistics;

import java.util.List;

/**
 * Created by bzude on 5/16/15.
 */
public interface BookService {
     // Cached Entity
    Book findBookById(Integer id);


    // Cached Entity
    Book findBookByIsbn(String isbn);

    //query cache example
    List<Book> findAllBooks();

    //query cache lab
    List<Book> findAllBooksInGenre(Genre genre);

    //query cache lab
    List<Book> findAllBooksTitleSearch(String titlesearch);

    // namedQuery cache example
    List<Book> findAllBooks_named_query_cache();


    List<Book> getBooksByAuthor(String authorLastName, String authorFirstName);

    Book updateBook(Book bookupdate);

    boolean deleteBook(Book bookdelete);

    void refreshBooks();

    List<Review> findReviewsByIsbn(String isbn);

    List<Review> addReviewToIsbn(String isbn, Review review);

    Statistics getHibernateStatistics();

/*
    List<Author> findAllAuthors();

    List<Author> findAuthorsByLastName(String lastName);
*/


}
