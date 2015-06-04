package edu.uwpce.bzbookstore.model;

import java.io.Serializable;
import java.util.Date;

public interface Review extends Serializable{
	
	String getReviewText();
		
	void setReviewText(String reviewText);

	void setReviewIsbn(String isbn);
	
	String getReviewIsbn();

	long getTimeStamp();

	void setTimeStamp(long timeStamp);
	
	String getReviewUuid();
	
	void setReviewUuid(String uuid);

	
}
