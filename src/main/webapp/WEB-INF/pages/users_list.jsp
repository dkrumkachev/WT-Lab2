<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:if test="${user.getRole().name() != 'ADMIN'}">
    <div><fmt:message key="access_denied"/></div>
</c:if>
<c:set value='${requestScope["users"]}' var="users"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title><fmt:message key="list_of_users"/></title>
</head>
<body>
<c:forEach var="user" items="${users}">
    <div class="my-3 container rounded border border-dark">
        <div class="px-4 d-flex flex-column justify-content-between w-100">
            <div>
                <a href="<c:url value="/controller?command=get_user&id=${user.getId()}"/>">
                        ${user.getLogin()}
                </a>
            </div>
            <div>
                <fmt:message key="rating"/>: ${user.getRating()}
            </div>
            <div>
                <fmt:message key="status"/>: ${user.getStatus().name()}
            </div>
            <div>
                <form action="controller" method="post" class="form">
                    <input type="hidden" name="userId" value="${user.getId()}">
                    <input type="hidden" name="command" value="ban">
                    <button type="submit" class=""><fmt:message key="ban"/></button>
                </form>
            </div>
        </div>
    </div>
</c:forEach>
</body>
</html>
