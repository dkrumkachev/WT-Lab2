package com.example.movieratingwebapp.dao.interfaces;

import com.example.movieratingwebapp.beans.Genre;
import com.example.movieratingwebapp.exceptions.DaoException;

import java.util.List;

public interface GenreDao {
    List<Genre> getGenres() throws DaoException;

    List<Genre> getMovieGenres(int movieId) throws DaoException;

    void updateMovieGenres(int movieId, List<Genre> genres) throws DaoException;

    Genre addGenre(Genre genre) throws DaoException;

    void updateGenre(Genre genre) throws DaoException;

    void deleteGenre(int id) throws DaoException;
}
