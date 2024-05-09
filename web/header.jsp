<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 10:33 pm
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="css/styles-header.css">
<% String role = (String) session.getAttribute("role"); %>
<header class="header">
    <div class="logo">
        <img src="<%= request.getServletContext().getInitParameter("header") %>"
             alt="Active Learning Logo"/>
    </div>

    <% if (role == null) { %>

    <% } else if (role.equals("admin")) { %>
    <nav class="test">
        <a href="admin-registration.jsp">Registration</a>
        <a href="admin-schedule.jsp">Schedule</a>
        <a href="admin-users.jsp">Users</a>
    </nav>

    <% } else if (role.equals("teacher")) { %>
    <nav class="test">
        <a href="teacher-myclasses.jsp">My Classes</a>
    </nav>

    <% } else if (role.equals("student")) { %>
    <nav class="test">
        <a href="student-findcourses.jsp">Find Courses</a>
        <a href="student-mycourses.jsp">My Courses</a>
    </nav>

    <% } %>
    <div class="action-buttons">
        <a href="logout" class="logout">Logout</a>
    </div>
</header>