<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<c:set value='${sessionScope["user"]}' var="user"/>
<c:if test="${user.getRole().name() != 'ADMIN'}">
    <div><fmt:message key="access_denied"/></div>
</c:if>
<c:if test="${user.getRole().name() == 'ADMIN'}">
    <html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        <title><fmt:message key="add_genre"/></title>
    </head>
    <body>
    <%@ include file="/WEB-INF/pages/common.jsp" %>
    <div class="container d-flex justify-content-center">
        <form action="controller" method="post" class="form">
            <input type="hidden" name="command" value="add_genre">
            <h1 class="text-center py-3"><fmt:message key="add_genre_form"/>:</h1>
            <table>
                <tr class="my-2">
                    <td><label for="name"><fmt:message key="genre_name_form"/>:</label></td>
                    <td><input type="text" id="name" name="name"/></td>
                </tr>
            </table>
            <div>
                <button type="submit" class="w-100"><fmt:message key="add"/></button>
            </div>
        </form>
    </div>
    </body>
    </html>
</c:if>

