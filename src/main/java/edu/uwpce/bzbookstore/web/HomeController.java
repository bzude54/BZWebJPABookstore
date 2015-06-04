package edu.uwpce.bzbookstore.web;

import edu.uwpce.bzbookstore.dao.BookDao;
import edu.uwpce.bzbookstore.model.Book;
import edu.uwpce.bzbookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller

public class HomeController {

	private static final Logger log = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
    BookDao bookDao;

    @Autowired
    BookService bookService;

    @RequestMapping(value={"/","/home","/index"}, method = RequestMethod.GET)

	public ModelAndView homePage() {
        log.info("home");
		ModelAndView mav = new ModelAndView();
        List<Book> books = bookDao.findAll();
        mav.addObject("books", books);
		mav.setViewName("bzbooks");
		return mav;
	}

    @RequestMapping("/home2")
    public ModelAndView homePage2() {
        log.info("home2");
        ModelAndView mav = new ModelAndView();
        List<Book> books = bookDao.findAll();
        mav.addObject("books", books);
        mav.setViewName("home");
        return mav;
    }
}