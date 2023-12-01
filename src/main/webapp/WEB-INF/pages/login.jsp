<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<c:set value='${requestScope["error"]}' var="errorMessage"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:if test="${user != null}">
    <c:redirect url="/controller?command=get_movies"/>
</c:if>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title><fmt:message key="login"/></title>
</head>
<body>
<div class="container d-flex justify-content-center">
    <form action="controller" method="post" class="form">
        <input type="hidden" name="command" value="login">
        <h1 class="text-center py-3"><fmt:message key="login"/></h1>
        <table>
            <tr class="my-1">
                <td><label for="login"><fmt:message key="username"/></label></td>
                <td><input type="text" id="login" name="login" required/></td>
            </tr>
            <tr class="my-1">
                <td class="pr-3"><label for="password"><fmt:message key="password"/></label></td>
                <td><input type="password" id="password" name="password" required/></td>
            </tr>
        </table>
        <div class="w-100 py-2 text-info text-center">${errorMessage}</div>
        <button type="submit" class="w-100">
            <fmt:message key="login"/>
        </button>
        <div class="my-2 text-center w-100">
            <span><fmt:message key="dont_have_an_account"/></span>
            <a href="<c:url value="/controller?page=registration"/>">
                <fmt:message key="register"/>
            </a>
        </div>
    </form>
</div>
</body>
</html>

