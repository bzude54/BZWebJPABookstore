package edu.uwpce.bzbookstore.model;

import java.util.ArrayList;
import java.util.List;

public class BookAllReviews {
	
	private String isbn;
	private List<Review> reviews;
	
	public BookAllReviews() {
		this.reviews = new ArrayList<Review>();
	}
		
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIsbn() {
	 return this.isbn;	
	}
		
	public void setReview(Review review) {
		this.reviews.add(review);
	}

	Review getReview(int index) {
		return (Review) reviews.get(index);
	}
	
	void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	List<Review> getReviews() {
		return reviews;
	}
}
