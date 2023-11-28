<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Movies</title>
</head>
<body>
<div class="album py-5 bg-body-tertiary d-flex justify-content-evenly">
    <div class="container w-75">
        <div class="row row-cols-3 row-cols-sm-3 row-cols-md-3 g-3">
            <c:set value='${requestScope["movies"]}' var="movies"/>
            <c:forEach var="movie" items="${movies}">

                <a href="<c:url value="?command=get_movie&id=${movie.getId()}"/>" class="movie-card">
                    <div class="col card shadow-sm">
                        <div class="card-body d-flex">
                            <img src="${movie.getImageUrl()}" class="movie-img"
                                 alt="${movie.getTitle()}">
                            <div class="px-4 d-flex flex-column justify-content-between w-100">
                                <div>
                                    <h2 class="card-title my-0">${movie.getTitle()}</h2>
                                    <p class="card-text my-2">${movie.getYear()}</p>
                                    <p class="card-text my-2">${movie.getDirectorName()}</p>
                                    <p class="card-text my-2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="rgb(245,197,24)" class="bi bi-star-fill" viewBox="0 0 20 20">
                                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
                                        </svg>
                                        ${movie.getRating()}
                                    </p>
                                </div>
                                <div class="btn-group pt-4 pb-1">
                                    <a href="<c:url value="command=get_movie&id=${movie.getId()}"/>" type="btn btn-success" class="btn btn-outline-secondary btn-block">
                                        View
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
