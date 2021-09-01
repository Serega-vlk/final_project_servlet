<%@ page import="com.dto.LoginErrors" %><%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 26.08.2021
  Time: 12:39
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
    <title><fmt:message key="login.title" /></title>
</head>
<body>
<br/>
<br/>
<form style="text-align:center" method="POST" action="/login">
    <div><label> <fmt:message key="login.login" />: <input type="text" name="username" id="username"/> </label></div>
    <%=
    request.getSession().getAttribute("logErrors") != null ?
            ((LoginErrors) request.getSession().getAttribute("logErrors")).isLogin() ? "<div style=\"color:red\">Login not registered</div>" : ""
            : ""
    %>
    <div><label> <fmt:message key="login.password" />: <input type="password" name="password" id="password"/> </label></div>
    <%=
    request.getSession().getAttribute("logErrors") != null ?
            ((LoginErrors) request.getSession().getAttribute("logErrors")).isPassword() ? "<div style=\"color:red\">wrong password</div>" : ""
            : ""
    %>
    <div><button type="submit"><fmt:message key="login.enter" /></button></div>
</form>
</body>
