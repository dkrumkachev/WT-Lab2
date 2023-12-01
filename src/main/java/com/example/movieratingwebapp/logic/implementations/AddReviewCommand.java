package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.beans.Review;
import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcMovieDao;
import com.example.movieratingwebapp.dao.implementations.JdbcReviewDao;
import com.example.movieratingwebapp.dao.interfaces.MovieDao;
import com.example.movieratingwebapp.dao.interfaces.ReviewDao;
import com.example.movieratingwebapp.enums.UserStatus;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AddReviewCommand implements ICommand {
    private final ReviewDao reviewDao = new JdbcReviewDao();
    private final MovieDao movieDao = new JdbcMovieDao();

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String reviewText = request.getParameter("review");
        String a = request.getParameter("movieId");
        int movieId = Integer.parseInt(a);
        Review review = new Review(0, movieId, user, reviewText, rating);
        try {
            reviewDao.addReview(review);
            changeUserRating(user, movieId, rating);
            return JspPageName.INDEX_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }

    private void changeUserRating(User user, int movieId, int reviewRating) throws DaoException {
        Movie movie = movieDao.getMovieById(movieId);
        float ratingDifference = Math.abs(movie.getRating() - reviewRating);
        int userRatingIncrement = Math.round(5 - ratingDifference);
        user.setRating(user.getRating() + userRatingIncrement);
        user.setStatus(UserStatus.getStatusByUserRating(user.getRating()));
    }
}
