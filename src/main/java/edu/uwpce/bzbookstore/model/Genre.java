package edu.uwpce.bzbookstore.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bzude on 4/25/15.
 */

@Entity
@Table(name="GENRE")
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (!id.equals(genre.id)) return false;
        return name.equals(genre.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public String toString() {
        return "Genre {" +
                "ID = " + id +
                ", Name =" + name +
                "}";
    }


    public static class Builder {
        private Genre genre;

        public Builder() {
            genre = new Genre();
        }

        public Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            genre.setId(id);
            return this;
        }

        public Builder name(String name) {
            genre.setName(name);
            return this;
        }


        public Genre build() {
            //validate ??;
            return genre;
        }
    }


}
