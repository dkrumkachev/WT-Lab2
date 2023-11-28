package com.example.movieratingwebapp.logic.implementations;

import com.example.movieratingwebapp.controller.JspPageName;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPageName.REGISTRATION_PAGE;
    }
}
