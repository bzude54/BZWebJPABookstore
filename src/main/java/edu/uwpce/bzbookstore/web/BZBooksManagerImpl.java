package edu.uwpce.bzbookstore.web;

import java.io.IOException;
import java.util.HashMap;
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

public class BZBooksManagerImpl implements BZBooksManager {

	private static final Logger logger = LoggerFactory.getLogger(BZBooksManagerImpl.class);

	private Map<String, BZBook> books;

	@Value("classpath:/defaultbooks.json")
	private Resource defaultBooksResource;

	public BZBooksManagerImpl() {
		this.books = new ConcurrentHashMap<String, BZBook>();
		logger.info("created new bookmanager, map size is: " + this.books.size());
	}
	

	@PostConstruct
	public void init() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.books = mapper.readValue(defaultBooksResource.getInputStream(),
					new TypeReference<HashMap<String, BZBook>>() {});
			logger.info("size of books in bookmanager after inputstream from json file: " + this.books.size());
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
	}
		

	
	public void setBooks(Map<String, BZBook> books) {
		this.books = books;
	}

	@Override
	public Map<String, BZBook> getBooks() {
//		logger.info("Getting books from BZSimpleBookManager");
		return books;
	}

	@Override
	public BZBook getSingleBook(String bookId) {
		return books.get(bookId);
	}

	@Override
	public void setSingleBook(BZBook book) {
		books.put(book.getISBN(), book);
	}


	@Override
	public void updateBook(BZBook book) {
		BZBook checkbook = books.get(book.getISBN());
		if (checkbook != null){
			books.remove(book.getISBN());
		}
		books.put(book.getISBN(), book);
	}


	@Override
	public boolean deleteBook(String bookId) {
		boolean deleteSuccess = false;
		if (books.get(bookId) != null){
			books.remove(bookId);
			deleteSuccess = true; 
		} 
		return deleteSuccess;
	}
}
