<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="movies"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/movies.css">
</head>
<body>
<%@ include file="/WEB-INF/pages/common.jsp" %>
<c:set value='${requestScope["movies"]}' var="movies"/>
<c:forEach var="movie" items="${movies}">
    <div class="my-3 container rounded border border-dark">
        <a href="<c:url value="/controller?command=get_movie&id=${movie.getId()}"/>" class="movie-card">
            <div class="d-flex">
                <img src="${pageContext.request.contextPath}/images/${movie.getImageUrl()}" class="movie-img"
                     alt="${movie.getTitle()}">
                <div class="px-4 d-flex flex-column justify-content-between w-100">
                    <h2 class="">${movie.getTitle()}</h2>
                    <p class="">${movie.getYear()}</p>
                    <p class="">${movie.getDirectorName()}</p>
                    <p class="">${movie.getDescription()}</p>
                    <p class="mt-1">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="rgb(245,197,24)"
                             class="bi bi-star-fill" viewBox="0 0 20 20">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
                        </svg>${movie.getRating()}
                    </p>
                </div>
            </div>
        </a>
        <c:set value='${sessionScope["user"]}' var="user"/>
        <c:if test="${user.getRole() == 'ADMIN'}">
            <div class="py-2">
                <a href="<c:url value="/controller?command=delete_movie&id=${movie.getId()}"/>">
                    <fmt:message key="delete"/>
                </a>
            </div>
        </c:if>
    </div>
</c:forEach>
</body>
</html>
