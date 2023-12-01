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

    private static final String COMMAND_PARAMETER_NAME = "command";
    private static final String PAGE_PARAMETER_NAME = "page";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageName = req.getParameter(PAGE_PARAMETER_NAME);
        if (pageName != null) {
            dispatchToPage(JspPageName.getPageByName(pageName), req, resp);
        } else {
            processCommand(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND_PARAMETER_NAME);
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
            dispatchToPage(page, request, response);
        }
    }

    private void dispatchToPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        var paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            request.setAttribute(paramName, request.getParameter(paramName));
        }
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            errorMessageDirectlyFromResponse(response);
        }
    }

    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }
}
