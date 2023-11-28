package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcMovieDao;
import com.example.movieratingwebapp.dao.interfaces.MovieDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetMoviesCommand implements ICommand {
    private final MovieDao movieDao = new JdbcMovieDao();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            List<Movie> movies = movieDao.getMovies();
            request.setAttribute("movies", movies);
            return JspPageName.MOVIES_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
