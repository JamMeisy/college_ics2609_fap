<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 10:33 pm
  To change this template use File | Settings | File Templates.
--%>

<%-- TODO: Raph, add specific nav bar for each role --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String role = (String) session.getAttribute("role"); %>
<% if (role.equals("console")) { %>
    <header>
        <h1>HEADER WORKS - ADMIN</h1>
        <nav class="test">
            <a href="admin-registration.jsp">Registration</a>
            <a href="admin-registration.jsp">Registration</a>
            <a href="admin-registration.jsp">Registration</a>
        </nav>
    </header>

<% } else if (role.equals("teacher")) { %>
    <header>
        <h1>HEADER WORKS - TEACHER</h1>
        <nav class="test">
            <a href="admin-registration.jsp">Registration</a>
        </nav>
    </header>

<% } else if (role.equals("guest")) { %>
    <header>
        <h1>HEADER WORKS - STUDENT</h1>
        <nav class="test">
            <a href="admin-registration.jsp">Registration</a>
            <a href="admin-registration.jsp">Registration</a>
        </nav>
    </header>

<% } else { %>
    <header>
        <h1>HEADER WORKS - GUEST</h1>
    </header>
<% } %>