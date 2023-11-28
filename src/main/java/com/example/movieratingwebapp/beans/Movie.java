package com.example.movieratingwebapp.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Movie implements Serializable {
    private int id;
    private String title;
    private int year;
    private String description;
    private String directorName;
    private float rating;
    private String imageUrl;
    private List<Genre> genres;

    public Movie() {
    }

    public Movie(int id, String title, int year, String description, String directorName, float rating, String imageUrl) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
        this.directorName = directorName;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public float getRating() {
        return (float) Math.round(rating * 100) / 100;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && Float.compare(rating, movie.rating) == 0 && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description) && Objects.equals(directorName, movie.directorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, directorName, rating);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", directorName='" + directorName + '\'' +
                ", rating=" + rating +
                '}';
    }
}
