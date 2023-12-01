package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.User;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcUserDao;
import com.example.movieratingwebapp.dao.interfaces.UserDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginCommand implements ICommand {
    private final UserDao userDao = new JdbcUserDao();
    
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            User user = userDao.getUserByUsername(login);
            if (user == null) {
                request.setAttribute("error", "Incorrect username.");
                return JspPageName.LOGIN_PAGE;
            }
            password = DigestUtils.sha256Hex(password);
            if (!user.getPassword().equals(password)) {
                request.setAttribute("error", "Incorrect password.");
                return JspPageName.LOGIN_PAGE;
            }
            request.getSession().setAttribute("user", user);
            return JspPageName.INDEX_PAGE;
        }
        catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
