<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:45 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="objects.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="exceptions.InvalidSessionException" %>

<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    System.out.println("-- Current User:" + username);
//    if (username == null || username.isEmpty()) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
//    if (!role.equals("admin")) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
%>

<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <jsp:include page="/data" />

        <!-- All Users -->
        <table>
            <tr>
                <th>User</th>
                <th>Role</th>
            </tr>
        <%
            if (session.getAttribute("users") != null) {
                ArrayList<User> users = (ArrayList<User>) session.getAttribute("users");
                for (User x : users) {
        %>
        <tr>
            <td><%= x.getEmail() %></td>
            <td><%= x.getRole() %></td>
        </tr>
        <%
                }
            }
            else
                System.out.println("Null Value");
        %>
        </table>
    </body>
</html>
