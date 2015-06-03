package edu.uwpce.bzbookstore.web;

import java.util.UUID;

public class BZBookReview implements BZReview {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8545327746357214420L;
	
	private String uuid;
	private String isbn;
	private String reviewText;
	private long timeStamp;
	
	public BZBookReview() {	}
	
	public BZBookReview(String isbn, String reviewtext, long timestamp) {
		this.isbn = isbn;
		this.reviewText = reviewtext;
		this.timeStamp = timestamp;
		this.uuid = UUID.randomUUID().toString();
	}

	@Override
	public String getReviewText() {
		return this.reviewText;
	}

	@Override
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	@Override
	public void setReviewIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String getReviewIsbn() {
		return this.isbn;
	}

	@Override
	public long getTimeStamp() {
		return timeStamp;
	}

	@Override
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String getReviewUuid() {
		return this.uuid;
	}

	@Override
	public void setReviewUuid(String uuid) {
		this.uuid = uuid;
	}

}
