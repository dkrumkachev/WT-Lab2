package com.example.movieratingwebapp.dao.implementations;

import com.example.movieratingwebapp.beans.Movie;
import com.example.movieratingwebapp.beans.Review;
import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.dao.ConnectionPool;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.dao.interfaces.ReviewDao;
import com.example.movieratingwebapp.enums.UserStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcReviewDao implements ReviewDao {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<Review> getReviewsByMovie(Movie movie) throws DaoException {
        final String selectReviewsSql = "SELECT r.id, r.movie_id, r.text, r.rating, u.id, u.login, u.status " +
                "FROM review r JOIN user u ON user_id = u.id " +
                "WHERE r.movie_id = " + movie.getId();
        List<Review> reviews = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(selectReviewsSql)) {
            while (resultSet.next()) {
                Review review = new Review();
                User user = new User();
                review.setId(resultSet.getInt(1));
                review.setMovieId(resultSet.getInt(2));
                review.setText(resultSet.getString(3));
                review.setRating(resultSet.getInt(4));
                user.setId(resultSet.getInt(5));
                user.setLogin(resultSet.getString(6));
                user.setStatus(UserStatus.valueOf(resultSet.getString(7)));
                review.setUser(user);
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Review> getReviewsByUser(User user) throws DaoException {
        final String selectReviewsSql = "SELECT r.id, r.movie_id, r.text, r.rating " +
                "FROM review r JOIN movie ON movie_id = movie.id " +
                "WHERE user_id = " + user.getId();
        List<Review> reviews = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(selectReviewsSql)) {
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt(1));
                review.setMovieId(resultSet.getInt(2));
                review.setText(resultSet.getString(3));
                review.setRating(resultSet.getInt(4));
                review.setUser(user);
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void addReview(Review review) throws DaoException {
        final String sql = "INSERT INTO review(user_id, movie_id, text, rating) VALUES (?, ?, ?, ?)";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, review.getUser().getId());
            statement.setInt(2, review.getMovieId());
            statement.setString(3, review.getText());
            statement.setInt(4, review.getRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updateReview(Review review) throws DaoException {
        final String sql = "UPDATE review SET text = ? WHERE id = ?";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, review.getText());
            statement.setInt(2, review.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteReview(int id) throws DaoException {
        final String sql = "DELETE FROM review WHERE id = " + id;
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
