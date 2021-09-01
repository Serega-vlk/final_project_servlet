<%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 30.08.2021
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="service.add" /></title>
</head>
<body>
<form method="POST" action="/admin/add">
    <label for="name"><fmt:message key="service.name" />:</label>
    <input name="name"  id="name">
    <br/>
    <label for="price"><fmt:message key="service.price" />:</label>
    <input name="price" id="price">
    <button type="submit"><fmt:message key="service.add" /></button>
</form>
</body>
</html>
