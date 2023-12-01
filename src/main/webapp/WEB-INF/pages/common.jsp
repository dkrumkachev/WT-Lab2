<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<div class="pt-3">
    <ul class="nav d-flex justify-content-end ">
        <c:if test="${user.getRole() == 'ADMIN'}">
        <li class="nav-item px-2">
            <a href="<c:url value="/controller?page=add_movie"/>">
                <fmt:message key="add_movie"/>
            </a>
        </li>
        <li class="nav-item px-2">
            <a href="<c:url value="/controller?page=add_genre"/>">
                <fmt:message key="add_genre"/>
            </a>
        </li>
        <li class="nav-item px-2">
            <a href="<c:url value="/controller?command=get_users"/>">
                <fmt:message key="get_users"/>
            </a>
        </li>
        </c:if>
        <c:choose>
        <c:when test="${user != null}">
        <li class="nav-item px-2">
            <span class="text-success">${user.getLogin()}</span>
        </li>
        <li class="nav-item px-2">
            <form action="controller" method="POST">
                <input type="hidden" name="command" value="logout">
                <button type="submit">
                    <fmt:message key="logout"/>
                </button>
            </form>
        </li>
        </c:when>
        <c:otherwise>
        <li class="nav-item px-2">
            <form action="controller" method="GET">
                <input type="hidden" name="page" value="login">
                <button type="submit">
                    <fmt:message key="login"/>
                </button>
            </form>
        </li>
        </c:otherwise>
        </c:choose>
        <li class="nav-item px-2">
            <form action="controller" method="POST">
                <input type="hidden" name="command" value="change_language">
                <select name="language" onchange="submit()">
                    <option value="ru" ${sessionScope.language == 'ru' ? 'selected' : ''}>Русский</option>
                    <option value="en" ${sessionScope.language == 'en' ? 'selected' : ''}>English</option>
                </select>
            </form>
        </li>
</div>

