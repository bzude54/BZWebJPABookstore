package edu.uwpce.bzbookstore.model;

import java.util.UUID;

public class BookReview implements Review {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8545327746357214420L;
	
	private String uuid;
	private String isbn;
	private String reviewText;
	private long timeStamp;
	
	public BookReview() {	}
	
	public BookReview(String isbn, String reviewtext, long timestamp) {
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
