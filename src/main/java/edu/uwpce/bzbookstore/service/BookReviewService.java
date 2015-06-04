package edu.uwpce.bzbookstore.service;

import edu.uwpce.bzbookstore.model.BookReview;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookReviewService {
	
	List<BookReview> getBookReviews(String isbn);
	
	void setBookReviews(List<BookReview> bookReviews );
	
	Map<String, List<BookReview>> getAllBooksReviews();
	
	void setAllBooksReviews(Map<String, List<BookReview>> booksreviews);
	
	void addBookReview(BookReview review);
	
	BookReview getBookReview(String isbn, Date timestamp);
		

}
