package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.Review;
import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcReviewDao;
import com.example.movieratingwebapp.dao.implementations.JdbcUserDao;
import com.example.movieratingwebapp.dao.interfaces.ReviewDao;
import com.example.movieratingwebapp.dao.interfaces.UserDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class GetUserCommand implements ICommand {
    private final UserDao userDao = new JdbcUserDao();

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            User user = userDao.getUserById(id);
            request.setAttribute("user", user);
            return JspPageName.USER_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
