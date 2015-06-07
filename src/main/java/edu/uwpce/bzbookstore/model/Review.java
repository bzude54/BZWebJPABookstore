package edu.uwpce.bzbookstore.model;

import java.io.Serializable;
import java.util.Date;

public interface Review extends Serializable{

	Integer getId();

	void setId(Integer id);
	
	String getReviewText();
		
	void setReviewText(String reviewText);

	void setIsbn(String isbn);
	
	String getIsbn();

	long getTimeStamp();

	void setTimeStamp(long timeStamp);

	
}
