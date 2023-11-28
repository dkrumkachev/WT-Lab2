package com.example.movieratingwebapp.controller;

import com.example.movieratingwebapp.logic.CommandHelper;
import com.example.movieratingwebapp.logic.implementations.ChangeLanguageCommand;
import com.example.movieratingwebapp.logic.interfaces.ICommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Controller extends HttpServlet {

    private static final String COMMAND_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND_NAME);
        CommandHelper a = new CommandHelper();
        ICommand command = a.getCommand(commandName);
        String page;
        try {
            page = command.execute(request);
        } catch (Exception e) {
            page = JspPageName.ERROR_PAGE;
        }
        if (command instanceof ChangeLanguageCommand) {
            response.sendRedirect(page);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                errorMessageDirectlyFromResponse(response);
            }
        }
    }

    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }
}
