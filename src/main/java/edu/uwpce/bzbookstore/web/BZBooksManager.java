package edu.uwpce.bzbookstore.web;

import java.util.Map;

public interface BZBooksManager {

	public Map<String, BZBook> getBooks();
	
	public void setBooks(Map<String, BZBook> books);
	
	public BZBook getSingleBook(String bookId);
	
	public void setSingleBook(BZBook book);

	public void updateBook(BZBook book);

	public boolean deleteBook(String bookId);
}
