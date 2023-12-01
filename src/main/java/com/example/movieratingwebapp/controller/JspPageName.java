package com.example.movieratingwebapp.controller;

public final class JspPageName {
    public static final String ERROR_PAGE = "/WEB-INF/pages/error.jsp";
    public static final String INDEX_PAGE = "/WEB-INF/pages/index.jsp";
    public static final String LOGIN_PAGE = "/WEB-INF/pages/login.jsp";
    public static final String REGISTRATION_PAGE = "/WEB-INF/pages/registration.jsp";
    public static final String MOVIES_PAGE = "/WEB-INF/pages/movies.jsp";
    public static final String MOVIE_DETAILS_PAGE = "/WEB-INF/pages/movieDetails.jsp";
    public static final String ADD_REVIEW_PAGE = "/WEB_INF/pages/add_review.jsp";
    public static final String USER_PAGE = "/WEB_INF/pages/user.jsp";
    public static final String USERS_LIST_PAGE = "/WEB_INF/pages/users_list.jsp";
    public static final String ADD_MOVIE_PAGE = "/WEB_INF/pages/add_movie.jsp";
    public static final String ADD_GENRE_PAGE = "/WEB_INF/pages/add_genre.jsp";


    private JspPageName() {}

    public static String getPageByName(String pageName) {
        return String.format("/WEB-INF/pages/%s.jsp", pageName);
    }
}
