package edu.uwpce.bzbookstore.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import edu.uwpce.bzbookstore.service.BookService;
import edu.uwpce.bzbookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiBookController {

    @Autowired
    private BookService bookService;
    
    
    @RequestMapping(value="/books", method=RequestMethod.GET)
    public List<Book> getBooks() {
        return bookService.findAllBooks();
    }

    @RequestMapping(value="/books/{isbn}", method=RequestMethod.GET)
    public Object getBook(@PathVariable("isbn") String isbn) {
        Book book = bookService.findBookByIsbn(isbn);
        if (book != null) {
            return book;
        } else {
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Book with ISBN=" + isbn + " does not exist.");
        }
    }
    
    @RequestMapping(value="/books", method=RequestMethod.POST)
    public Object createBook(@RequestBody Book book, HttpServletResponse response) {
        if (bookService.findBookByIsbn(book.getIsbn()) != null) {
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Book with ISBN=" + book.getIsbn() + " already exists.");
        }
        bookService.updateBook(book);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return bookService.findBookByIsbn(book.getIsbn());
    }
    
    @RequestMapping(value="/books/{isbn}", method=RequestMethod.PUT)
    public Book updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return bookService.findBookByIsbn(book.getIsbn());
    }
    
    @RequestMapping(value="/books/{isbn}", method=RequestMethod.DELETE)
    public Object deleteBook(@PathVariable("isbn") String isbn,
            HttpServletResponse response) {
        Book deletebook = bookService.findBookByIsbn(isbn);
        if (bookService.deleteBook(deletebook)) {
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return new ApiMessage(ApiMessage.MsgType.INFO, "Book with ISBN=" + isbn + " has been removed.");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Book with ISBN=" + isbn + " was not found.");
        }
    }   
    
  
}
