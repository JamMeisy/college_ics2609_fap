<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("page", "teacher-myclasses.jsp"); %>
<%@ page import="objects.*" %>
<%@ page import="java.util.ArrayList" %>

<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <!-- Teacher Course Schedules -->
        <%
            if (session.getAttribute("schedule") != null) {
                ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                for (Schedule x : schedule) {
                    if (x.getTeacherEmail().equals(session.getAttribute("username"))) {
                        out.print("<tr>");
                        out.print("<td>" + x.getStudentEmail() + "</td>");
                        out.print("<td>" + x.getTeacherEmail() + "</td>");
                        out.print("<td>" + x.getCourse() + "</td>");
                        out.print("<td>" + x.getSchedule() + "</td>");
                        out.print("</tr>");
                    }
                }
            }
            else
                System.out.println("Null Value");
        %>
    </body>
</html>
