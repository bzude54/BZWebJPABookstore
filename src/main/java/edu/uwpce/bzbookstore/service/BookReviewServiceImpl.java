package edu.uwpce.bzbookstore.service;

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

import edu.uwpce.bzbookstore.model.BookReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("bookreviewService")
public class BookReviewServiceImpl implements BookReviewService {


	private static final Logger logger = LoggerFactory.getLogger(BookReviewServiceImpl.class);

	private Map<String, List<BookReview>> allbooksreviews;

	
	@Value("classpath:/defaultreviews.json")
	private Resource defaultReviewsResource;
	
	
	public BookReviewServiceImpl() {
		this.allbooksreviews = new ConcurrentHashMap<String, List<BookReview>>();
		logger.info("created new bookreviewmanager, map size is: " + this.allbooksreviews.size());
	}

	@PostConstruct
	void init() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.allbooksreviews = mapper.readValue(defaultReviewsResource.getInputStream(),
					new TypeReference<HashMap<String, List<BookReview>>>() {});
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
		for (List<BookReview> reviews : allbooksreviews.values()) {
			Collections.sort(reviews, reviewCompare);
		}
	}

	@Override
	public List<BookReview> getBookReviews(String isbn) {
		List<BookReview> bookreviews = null;
		if (allbooksreviews.containsKey(isbn)) {
			bookreviews = allbooksreviews.get(isbn);
		}
		return bookreviews;
	}

	@Override
	public void setBookReviews(List<BookReview> bookReviews) {
		String key = bookReviews.get(0).getReviewIsbn();
		this.allbooksreviews.put(key, bookReviews);
	}

	@Override
	public void setAllBooksReviews(Map<String, List<BookReview>> booksreviews) {
		this.allbooksreviews = booksreviews;
	}

	@Override
	public Map<String, List<BookReview>> getAllBooksReviews() {
		return this.allbooksreviews;
	}

	@Override
	public void addBookReview(BookReview review) {
		List<BookReview> reviewlist = allbooksreviews
				.get(review.getReviewIsbn());
		if (reviewlist == null) {
			List<BookReview> reviews = new ArrayList<BookReview>();
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
	public BookReview getBookReview(String isbn, Date timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	class ReviewCompare implements Comparator<BookReview> {
		public int compare(BookReview reviewone, BookReview reviewtwo) {
			int result = 0;
			long thistimestamp = reviewone.getTimeStamp();
			long thattimestamp = reviewtwo.getTimeStamp();
			result = (thistimestamp == thattimestamp ? 0 : (thistimestamp > thattimestamp ? -1 : 1));
			return result;
		}
	}

}
