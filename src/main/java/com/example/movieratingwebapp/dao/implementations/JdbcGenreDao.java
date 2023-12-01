package com.example.movieratingwebapp.dao.implementations;

import com.example.movieratingwebapp.beans.Genre;
import com.example.movieratingwebapp.dao.ConnectionPool;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.dao.interfaces.GenreDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcGenreDao implements GenreDao {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<Genre> getGenres() throws DaoException {
        final String sql = "SELECT * FROM genre";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(sql)) {
            List<Genre> genres = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                genres.add(new Genre(id, name));
            }
            return genres;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Genre> getMovieGenres(int movieId) throws DaoException {
        final String sql = "SELECT genre.id, genre.name FROM genre JOIN m2m_genre_movie ON movie_id = " + movieId;
        List<Genre> genres = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                genres.add(new Genre(id, name));
            }
            return genres;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updateMovieGenres(int movieId, List<Genre> genres) throws DaoException {
        final String deleteSql = "DELETE FROM m2m_genre_movie WHERE movie_id = " + movieId;
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO m2m_genre_movie(genre_id, movie_id) VALUES ");
        sqlBuilder.append("(?, ?),".repeat(genres.size()));
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        final String insertSql = sqlBuilder.toString();
        Connection connection = connectionPool.getConnection();
        try (var deleteStatement = connection.createStatement();
             var insertStatement = connection.prepareStatement(insertSql)) {
            deleteStatement.executeUpdate(deleteSql);
            int i = 1;
            for (Genre genre : genres) {
                insertStatement.setInt(i++, genre.getId());
                insertStatement.setInt(i++, movieId);
            }
            insertStatement.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Genre addGenre(Genre genre) throws DaoException {
        final String sql = "INSERT INTO genre (name) VALUES (?)";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, genre.getName());
            statement.executeUpdate();
            try (var getIdStatement = connection.createStatement();
                 ResultSet resultSet = getIdStatement.executeQuery("SELECT LAST_INSERT_ID()")) {
                resultSet.next();
                genre.setId(resultSet.getInt(1));
                return genre;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updateGenre(Genre genre) throws DaoException {
        final String sql = "UPDATE genre SET name = ? WHERE id = ?";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, genre.getName());
            statement.setInt(2, genre.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteGenre(int id) throws DaoException {
        final String sql = "DELETE FROM genre WHERE id = " + id;
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
