package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcUserDao;
import com.example.movieratingwebapp.dao.interfaces.UserDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetUsersCommand implements ICommand {
    private final UserDao userDao = new JdbcUserDao();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            List<User> users = userDao.getUsers();
            request.setAttribute("users", users);
            return JspPageName.USERS_LIST_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
