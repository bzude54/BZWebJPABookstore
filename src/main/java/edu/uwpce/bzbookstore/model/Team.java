package edu.uwpce.bzbookstore.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bzude on 5/10/15.
 */
@Entity
@Table(name = "BOOK_AUTHOR")
public class Team {

    @EmbeddedId
    private BookAuthorPK id;

    @Column(name = "IS_BESTSELLER")
    private boolean isBestSeller;



    public Team() {}

    public Team(BookAuthorPK id) {
        this.id = id;
        this.isBestSeller = false;
    }


    public Team(BookAuthorPK id, boolean isBestSeller) {
        this.id = id;
        this.isBestSeller = isBestSeller;
    }

    public BookAuthorPK getId() {
        return id;
    }

    public void setId(BookAuthorPK id) {
        this.id = id;
    }

    public boolean isBestSeller() {
        return isBestSeller;
    }

    public void setIsBestSeller(boolean isBestSeller) {
        this.isBestSeller = isBestSeller;
    }

    public Book getBook() {
        return this.id.getBook();
    }

    public Author getAuthor() {
        return this.id.getAuthor();
    }



    @Embeddable
    private static class BookAuthorPK implements Serializable {

        @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID", nullable = false)
        private Book book;

        @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID", nullable = false)
        private Author author;


        public BookAuthorPK(Book book, Author author) {
            this.author = author;
            this.book = book;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BookAuthorPK)) return false;

            BookAuthorPK that = (BookAuthorPK) o;

            if (!getBook().equals(that.getBook())) return false;
            return getAuthor().equals(that.getAuthor());

        }

        @Override
        public int hashCode() {
            int result = getBook().hashCode();
            result = 31 * result + getAuthor().hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "BookAuthorPK{" +
                    "book=" + book.getTitle() +
                    ", author=" + author.getFirstName() + " " + author.getLastName() +
                    '}';
        }
    }

}
