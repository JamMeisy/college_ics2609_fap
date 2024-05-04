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
            <% if (role == null) { %>
            <h1>HEADER WORKS - LOGIN</h1>

            <% } else if (role.equals("Admin")) { %>

            <h1>HEADER WORKS - ADMIN</h1>
            <nav class="test">
                <a href="admin-registration.jsp">Registration</a>
                <a href="admin-registration.jsp">Registration</a>
                <a href="admin-registration.jsp">Registration</a>
            </nav>

            <% } else if (role.equals("Teacher")) { %>
            <h1>HEADER WORKS - TEACHER</h1>
            <nav class="test">
                <a href="admin-registration.jsp">Registration</a>
            </nav>


            <% } else { %>
            <h1>HEADER WORKS - STUDENT</h1>
            <nav class="test">
                <a href="admin-registration.jsp">Registration</a>
                <a href="admin-registration.jsp">Registration</a>
            </nav>

            <% } %>
        </header>