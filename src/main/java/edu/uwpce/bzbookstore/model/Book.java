package edu.uwpce.bzbookstore.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


@Entity
@Table(name="BOOK")
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5152205072240029064L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "ISBN")
	private String isbn;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
			@JoinTable(name = "BOOK_AUTHOR",
					joinColumns = {@JoinColumn(name ="BOOK_ID", referencedColumnName = "ID")},
					inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")})
	private Set<Author> authors = new TreeSet<Author>();

	// unidirectional , BOOK table has a foreign key reference to the GENRE table
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "GENRE_ID")
	private Genre genre;
	
	@Transient //not mapped from database - use during development to minimize mappings
	private String image;

	@Transient
	@Temporal(TemporalType.DATE)
	@Column(name = "PUBLISH_DATE")
	private Date publishDate;
	
//	@Transient
	@Column(name = "PRICE")
	private BigDecimal price;

	@OneToMany
	@JoinColumn(name = "BOOK_ID")
//	@OneToMany(mappedBy="book",orphanRemoval = true ,fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
	private Set<Review> reviews = new TreeSet<Review>();



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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

/*
	public Set<Team> getTeam() {
		return teams;
	}

	public void setTeam(Set<Team> teams) {
		this.teams = teams;
	}


	public void addAuthor(Author author, boolean isBestSeller) {

		Team team = new Team();
		Team.Id teamId = new Team.Id(this, author);
		team.setId(teamId);
		team.setIsBestSeller(isBestSeller);

		this.teams.add(team);
		// Also add the team object to the employee.
		//employee.getTeams().add(team); //TODO not needed  if cascade is enabled

	}

*/


	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthor(Set<Author> authors) {
		this.authors = authors;
	}

	public void addAuthor(Author author) {
		if (author != null) {
			this.getAuthors().add(author);
		}
	}



	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public BigDecimal getPrice() {
		return price;
	}


	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	


	public String toString() {
	    return "Book {" +
	    		"ID = " + id +
	            ", ISBN =" + isbn +
	            ", title = " + title + 
//	            ", author = " + author +
	            ", genre = " + genre + 
	            ", publish date = " + publishDate +
	            ", price = " + price +
	            ", description = " + description +
	            "}";
	}
	
	
	  public static class Builder {
		    private Book book;

		    public Builder() {
		      book = new Book();
		    }

		    public Builder builder() {
		      return new Builder();
		    }

		    public Builder id(Integer id) {
			      book.setId(id);
			      return this;
			}

		    public Builder isbn(String isbn) {
		      book.setIsbn(isbn);
		      return this;
		    }

		    public Builder title(String title) {
		    	book.setTitle(title);
		    	return this;
		    }

		    public Builder description(String description) {
		    	book.setDescription(description);
		    	return this;
			}

/*
		    public Builder author(String author) {
		    	book.setAuthor(author);
		        return this;
		    }

*/

		    public Builder genre(Genre genre) {
		    	book.setGenre(genre);
		    	return this;
		    }
		    
		    
		    public Builder publicationDate(Date pubdate) {
		    	book.setPublishDate(pubdate);
		    	return this;
		    }


		    public Builder price(BigDecimal price) {
		    	book.setPrice(price);
		    	return this;
		    }

		    public Book build() {
		      //validate ??;
		      return book;
		    }
		  }

	

}
