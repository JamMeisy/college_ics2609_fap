<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:36 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="objects.*" %>
<%@ page import="java.util.ArrayList" %>

<%
    session.setAttribute("page", "student-mycourses.jsp");
    String username = (String) session.getAttribute("username");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ActiveLearning PH</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles-index.css"/>
        <link rel="stylesheet" href="css/styles-global.css" />
        <link
                rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400&display=swap"
        />
    </head>
    <body>
        <!-- Student Course Schedules -->
        <%
            if (session.getAttribute("schedule") != null) {
                ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                for (Schedule x : schedule) {
                    if (x.getStudentEmail().equals(username)) {
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
