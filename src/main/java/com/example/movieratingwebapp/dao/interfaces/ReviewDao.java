package com.example.movieratingwebapp.dao.interfaces;

import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.beans.Review;
import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.exceptions.DaoException;

import java.util.List;

public interface ReviewDao {
    List<Review> getReviewsByMovie(int movieId) throws DaoException;

    List<Review> getReviewsByUser(User user) throws DaoException;

    boolean UserHasReviewForMovie(int userId, int movieId) throws DaoException;

    Review addReview(Review review) throws DaoException;

    void updateReview(Review review) throws DaoException;

    void deleteReview(int id) throws DaoException;
}
