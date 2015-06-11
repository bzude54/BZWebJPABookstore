package edu.uwpce.bzbookstore.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uwpce.bzbookstore.model.Review;
import edu.uwpce.bzbookstore.service.BookService;
import edu.uwpce.bzbookstore.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//@RestController
@Controller
public class BookController {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    @Autowired
    private BookService bookService;
    
    
/*
    public List<Book> getBooks() {
        return bookService.findAllBooks();
    }
*/

@RequestMapping(value="/books", method=RequestMethod.GET)
    public ModelAndView getAllBooks() {
    logger.info("getting all books");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookService.findAllBooks();
        mav.addObject("books", books);
        mav.setViewName("books");
        return mav;
    }


    @RequestMapping(value="/books/{isbn}", method=RequestMethod.GET)
    public ModelAndView getBookByIsbn(@PathVariable("isbn") String isbn) {
        logger.info("looking for book with isbn: " + isbn);
        Book book = bookService.findBookByIsbn(isbn);
        List<Review> reviews= bookService.findReviewsByIsbn(isbn);
        ModelAndView mav = new ModelAndView();
        if (book != null) {
            logger.info("found book with isbn: " + isbn);
            mav.addObject("book", book);
//            mav.setViewName("bookdetail");
        } else {
            logger.error("Book with ISBN=" + isbn + " does not exist.");
            mav = null;
        }
        if (reviews != null) {
            logger.info("found reviews for isbn: " + isbn);
            mav.addObject("reviews", reviews);
//            mav.setViewName("bookdetail");
        } else {
            logger.error("Reviews for book with ISBN=" + isbn + " do not exist.");
            return null;
        }
        mav.setViewName("bookdetail");
        return mav;

    }


    @RequestMapping(value="/books/{isbn}/review", method=RequestMethod.GET)
    public ModelAndView getReviewsByIsbn(@PathVariable("isbn") String isbn) {
        logger.info("looking for book reviews for isbn: " + isbn);
        List<Review> reviews= bookService.findReviewsByIsbn(isbn);
        ModelAndView mav = new ModelAndView();
        if (reviews != null) {
            logger.info("found reviews for isbn: " + isbn);
            mav.addObject("reviews", reviews);
            mav.setViewName("bookdetail");
            return mav;
        } else {
            logger.error("Reviews for book with ISBN=" + isbn + " do not exist.");
            return null;
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




    @ResponseBody
    @RequestMapping(value = "/books/reviews", method = RequestMethod.GET)
    public List<Review> getBookReviews(@RequestParam String isbn, Model model) {
        logger.info("ISBN from ajax = " + isbn);
        List<Review> reviews = bookService.findReviewsByIsbn(isbn);
        if (reviews.size() == 0){
            reviews = null;
        }
        return reviews;
    }

    @ResponseBody
    @RequestMapping(value = "/books/reviews", method = RequestMethod.POST)
    public List<Review> postReview(HttpSession session, @RequestParam int id, @RequestParam String isbn, @RequestParam String reviewText) {
        logger.debug("IN THE POST REVIEWS HANDLER WITH ID: " + id + ", AND ISBN: " + isbn + ", review text: " + reviewText);

        Date date = new Date();
        logger.info("current time: " + new Timestamp(date.getTime()));


        Review newreview = new Review(id, isbn, reviewText, new Timestamp(date.getTime()));
//    	newreview.setReviewIsbn(bookIsbn);
//    	newreview.setReviewText(review.getReviewText());
//    	newreview.setTimeStamp(new Date());
        bookService.addReview(newreview);
        List<Review> reviews = bookService.findReviewsByIsbn(isbn);
/*
        logger.info("reviews from post text1 = " + reviews.get(0).getReviewText());
        logger.info("reviews from post text2 = " + reviews.get(1).getReviewText());
        logger.info("reviews from post text3 = " + reviews.get(2).getReviewText());
        logger.info("reviews from post text4 = " + reviews.get(3).getReviewText());
        logger.info("reviews from post text5 = " + reviews.get(4).getReviewText());
*/



        return reviews;

    }




}
