<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:44 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="objects.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="exceptions.InvalidSessionException" %>
<%@ page import="console.*" %>


<%
    //    String username = (String) session.getAttribute("username");
//    String role = (String) session.getAttribute("role");
//    System.out.println("-- Current User:" + username);
//    if (username == null || username.isEmpty()) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
//    if (!role.equals("Admin")) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }

%>

<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h1>ADMIN MODE</h1>

        <!-- Pending Registration Applications -->
        <%
            if (session.getAttribute("teacher") != null) {
                ArrayList<Teacher> teacher = (ArrayList<Teacher>) session.getAttribute("teacher");
                for (Teacher x : teacher) {
                    if (x.getStatus().equals("Unverified")) {
                        out.print("<tr>");
                        out.print("<td>" + x.getEmail() + "</td>");
                        out.print("<td>" + x.getFname() + "</td>");
                        out.print("<td>" + x.getLname() + "</td>");
                        out.print("<td>" + x.getBday() + "</td>");
                        out.print("<td>" + x.getStatus() + "</td>");
                        out.print("</tr>");
                    }
                }
            }
            else
                System.out.println("Null Value");
        %>
        <h1>END</h1>
    </body>
</html>
