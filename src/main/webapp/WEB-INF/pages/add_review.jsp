<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:set value='${requestScope["movieId"]}' var="movieId"/>
<c:if test="${user == null}">
    <c:redirect url="/controller?page=login"/>
</c:if>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title><fmt:message key="add_review"/></title>
</head>
<body>
<%@ include file="/WEB-INF/pages/common.jsp" %>
<div class="container d-flex justify-content-center">
    <form action="controller" method="post" class="form">
        <input type="hidden" name="command" value="add_review">
        <input type="hidden" name="movieId" value="${movieId}">
        <h1 class="text-center py-3"><fmt:message key="rate_this_movie"/>:</h1>
        <div class="my-1">
            <div>
                <label for="rating"><fmt:message key="rating"/>:</label>
            </div>
            <input type="number" min="1" max="10" id="rating" name="rating" required/>
        </div>
        <div class="my-1">
            <div>
                <label for="review"><fmt:message key="your_review"/>:</label>
            </div>
            <textarea id="review" name="review"></textarea>
        </div>
        <div>
            <button type="submit" class="w-100"><fmt:message key="rate"/></button>
        </div>
    </form>
</div>
</body>
</html>
