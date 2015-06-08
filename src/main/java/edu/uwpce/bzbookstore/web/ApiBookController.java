package edu.uwpce.bzbookstore.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import edu.uwpce.bzbookstore.model.Review;
import edu.uwpce.bzbookstore.service.BookService;
import edu.uwpce.bzbookstore.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//@RestController
@Controller
@RequestMapping("/api")
public class ApiBookController {

    private static final Logger log = LoggerFactory
            .getLogger(HomeController.class);

    @Autowired
    private BookService bookService;
    
    
/*
    public List<Book> getBooks() {
        return bookService.findAllBooks();
    }
*/

@RequestMapping(value="/book/get", method=RequestMethod.GET)
    public ModelAndView getAllBooks() {
        log.info("getting all books");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        mav.addObject("books", books);
        mav.setViewName("book");
        return mav;
    }


    @RequestMapping(value="/book/get/{isbn}", method=RequestMethod.GET)
    public ModelAndView getBookByIsbn(@PathVariable("isbn") String isbn) {
        log.info("looking for book with isbn: " + isbn);
        Book book = bookService.findBookByIsbn(isbn);
        List<Review> reviews= bookService.findReviewsByIsbn(isbn);
        ModelAndView mav = new ModelAndView();
        if (book != null) {
            log.info("found book with isbn: " + isbn);
            mav.addObject("book", book);
//            mav.setViewName("bookdetail");
        } else {
            log.error("Book with ISBN=" + isbn + " does not exist.");
            mav = null;
        }
        if (reviews != null) {
            log.info("found reviews for isbn: " + isbn);
            mav.addObject("reviews", reviews);
//            mav.setViewName("bookdetail");
        } else {
            log.error("Reviews for book with ISBN=" + isbn + " do not exist.");
            return null;
        }
        mav.setViewName("bookdetail");
        return mav;

    }


    @RequestMapping(value="/book/get/{isbn}/review", method=RequestMethod.GET)
    public ModelAndView getReviewsByIsbn(@PathVariable("isbn") String isbn) {
        log.info("looking for book reviews for isbn: " + isbn);
        List<Review> reviews= bookService.findReviewsByIsbn(isbn);
        ModelAndView mav = new ModelAndView();
        if (reviews != null) {
            log.info("found reviews for isbn: " + isbn);
            mav.addObject("reviews", reviews);
            mav.setViewName("bookdetail");
            return mav;
        } else {
            log.error("Reviews for book with ISBN=" + isbn + " do not exist.");
            return null;
        }
    }



    @RequestMapping(value="/book", method=RequestMethod.POST)
    public Object createBook(@RequestBody Book book, HttpServletResponse response) {
        if (bookService.findBookByIsbn(book.getIsbn()) != null) {
            return new ApiMessage(ApiMessage.MsgType.ERROR, "Book with ISBN=" + book.getIsbn() + " already exists.");
        }
        bookService.updateBook(book);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return bookService.findBookByIsbn(book.getIsbn());
    }
    
    @RequestMapping(value="/book/{isbn}", method=RequestMethod.PUT)
    public Book updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return bookService.findBookByIsbn(book.getIsbn());
    }
    
    @RequestMapping(value="/book/{isbn}", method=RequestMethod.DELETE)
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
