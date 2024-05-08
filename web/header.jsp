<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 10:33 pm
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String role = (String) session.getAttribute("role"); %>
        <header>
            <%= request.getServletContext().getInitParameter("header") %>
            <% if (role == null) { %>
            <h1>HEADER WORKS - LOGIN</h1>

            <% } else if (role.equals("admin")) { %>
            <h1>HEADER WORKS - ADMIN</h1>
            <nav class="test">
                <a href="admin-registration.jsp">Registration</a>
                <a href="admin-schedule.jsp">Schedule</a>
                <a href="admin-users.jsp">Users</a>
            </nav>
            <a href="profile.jsp">Profile</a>

            <% } else if (role.equals("teacher")) { %>
            <h1>HEADER WORKS - TEACHER</h1>
            <nav class="test">
                <a href="teacher-myclasses.jsp">My Classes</a>
            </nav>

            <% } else if (role.equals("student")) { %>
            <h1>HEADER WORKS - STUDENT</h1>
            <nav class="test">
                <a href="student-findcourses.jsp">Find Courses</a>
                <a href="student-mycourses.jsp">My Courses</a>
            </nav>
            <a href="profile.jsp">Profile</a>

            <% } %>
            <a href="logout">Logout</a>
        </header>