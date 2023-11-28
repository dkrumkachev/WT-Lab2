package com.example.movieratingwebapp.logic.interfaces;

import jakarta.servlet.http.HttpServletRequest;

public interface ICommand {
    String execute(HttpServletRequest request);
}
