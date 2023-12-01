<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <c:set value='${requestScope["movie"]}' var="movie"/>
    <title>${movie.getTitle()}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/movieDetails.css">
</head>
<body>
<%@ include file="/WEB-INF/pages/common.jsp" %>
<div class="container w-75 my-5">
    <div class="d-flex justify-content-center">
        <img src="${pageContext.request.contextPath}/images/${movie.getImageUrl()}" class="movie-img"
             alt="${movie.getTitle()}">
        <div class="px-5 d-flex flex-column justify-content-between w-100">
            <div>
                <h1 class="">${movie.getTitle()}</h1>
                <p class="pt-4"><fmt:message key="year"/>: ${movie.getYear()}</p>
                <p class=""><fmt:message key="director"/>: ${movie.getDirectorName()}</p>
            </div>
            <div class="mt-3">
                <c:set value='${requestScope["genres"]}' var="genres"/>
                <c:forEach var="genre" items="${genres}">
                    <span class="text-info">${genre.getName()}</span>&nbsp
                </c:forEach>
            </div>
            <p class="">${movie.getDescription()}</p>
        </div>
    </div>
    <p class="mt-5">
        <fmt:message key="overall_rating"/>: ${movie.getRating()}
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="rgb(245,197,24)"
             class="bi bi-star-fill" viewBox="0 0 20 20">
            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
        </svg>
    </p>
    <c:set value='${sessionScope["user"]}' var="user"/>
    <c:set value='${requestScope["userHasReview"]}' var="userHasReview"/>
    <c:if test="${user != null and user.getStatus() == 'BANNED'}">
        <div class="my-5">
            <fmt:message key="you_were_banned"/>
        </div>
    </c:if>
    <c:if test="${not userHasReview and user.getRole() == 'USER'}">
        <div class="my-5">
            <a href="<c:url value="/controller?page=add_review&movieId=${movie.getId()}"/>" class="movie-card">
                <fmt:message key="add_review"/>
            </a>
        </div>
    </c:if>
    <c:set value='${requestScope["reviews"]}' var="reviews"/>
    <c:forEach var="review" items="${reviews}">
        <div class="my-3 container rounded border border-dark">
            <div class="px-4 d-flex flex-column justify-content-between w-100">
                <div>
                    <a href="<c:url value="/controller?command=get_user&id=${review.getUser().getId()}"/>">
                            ${review.getUser().getLogin()}
                    </a>&nbsp
                    <span class="text-info">${review.getUser().getStatus().name()}</span>
                </div>
                <div class="mt-1">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="rgb(245,197,24)"
                         class="bi bi-star-fill" viewBox="0 0 20 20">
                        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
                    </svg>
                        ${review.getRating()}
                </div>
                <p class="">${review.getText()}</p>
                <c:if test="${user.getRole() == 'ADMIN'}">
                    <div class="py-1">
                        <a href="<c:url value="/controller?command=delete_review&id=${review.getId()}"/>">
                            <fmt:message key="delete"/>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>