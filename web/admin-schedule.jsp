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
<%
    session.setAttribute("page", "admin-schedule.jsp");
%>

<html>
    <head>
        <title>Title</title>
        <script src="generatedata.js"></script>
    </head>
    <body>

        <!-- All Student-Teacher Schedules -->
        <%
            if (session.getAttribute("schedule") != null) {
                ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                for (Schedule x : schedule) {
                    out.print("<tr>");
                    out.print("<td>" + x.getStudentEmail() + "</td>");
                    out.print("<td>" + x.getTeacherEmail() + "</td>");
                    out.print("<td>" + x.getCourse() + "</td>");
                    out.print("<td>" + x.getSchedule() + "</td>");
                    out.print("</tr>");
                }
            } else
                System.out.println("Null Value");
        %>
    </body>
</html>
