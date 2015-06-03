package edu.uwpce.bzbookstore.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BZBookReviewsManagerImpl implements BZBookReviewsManager {


	private static final Logger logger = LoggerFactory.getLogger(BZBookReviewsManagerImpl.class);

	private Map<String, List<BZBookReview>> allbooksreviews;

	
	@Value("classpath:/defaultreviews.json")
	private Resource defaultReviewsResource;
	
	
	public BZBookReviewsManagerImpl() {
		this.allbooksreviews = new ConcurrentHashMap<String, List<BZBookReview>>();
		logger.info("created new bookreviewmanager, map size is: " + this.allbooksreviews.size());
	}

	@PostConstruct
	void init() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.allbooksreviews = mapper.readValue(defaultReviewsResource.getInputStream(),
					new TypeReference<HashMap<String, List<BZBookReview>>>() {});
			logger.info("size of allbooksreviews in bookreviewmanager after inputstream from json file: " + this.allbooksreviews.size());
		} catch (JsonParseException e) {
			logger.error("Got JSONParseException." + e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error("Got JSONMappingException." + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Got IOException." + e);
			e.printStackTrace();
		}
		
		ReviewCompare reviewCompare  = new ReviewCompare();
		for (List<BZBookReview> reviews : allbooksreviews.values()) {
			Collections.sort(reviews, reviewCompare);
		}
	}

	@Override
	public List<BZBookReview> getBookReviews(String isbn) {
		List<BZBookReview> bookreviews = null;
		if (allbooksreviews.containsKey(isbn)) {
			bookreviews = allbooksreviews.get(isbn);
		}
		return bookreviews;
	}

	@Override
	public void setBookReviews(List<BZBookReview> bookReviews) {
		String key = bookReviews.get(0).getReviewIsbn();
		this.allbooksreviews.put(key, bookReviews);
	}

	@Override
	public void setAllBooksReviews(Map<String, List<BZBookReview>> booksreviews) {
		this.allbooksreviews = booksreviews;
	}

	@Override
	public Map<String, List<BZBookReview>> getAllBooksReviews() {
		return this.allbooksreviews;
	}

	@Override
	public void addBookReview(BZBookReview review) {
		List<BZBookReview> reviewlist = allbooksreviews
				.get(review.getReviewIsbn());
		if (reviewlist == null) {
			List<BZBookReview> reviews = new ArrayList<BZBookReview>();
			reviews.add(review);
			allbooksreviews.put(review.getReviewIsbn(), reviews);

		} else {
			ReviewCompare reviewCompare  = new ReviewCompare();
//			int index = Collections.binarySearch(reviewlist, review, reviewCompare);
//			reviewlist.add(index, review);
			reviewlist.add(review);
			Collections.sort(reviewlist, reviewCompare);

		}

	}

	@Override
	public BZBookReview getBookReview(String isbn, Date timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	class ReviewCompare implements Comparator<BZBookReview> {
		public int compare(BZBookReview reviewone, BZBookReview reviewtwo) {
			int result = 0;
			long thistimestamp = reviewone.getTimeStamp();
			long thattimestamp = reviewtwo.getTimeStamp();
			result = (thistimestamp == thattimestamp ? 0 : (thistimestamp > thattimestamp ? -1 : 1));
			return result;
		}
	}

}
