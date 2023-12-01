package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.Genre;
import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcGenreDao;
import com.example.movieratingwebapp.dao.implementations.JdbcMovieDao;
import com.example.movieratingwebapp.dao.interfaces.GenreDao;
import com.example.movieratingwebapp.dao.interfaces.MovieDao;
import com.example.movieratingwebapp.enums.UserRole;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetMoviesCommand implements ICommand {
    private final MovieDao movieDao = new JdbcMovieDao();
    private final GenreDao genreDao = new JdbcGenreDao();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            List<Movie> movies = movieDao.getMovies();
            request.setAttribute("movies", movies);
            User user = (User)request.getSession().getAttribute("user");
            if (user != null && user.getRole() == UserRole.ADMIN) {
                List<Genre> genres = genreDao.getGenres();
                request.setAttribute("genres", genres);
            }
            return JspPageName.MOVIES_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
