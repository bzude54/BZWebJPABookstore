package edu.uwpce.bzbookstore.web;

import java.util.ArrayList;
import java.util.List;

public class BZBookAllReviews {
	
	private String isbn;
	private List<BZBookReview> reviews;
	
	public BZBookAllReviews() {
		this.reviews = new ArrayList<BZBookReview>();
	}
		
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIsbn() {
	 return this.isbn;	
	}
		
	public void setReview(BZBookReview review) {
		this.reviews.add(review);
	}

	BZReview getReview(int index) {
		return (BZReview) reviews.get(index);
	}
	
	void setReviews(List<BZBookReview> reviews) {
		this.reviews = reviews;
	}
	
	List<BZBookReview> getReviews() {
		return reviews;
	}
}
