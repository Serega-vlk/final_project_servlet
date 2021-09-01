<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 23.08.2021
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="main.title" /></title>
    <div class="container">
        <a href="/user">
            <button style="margin-left:120px" type="submit" ><fmt:message key="profile" /></button>
        </a>
        <a href="?language=en">
            <button style="margin-left:20px" type="submit">EN</button>
        </a>
        <a href="?language=uk">
            <button style="margin-left:1px" type="submit">UK</button>
        </a>
    </div>
</head>
<body>
<hr/>

<p style="text-align:center"><span style="font-size:26px"><span style="font-family:Comic Sans MS,cursive">
    <fmt:message key="main.title" />
</span></span></p>

<hr />
<p style="margin-left:120px">&nbsp;</p>

<p style="margin-left:120px">&nbsp;</p>

<p style="margin-left:120px">
    <fmt:message key="main.connection" />:
</p>

<p style="margin-left:160px">+380503994939</p>

<p style="margin-left:160px">telegram: @serega_vlk</p>
</body>
</html>