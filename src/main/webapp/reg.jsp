<%@ page import="com.dto.ValidationResults.RegistrationErrors" %><%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 31.08.2021
  Time: 13:24
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
    <title><fmt:message key="reg.title" /></title>
</head>
<body>
<form method="POST" action="/admin/register">
    <label for="name"><fmt:message key="reg.enter.name" />:</label>
    <input name="name" id="name">
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isEmailEmpty() ? "<div style=\"color:red\">Name can`t be empty</div>" : ""
            : ""
    %>
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isNameInvalid() ? "<div style=\"color:red\">Name must start with capital</div>" : ""
            : ""
    %>
    <br/>
    <label for="email"><fmt:message key="reg.enter.email" />:</label>
    <input name="email" id="email">
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isEmailEmpty() ? "<div style=\"color:red\">Email can`t be empty</div>" : ""
            : ""
    %>
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isEmailExist() ? "<div style=\"color:red\">This email already exist</div>" : ""
            : ""
    %>
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isEmailInvalid() ? "<div style=\"color:red\">Email invalid</div>" : ""
            : ""
    %>
    <br/>
    <label for="login"><fmt:message key="reg.enter.login" />:</label>
    <input name="login" id="login">
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isLoginEmpty() ? "<div style=\"color:red\">Login can`t be empty</div>" : ""
            : ""
    %>
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isLoginExist() ? "<div style=\"color:red\">This Login already exist</div>" : ""
            : ""
    %>
    <br/>
    <label for="password"><fmt:message key="reg.enter.password" />:</label>
    <input type="password" name="password" id="password">
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isPasswordInvalid() ? "<div style=\"color:red\">password must contain at least 5 characters</div>" : ""
            : ""
    %>
    <br/>
    <label for="repeatPass"><fmt:message key="reg.enter.passwordRepeat" />:</label>
    <input type="password" name="repeatPass" id="repeatPass">
    <%=
    request.getSession().getAttribute("regErrors") != null ?
            ((RegistrationErrors) request.getSession().getAttribute("regErrors")).isRepPassword() ? "<div style=\"color:red\">passwords must match</div>" : ""
            : ""
    %>
    <br/>
    <button type="submit"><fmt:message key="reg.enter.register" /></button>
</form>
</body>
</html>
