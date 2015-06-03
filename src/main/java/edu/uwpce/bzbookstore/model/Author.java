package edu.uwpce.bzbookstore.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bzude on 5/10/15.
 */
@Entity
@Table(name = "AUTHOR")
public class Author implements Comparable, Serializable {

//    private static final long serialVersionUID = ;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIO")
    private String bio;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Set<Book> books = new TreeSet<Book>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public int compareTo(Object o) {
        int result = 0;
        if (this.equals(o)) return 0;

        Author author = (Author) o;

        if (!(this.getLastName()).equals(author.getLastName())) {
            result = (this.getLastName().compareTo(author.getLastName()));
        } else {
            result = (this.getFirstName().compareTo(author.getFirstName()));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (!getLastName().equals(author.getLastName())) return false;
        return getFirstName().equals(author.getFirstName());

    }

    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
//                ", books=" + books +
                '}';
    }
}
