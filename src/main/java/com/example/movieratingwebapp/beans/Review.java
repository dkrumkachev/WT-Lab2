package com.example.movieratingwebapp.beans;

import java.io.Serializable;
import java.util.Objects;

public class Review implements Serializable {
    private int id;
    private User user;
    private int movieId;
    private String text;
    private int rating;

    public Review() {
    }

    public Review(int id, int movieId, User user, String text, int rating) {
        this.id = id;
        this.movieId = movieId;
        this.user = user;
        this.text = text;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && rating == review.rating && movieId == review.movieId && Objects.equals(user, review.user) && Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, user, text, rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                '}';
    }
}
