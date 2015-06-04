package edu.uwpce.bzbookstore.model;

import java.util.ArrayList;
import java.util.List;

public class BookAllReviews {
	
	private String isbn;
	private List<BookReview> reviews;
	
	public BookAllReviews() {
		this.reviews = new ArrayList<BookReview>();
	}
		
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIsbn() {
	 return this.isbn;	
	}
		
	public void setReview(BookReview review) {
		this.reviews.add(review);
	}

	Review getReview(int index) {
		return (Review) reviews.get(index);
	}
	
	void setReviews(List<BookReview> reviews) {
		this.reviews = reviews;
	}
	
	List<BookReview> getReviews() {
		return reviews;
	}
}
