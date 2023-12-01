<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:if test="${user.getRole().name() != 'ADMIN'}">
    <div><fmt:message key="access_denied"/></div>
</c:if>
<c:set value='${requestScope["genres"]}' var="genres"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title><fmt:message key="add_movie"/></title>
</head>
<body>
<%@ include file="/WEB-INF/pages/common.jsp" %>
<div class="container d-flex justify-content-center">
    <form enctype="multipart/form-data" action="controller" method="post" class="form">
        <input type="hidden" name="command" value="add_movie">
        <h1 class="text-center py-3"><fmt:message key="add_movie_form"/>:</h1>
        <table>
            <tr class="my-2">
                <td><label for="title"><fmt:message key="title"/>:</label></td>
                <td><input type="text" id="title" name="title"/></td>
            </tr>
            <tr class="my-2">
                <td><label for="year"><fmt:message key="year"/>:</label></td>
                <td><input type="number" min="1888" max="2100" id="year" name="year" required/></td>
            </tr>
            <tr class="my-2">
                <td><label for="director"><fmt:message key="director"/>:</label></td>
                <td><input type="text" id="director" name="director" required/></td>
            </tr>
            <tr class="my-2">
                <td><label for="description"><fmt:message key="description"/>:</label></td>
                <td><textarea id="description" name="description"></textarea></td>
            </tr>
            <tr class="my-2">
                <td><label for="genres"><fmt:message key="genres"/>:</label></td>
                <td>
                    <select id="genres" name="genres" multiple>
                        <c:forEach var="genre" items="${genres}">
                            <option value="${genre.getId()}">${genre.getName()}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="my-2">
                <td><label for="image"><fmt:message key="poster"/>:</label></td>
                <td><input type="file" id="image" name="image"/></td>
            </tr>
        </table>
        <div>
            <button type="submit" class="w-100"><fmt:message key="add"/></button>
        </div>
    </form>
</div>
</body>
</html>
