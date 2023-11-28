package com.example.movieratingwebapp.dao.interfaces;

import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.exceptions.DaoException;

import java.util.List;

public interface MovieDao {
    List<Movie> getMovies() throws DaoException;

    Movie getMovieById(int id) throws DaoException;

    List<Movie> getMoviesByGenre(int genreId) throws DaoException;

    void addMovie(Movie movie) throws DaoException;

    void updateMovie(Movie movie) throws DaoException;

    void deleteMovie(int id) throws DaoException;
}
