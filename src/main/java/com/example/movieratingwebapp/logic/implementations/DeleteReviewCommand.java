package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcReviewDao;
import com.example.movieratingwebapp.dao.interfaces.ReviewDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteReviewCommand implements ICommand {
    private final ReviewDao reviewDao = new JdbcReviewDao();

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            reviewDao.deleteReview(id);
            return request.getHeader("Referer");
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
