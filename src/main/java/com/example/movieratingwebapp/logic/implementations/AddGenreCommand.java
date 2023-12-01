package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.beans.Genre;
import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.dao.implementations.JdbcGenreDao;
import com.example.movieratingwebapp.dao.interfaces.GenreDao;
import com.example.movieratingwebapp.dao.interfaces.ReviewDao;
import com.example.movieratingwebapp.exceptions.DaoException;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class AddGenreCommand implements ICommand {
    private final GenreDao genreDao = new JdbcGenreDao();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        Genre genre = new Genre(0, name);
        try {
            genreDao.addGenre(genre);
            return JspPageName.ADD_GENRE_PAGE;
        } catch (DaoException e) {
            request.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
