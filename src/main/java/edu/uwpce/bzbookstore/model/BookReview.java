package edu.uwpce.bzbookstore.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "REVIEW")
public class BookReview implements Comparable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8545327746357214420L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "ISBN")
	private String isbn;

	@Column(name = "REVIEW_TEXT")
	private String reviewText;

	@Temporal(TemporalType.DATE)
	@Column(name = "TIMESTAMP")
	private Date timeStamp;
	
	public BookReview() {	}
	
	public BookReview(String isbn, String reviewtext, Date timestamp) {
		this.isbn = isbn;
		this.reviewText = reviewtext;
		this.timeStamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getReviewText() {
		return this.reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BookReview)) return false;

		BookReview that = (BookReview) o;

		if (!getIsbn().equals(that.getIsbn())) return false;
		return getTimeStamp().equals(that.getTimeStamp());

	}

	@Override
	public int hashCode() {
		int result = getIsbn().hashCode();
		result = 31 * result + getTimeStamp().hashCode();
		return result;
	}

	@Override
	public int compareTo(Object o) {
		int result = 0;
		if (!this.equals(o) && (o instanceof BookReview)){
			BookReview that = (BookReview) o;
			result = this.getTimeStamp().compareTo(that.getTimeStamp());
		}
		return result;
	}
}
