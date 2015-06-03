package edu.uwpce.bzbookstore.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BZApiBooksController {

    @Autowired
    private BZBooksManager booksManager;
    
    
    @RequestMapping(value="/books", method=RequestMethod.GET)
    public Map<String, BZBook> getBooks() {
        return booksManager.getBooks();
    }

    @RequestMapping(value="/books/{isbn}", method=RequestMethod.GET)
    public Object getBook(@PathVariable("isbn") String isbn) {
        BZBook book = booksManager.getSingleBook(isbn);
        if (book != null) {
            return book;
        } else {
            return new BZApiMessage(BZApiMessage.MsgType.ERROR, "Book with ISBN=" + isbn + " does not exist.");
        }
    }
    
    @RequestMapping(value="/books", method=RequestMethod.POST)
    public Object createBook(@RequestBody BZBook book, HttpServletResponse response) {
        if (booksManager.getSingleBook(book.getISBN()) != null) {
            return new BZApiMessage(BZApiMessage.MsgType.ERROR, "Book with ISBN=" + book.getISBN() + " already exists.");
        }
        booksManager.setSingleBook(book);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return booksManager.getSingleBook(book.getISBN());
    }
    
    @RequestMapping(value="/books/{isbn}", method=RequestMethod.PUT)
    public BZBook updateBook(@RequestBody BZBook book) {
        booksManager.updateBook(book);
        return booksManager.getSingleBook(book.getISBN());
    }
    
    @RequestMapping(value="/books/{isbn}", method=RequestMethod.DELETE)
    public Object deleteBook(@PathVariable("isbn") String isbn,
            HttpServletResponse response) {
        if (booksManager.deleteBook(isbn)) {
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return new BZApiMessage(BZApiMessage.MsgType.INFO, "Book with ISBN=" + isbn + " has been removed.");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BZApiMessage(BZApiMessage.MsgType.ERROR, "Book with ISBN=" + isbn + " was not found.");
        }
    }   
    
  
}
