<%@ page import="com.dto.User" %>
<%@ page import="com.dto.ValidationResults.ChangePasswordErrors" %><%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 01.09.2021
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="change.title" /></title>
    <a href="/user/logout">
        <button style="margin-left:120px" type="submit" ><fmt:message key="exit" /></button>
    </a>
    <a href="/user">
        <button style="margin-left:1px" type="submit" ><fmt:message key="profile" /></button>
    </a>
    <a href="?language=en">
        <button style="margin-left:20px" type="submit">EN</button>
    </a>
    <a href="?language=uk">
        <button style="margin-left:1px" type="submit">UK</button>
    </a>
</head>
<hr/>
<body>
<form style="text-align:center" method="POST" action="/user/change">
    <div><label> <fmt:message key="change.old" />: <input type="password" name="oldPass" id="oldPass" /> </label>
    </div>
    <%=
    request.getSession().getAttribute("changeErrors") != null ?
            ((ChangePasswordErrors) request.getSession().getAttribute("changeErrors")).isOldPassEmpty() ? "<div style=\"color:red\">Password can`t be empty</div>" : ""
            : ""
    %>
    <%=
    request.getSession().getAttribute("changeErrors") != null ?
            ((ChangePasswordErrors) request.getSession().getAttribute("changeErrors")).isOldPassIncorrect() ? "<div style=\"color:red\">Old password is incorrect</div>" : ""
            : ""
    %>
    <div><label> <fmt:message key="change.new" />: <input type="password" name="newPass" id="newPass" /> </label>
    </div>
    <%=
    request.getSession().getAttribute("changeErrors") != null ?
            ((ChangePasswordErrors) request.getSession().getAttribute("changeErrors")).isNewPassEmpty() ? "<div style=\"color:red\">Password can`t be empty</div>" : ""
            : ""
    %>
    <%=
    request.getSession().getAttribute("changeErrors") != null ?
            ((ChangePasswordErrors) request.getSession().getAttribute("changeErrors")).isNewPassInvalid() ? "<div style=\"color:red\">password must contain at least 5 characters</div>" : ""
            : ""
    %>
    <div><button type="submit"><fmt:message key="change.enter" /></button></div>
</form>
</body>
</html>
