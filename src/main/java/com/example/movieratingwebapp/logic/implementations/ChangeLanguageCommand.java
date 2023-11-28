package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        String newLanguage = request.getParameter("language");
        request.getSession().setAttribute("language", newLanguage);
        return request.getHeader("Referer");
    }
}
