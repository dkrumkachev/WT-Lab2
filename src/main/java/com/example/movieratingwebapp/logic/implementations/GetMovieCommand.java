package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.Genre;
import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.beans.Review;
import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcGenreDao;
import com.example.movieratingwebapp.dao.implementations.JdbcMovieDao;
import com.example.movieratingwebapp.dao.implementations.JdbcReviewDao;
import com.example.movieratingwebapp.dao.interfaces.GenreDao;
import com.example.movieratingwebapp.dao.interfaces.MovieDao;
import com.example.movieratingwebapp.dao.interfaces.ReviewDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetMovieCommand implements ICommand {
    private final MovieDao movieDao = new JdbcMovieDao();
    private final ReviewDao reviewDao = new JdbcReviewDao();
    private final GenreDao genreDao = new JdbcGenreDao();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Movie movie = movieDao.getMovieById(id);
            List<Review> reviews = reviewDao.getReviewsByMovie(id);
            movie.setGenres(genreDao.getMovieGenres(id));
            request.setAttribute("movie", movie);
            request.setAttribute("reviews", reviews);
            request.setAttribute("genres", movie.getGenres());
            User currentUser = (User)request.getSession().getAttribute("user");
            if (currentUser != null && (reviewDao.UserHasReviewForMovie(currentUser.getId(), movie.getId()))) {
                request.setAttribute("userHasReview", true);
            }
            else {
                request.setAttribute("userHasReview", false);
            }
            return JspPageName.MOVIE_DETAILS_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }

    }
}
