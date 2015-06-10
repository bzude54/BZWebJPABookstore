package edu.uwpce.bzbookstore.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REVIEW")
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Review implements Comparable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8545327746357214420L;

	static final Logger logger = LoggerFactory.getLogger(Review.class);


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "BOOK_ID")
	private Integer bookId;

	@Column(name = "BOOK_ISBN")
	private String bookIsbn;

	@Column(name = "REVIEW_TEXT")
	private String reviewText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REVIEW_TIMESTAMP")
	private Date timeStamp;

/*
	@ManyToOne
	@JoinColumn(name = "ISBN")
	private Book book;
*/


	public Review() {	}
	
	public Review(Integer id, String isbn, String reviewtext, Date timestamp) {
		this.bookId = id;
		this.bookIsbn = isbn;
		this.reviewText = reviewtext;
		this.timeStamp = timestamp;
		logger.info("CREATED NEW REVIEW WITH ID: " + this.bookId + ", isbn: " + this.bookIsbn + ", reviewtext: " + this.getReviewText() + ", timestamp: " + this.getTimeStamp());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookid) {
		this.bookId = bookid;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String isbn) {
		this.bookIsbn = isbn;
	}

	public String getReviewText() {
		return this.reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public Date getTimeStamp() {
/*
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
		return dateFormat.format(timeStamp);
*/
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Review)) return false;

		Review review = (Review) o;

		if (!getBookId().equals(review.getBookId())) return false;
		if (!getBookIsbn().equals(review.getBookIsbn())) return false;
		return getTimeStamp().equals(review.getTimeStamp());

	}

	@Override
	public int hashCode() {
		logger.info("in hashcode method. review isbn is: " + getBookIsbn());

		return getBookIsbn().hashCode();
	}
/*
	@Override
	public int hashCode() {
	//	int result = getBookId().hashCode();
		int result = 31 * result + getBookIsbn().hashCode();
	//	result = 31 * result + getTimeStamp().hashCode();
		return result;
	}
*/

/*
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
*/



	@Override
	public int compareTo(Object o) {
		int result = 0;
		if (!this.equals(o) && (o instanceof Review)){
			Review that = (Review) o;
			result = this.getTimeStamp().compareTo(that.getTimeStamp());
		}
		return result;
	}
}
