<%@ page import="com.dto.User" %><%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 01.09.2021
  Time: 21:03
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
    <title><fmt:message key="balance.title" /></title>
    <a href="/user/logout">
        <button style="margin-left:120px" type="submit" ><fmt:message key="exit" /></button>
    </a>
    <a href="/user/change">
        <button style="margin-left:1px" type="submit" ><fmt:message key="change" /></button>
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
<body>
<hr/>
<p style="text-align:center"><span style="font-size:26px"><span style="font-family:Comic Sans MS,cursive"><fmt:message key="balance.yourBalance" />: </span></span></p>
<p style="text-align:center"><span style="font-size:26px"><span style="font-family:Comic Sans MS,cursive"><%=((User) request.getSession().getAttribute("user")).getMoney()%></span></span></p>
<form style="text-align:center" method="post" action="/user/balance">
    <label for="moneyToAdd"><fmt:message key="balance.toAdd" />:</label>
    <input type="number" name="moneyToAdd" id="moneyToAdd">
    <button type="submit"><fmt:message key="balance.enter" /></button>
</form>
</body>
</html>
