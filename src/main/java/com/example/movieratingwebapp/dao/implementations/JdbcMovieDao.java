package com.example.movieratingwebapp.dao.implementations;

import com.example.movieratingwebapp.beans.Genre;
import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.dao.ConnectionPool;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.dao.interfaces.MovieDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMovieDao implements MovieDao {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private Movie createMovieFromRow(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt(1));
        movie.setTitle(resultSet.getString(2));
        movie.setYear(resultSet.getInt(3));
        movie.setDescription(resultSet.getString(4));
        movie.setDirectorName(resultSet.getString(5));
        movie.setRating(resultSet.getFloat(6));
        movie.setImageUrl(resultSet.getString(7));
        return movie;
    }

    private List<Movie> getMoviesFromQuery(String sql) throws DaoException {
        List<Movie> movies = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                movies.add(createMovieFromRow(resultSet));
            }
            return movies;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Movie> getMovies() throws DaoException {
        final String sql = "SELECT * FROM movie ORDER BY rating";
        return getMoviesFromQuery(sql);
    }

    @Override
    public Movie getMovieById(int id) throws DaoException {
        final String sql = "SELECT * FROM movie WHERE id = " + id;
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.first() ? createMovieFromRow(resultSet) : null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) throws DaoException {
        final String sql = "SELECT * FROM movie " +
                "JOIN m2m_movie_genre on movie_id = id " +
                "WHERE genre_id = " + genreId;
        return getMoviesFromQuery(sql);
    }

    @Override
    public void addMovie(Movie movie) throws DaoException {
        final String movieInsertSql = "INSERT INTO movie(title, year, description, director_name, imageUrl) " +
                "VALUES (?, ?, ?, ?, ?)";
        final String genreInsertSql = "INSERT INTO m2m_genre_movie (genre_id, movie_id) VALUES (?, ?)";
        Connection connection = connectionPool.getConnection();
        try (var movieInsertStatement = connection.prepareStatement(movieInsertSql);
             var genreInsertStatement = connection.prepareStatement(genreInsertSql)) {
            movieInsertStatement.setString(1, movie.getTitle());
            movieInsertStatement.setInt(2, movie.getYear());
            movieInsertStatement.setString(3, movie.getDescription());
            movieInsertStatement.setString(4, movie.getDirectorName());
            movieInsertStatement.setString(5, movie.getImageUrl());
            movieInsertStatement.executeUpdate();
            int movieId;
            try (var getIdStatement = connection.createStatement();
                 ResultSet resultSet = getIdStatement.executeQuery("SELECT LAST_INSERT_ID()")) {
                resultSet.first();
                movieId = resultSet.getInt(1);
            }
            List<Genre> genres = movie.getGenres();
            if (genres != null) {
                for (Genre genre : genres) {
                    genreInsertStatement.setInt(1, genre.getId());
                    genreInsertStatement.setInt(2, movieId);
                    genreInsertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updateMovie(Movie movie) throws DaoException {
        final String sql = "UPDATE movie SET title = ?, year = ?, description = ?, director_name = ?, imageUrl = ? " +
                "WHERE id = ?";
        Connection connection = connectionPool.getConnection();
        try (var updateStatement = connection.prepareStatement(sql)) {
            updateStatement.setString(1, movie.getTitle());
            updateStatement.setInt(2, movie.getYear());
            updateStatement.setString(3, movie.getDescription());
            updateStatement.setString(4, movie.getDirectorName());
            updateStatement.setString(5, movie.getImageUrl());
            updateStatement.setFloat(6, movie.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteMovie(int id) throws DaoException {
        final String sql = "DELETE from movie WHERE id = " + id;
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
