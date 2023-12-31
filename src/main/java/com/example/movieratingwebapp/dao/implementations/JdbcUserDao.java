package com.example.movieratingwebapp.dao.implementations;

import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.dao.ConnectionPool;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.dao.interfaces.UserDao;
import com.example.movieratingwebapp.enums.UserRole;
import com.example.movieratingwebapp.enums.UserStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private User createUserFromRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        UserRole role = UserRole.valueOf(resultSet.getString(4));
        UserStatus status = UserStatus.valueOf(resultSet.getString(5));
        int rating = resultSet.getInt(6);
        return new User(id, login, password, role, status, rating);
    }

    @Override
    public List<User> getUsers() throws DaoException {
        final String sql = "SELECT * FROM user WHERE role = " + UserRole.USER;
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(sql)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(createUserFromRow(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public User getUserById(int id) throws DaoException {
        final String sql = "SELECT * FROM user WHERE id = " + id;
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(sql)) {
            return resultSet.next() ? createUserFromRow(resultSet) : null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public User getUserByUsername(String username) throws DaoException {
        final String sql = "SELECT * FROM user WHERE login = ?";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (var resultSet = statement.executeQuery()) {
                return resultSet.next() ? createUserFromRow(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public User addUser(User user) throws DaoException {
        final String sql = "INSERT INTO user(login, password, role, status) VALUES (?, ?, ?, ?)";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setString(4, user.getStatus().name());
            statement.executeUpdate();
            try (var getIdStatement = connection.createStatement();
                 ResultSet resultSet = getIdStatement.executeQuery("SELECT LAST_INSERT_ID()")) {
                resultSet.next();
                user.setId(resultSet.getInt(1));
                return user;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updateUser(User user) throws DaoException {
        final String sql = "UPDATE user SET login = ?, password = ?, status = ?, rating = ? WHERE id = ?";
        Connection connection = connectionPool.getConnection();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getStatus().name());
            statement.setInt(4, user.getRating());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteUser(int id) throws DaoException {
        final String sql = "DELETE FROM user WHERE id = " + id;
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
