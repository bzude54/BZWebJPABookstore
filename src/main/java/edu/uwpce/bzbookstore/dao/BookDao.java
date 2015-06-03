package edu.uwpce.bzbookstore.dao;


import edu.uwpce.bzbookstore.model.Author;
import edu.uwpce.bzbookstore.model.Book;
import edu.uwpce.bzbookstore.model.BookSearchForm;
import edu.uwpce.bzbookstore.model.Genre;

import java.util.List;


public interface BookDao {
  
  void createBook(Book book);

  Book findByISBN(String isbn);

  Book findById(Integer id);

  Book updateBook(Book book);

  boolean deleteBook(Book book);

  void refreshAllBooks();

  List<Book> findByGenre(Genre genre);

  List<Book> findAll();

  int countBooks();

  List<Object[]> findGenreBookCounts();

  List<Book> searchBooksByExample(Book examplebook);

  List<Genre> searchGenres(Genre genre);

  List<Book> searchBooksByCriteria(BookSearchForm searchbook);

  List<Author> getAuthorsByBook(String booktitle);

  List<Book> getBooksByAuthor(String lastname, String firstname);

  List<Author> addAuthorToBook(Author newauthor, String booktitle);

  org.hibernate.stat.Statistics getHibernateStatistics();



  }