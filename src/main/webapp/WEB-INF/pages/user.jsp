<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %><%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<c:set value='${requestScope["user"]}' var="user"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title>${user.getLogin()}</title>
</head>
<body>
<%@ include file="/WEB-INF/pages/common.jsp" %>
<div class="container w-50 my-5">
    <div class="d-flex justify-content-center">
        <div>
            <div class="">${user.getLogin()}</div>
            <div class="pt-1"><fmt:message key="status"/>: ${user.getStatus().name()}</div>
        </div>
    </div>
</div>
</body>
</html>
