package com.example.movieratingwebapp.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LanguageFilter implements Filter {
    private String getLocale(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        String language = (String)httpSession.getAttribute("language");
        if (language == null) {
            language = "en";
            httpSession.setAttribute("language", "en");
        }
        return language;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String language = getLocale(req);
        req.setAttribute("language", language);
        chain.doFilter(request, response);
    }
}
