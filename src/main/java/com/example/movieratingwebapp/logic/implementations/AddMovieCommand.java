package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.Genre;
import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcGenreDao;
import com.example.movieratingwebapp.dao.implementations.JdbcMovieDao;
import com.example.movieratingwebapp.dao.interfaces.GenreDao;
import com.example.movieratingwebapp.dao.interfaces.MovieDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMovieCommand implements ICommand {
    private final MovieDao movieDao = new JdbcMovieDao();
    private final GenreDao genreDao = new JdbcGenreDao();


    @Override
    public String execute(HttpServletRequest request) {
        String title = request.getParameter("title");
        int year = Integer.parseInt(request.getParameter("year"));
        String director = request.getParameter("director");
        String description = request.getParameter("description");
        String[] genreIdValues = request.getParameterValues("genres");
        List<Integer> genreIds = new ArrayList<>();
        if (genreIdValues != null) {
            for (String genreIdValue : genreIdValues) {
                genreIds.add(Integer.parseInt(genreIdValue));
            }
        }
        String imagePath = null;
        try {
            Part filePart = request.getPart("image");
            if (filePart != null) {
                InputStream fileContent = filePart.getInputStream();
                imagePath = "/images/" + title.toLowerCase();
                Path filePath = Paths.get(imagePath);
                Files.copy(fileContent, filePath);
            }
        } catch(IOException | ServletException e){
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        try {
            Movie movie = new Movie(0, title, year, description, director, 0, imagePath);
            movieDao.addMovie(movie);
            List<Genre> genres = new ArrayList<>();
            for (int id : genreIds) {
                genres.add(new Genre(id, null));
            }
            genreDao.updateMovieGenres(movie.getId(), genres);
            return JspPageName.ADD_MOVIE_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
