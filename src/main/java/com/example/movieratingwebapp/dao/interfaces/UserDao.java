package com.example.movieratingwebapp.dao.interfaces;

import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.exceptions.DaoException;

public interface UserDao {
    User getUserById(int id) throws DaoException;

    User getUserByUsername(String username) throws DaoException;

    void addUser(User user) throws DaoException;

    void updateUser(User user) throws DaoException;

    void deleteUser(int id) throws DaoException;
}
