<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:45 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("page", "admin-users.jsp"); %>
<%@ page import="objects.*" %>
<%@ page import="java.util.ArrayList" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>

        <!-- All Users -->
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
    </body>
</html>
