<%@ page import="com.dto.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dto.Service" %><%--
  Created by IntelliJ IDEA.
  User: SeregaAsus
  Date: 26.08.2021
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages"/>
<html>
<html xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title><fmt:message key="admin.title" /></title>
    <div class="container">
        <a href="/user/logout">
            <button style="margin-left:120px" type="submit" ><fmt:message key="exit" /></button>
        </a>
        <a href="/user/change">
            <button style="margin-left:120px" type="submit" ><fmt:message key="change" /></button>
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
<hr />
<a><span style="font-family:Comic Sans MS,cursive" ><fmt:message key="user.hello" />, </span></a>
<a><span style="font-family:Comic Sans MS,cursive" ><%= ((User) request.getSession().getAttribute("user")).getName() %></span></a>
<a><span style="font-family:Comic Sans MS,cursive" > (<fmt:message key="admin.admin" />)</span></a>

<br/>
<br/>
<br/>

<table border="1" cellpadding="1" cellspacing="1" style="width:500px">
    <caption><fmt:message key="admin.allUsers" /></caption>
    <thead>
    <th >ID</th>
    <th ><fmt:message key="admin.name" /></th>
    <th ><fmt:message key="admin.login" /></th>
    <th ><fmt:message key="admin.email" /></th>
    <th ><fmt:message key="admin.role" /></th>
    </thead>
    <tbody>
    <% StringBuilder sb = new StringBuilder();
        List<User> users = (List<User>) request.getAttribute("allUsers");
        for (User user : users) {%>
            <tr>
                <td> <%=user.getId()%> </td>
                <td> <%=user.getName()%> </td>
                <td> <%=user.getLogin()%> </td>
                <td> <%=user.getEmail()%> </td>
                <td> <%=user.getRole().name()%> </td>
                <td>
                    <form method = "post" action = "/admin/block" >
                             <input type="hidden" id="block_id" name="block_id" value="<%=user.getId()%>"/>
                             <button type="submit"><fmt:message key="admin.blockUser" /></button >
                    </form>
                    <td>
                        <form method="post" action="/admin/unblock" >
                            <input type="hidden" id="unblock_id" name="unblock_id" value="<%=user.getId()%>"/>
                            <button type="submit"><fmt:message key="admin.unblockUser" /></button>
                        </form >
                    <td >
                        <form method="post" action="/admin/delete">
                            <input type="hidden" id="delete_id" name="delete_id" value="<%=user.getId()%>"/>
                            <button type="submit"><fmt:message key="admin.deleteUser" /></button >
                        </form>
            </tr>
        <%}%>
    </tbody>
</table>
<br/>
<form style="margin-left:70px" method="get" action="/admin/register">
    <button type="submit"><fmt:message key="admin.userReg" /></button>
</form>

<p>&nbsp; &nbsp;&nbsp;</p>

<table border="1" cellpadding="1" cellspacing="1" style="width:500px">
    <caption><fmt:message key="admin.services" /></caption>
    <thead>
    <th>ID</th>
    <th><fmt:message key="user.name" /></th>
    <th><fmt:message key="user.price" /></th>
    </thead>
    <tbody>
    <%
        List<Service> services = (List<Service>) request.getAttribute("services");
        for (Service service : services) { %>
    <tr>
        <td> <%=service.getId()%></td>
        <td> <%=service.getName()%></td>
        <td> <%=service.getPrice()%></td>
        <td>
            <form method="post" action="/admin/remove">
                <input type="hidden" id="delete_service_id" name="delete_service_id" value="<%=service.getId()%>">
                <input type="submit" value="<fmt:message key="admin.deleteService" />"/>
            </form>
        </td>
    </tr>
        <%}%>
    </tbody>
</table>
<a><fmt:message key="user.sort" />:</a>
<a style="margin-left:50px" href="/admin?sort=id">Id</a>
<a style="margin-left:60px" href="/admin?sort=name"><fmt:message key="user.name" /></a>
<a style="margin-left:70px" href="/admin?sort=price"><fmt:message key="user.price" /></a>
<form style="margin-left:70px" method="get" action="/admin/add">
    <button type="submit"><fmt:message key="admin.addService" /></button>
</form>

<p>&nbsp;</p>
</body>
</html>

