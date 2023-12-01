<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value='${sessionScope["language"]}'/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="error"/></title>
</head>
<body>
    <p><fmt:message key="error"/></p>
</body>
</html>
