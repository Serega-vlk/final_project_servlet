<%@ page import="com.dto.User" %>
<%@ page import="com.dto.Service" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 01.09.2021
  Time: 12:17
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
    <title><fmt:message key="user.title" /></title>
    <a href="/user/logout">
        <button style="margin-left:120px" type="submit" ><fmt:message key="exit" /></button>
    </a>
    <a href="/user/change">
        <button style="margin-left:1px" type="submit" ><fmt:message key="change" /></button>
    </a>
    <a href="/user/balance">
        <button style="margin-left:1px" type="submit" ><fmt:message key="balance" /></button>
    </a>
    <a style="margin-left:1px"><%=((User) request.getSession().getAttribute("user")).getMoney()%></a>
    <a href="?language=en">
    <button style="margin-left:20px" type="submit">EN</button>
    </a>
    <a href="?language=uk">
        <button style="margin-left:1px" type="submit">UK</button>
    </a>
</head>
<body>
<hr />
<a><span style="font-family:Comic Sans MS,cursive" ><fmt:message key="user.hello" />, </span></a>
<a><span style="font-family:Comic Sans MS,cursive" ><%= ((User) request.getSession().getAttribute("user")).getName() %></span></a>

<br/>
<br/>
<br/>

<table border="1" cellpadding="1" cellspacing="1" style="width:500px">
    <caption><fmt:message key="user.connectedServices" /></caption>
    <thead>
    <th>ID</th>
    <th><fmt:message key="user.name" /></th>
    <th><fmt:message key="user.price" /></th>
    </thead>
    <tbody>
    <%
        List<Service> services = (List<Service>) request.getAttribute("connectedServices");
        for (Service service : services) {%>
            <tr>
                <td><%=service.getId()%></td>
                <td><%=service.getName()%></td>
                <td><%=service.getPrice()%></td>
                <td>
                 <form method="post" action="/user/deleteService">
                     <input type="hidden" id="delete_service_id" name="delete_service_id" value="<%=service.getId()%>"/>
                     <button type="submit"><fmt:message key="user.deleteService" /></button>
                 </form>
            </tr>
        <%}%>
    </tbody>
</table>

<p>&nbsp; &nbsp;&nbsp;</p>

<table border="1" cellpadding="1" cellspacing="1" style="width:500px">
    <caption><fmt:message key="user.services" /></caption>
    <thead>
    <th>ID</th>
    <th><fmt:message key="user.name" /></th>
    <th><fmt:message key="user.price" /></th>
    </thead>
    <tbody>
    <%
        services = (List<Service>) request.getAttribute("availableServices");
        for (Service service : services) {%>
            <tr>
                <td><%=service.getId()%></td>
                <td><%=service.getName()%></td>
                <td><%=service.getPrice()%></td>
                <td>
                <form method="post" action="/user/addService">
                    <input type="hidden" id="add_service_id" name="add_service_id" value="<%=service.getId()%>"/>
                    <button type="submit"><fmt:message key="user.addService" /></button>
                </form>
            </tr>
        <%}%>
    </tbody>
</table>
<a><fmt:message key="user.sort" />:</a>
<a style="margin-left:50px" href="/user?sort=id">Id</a>
<a style="margin-left:60px" href="/user?sort=name"><fmt:message key="user.name" /></a>
<a style="margin-left:70px" href="/user?sort=price"><fmt:message key="user.price" /></a>

<p>&nbsp;</p>

<a href="/user/download"><fmt:message key="user.download" /></a>
</body>
</html>
