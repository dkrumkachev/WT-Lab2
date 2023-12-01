package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcUserDao;
import com.example.movieratingwebapp.dao.interfaces.UserDao;
import com.example.movieratingwebapp.enums.UserRole;
import com.example.movieratingwebapp.enums.UserStatus;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;

public class RegistrationCommand implements ICommand {
    private final UserDao userDao = new JdbcUserDao();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm");
        if (password.length() < 8) {
            request.setAttribute("error", "The password must be at least 8 character long");
            return JspPageName.REGISTRATION_PAGE;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "The passwords do not match");
            return JspPageName.REGISTRATION_PAGE;
        }
        try {
            if (userDao.getUserByUsername(login) != null) {
                request.setAttribute("error", "The user with this username already exists.");
                return JspPageName.REGISTRATION_PAGE;
            }
            password = DigestUtils.sha256Hex(password);
            User newUser = new User(0, login, password, UserRole.USER, UserStatus.NEWCOMER, 0);
            newUser = userDao.addUser(newUser);
            request.getSession().setAttribute("user", newUser);
            return JspPageName.INDEX_PAGE;
        }
        catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
