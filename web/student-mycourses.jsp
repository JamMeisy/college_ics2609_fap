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
<%@ page import="exceptions.InvalidSessionException" %>

<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    System.out.println("-- Current User:" + username);
//    if (username == null || username.isEmpty()) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
//    if (!role.equals("student")) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
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
        <jsp:include page="header.jsp" />
        <jsp:include page="/data" />
        <!-- Student Course APPROVED Schedules -->
        <h1>Approved Schedules</h1>
        <table>
            <tr>
                <th>Teacher</th>
                <th>Course</th>
                <th>Schedule</th>
            </tr>
            <%
                if (session.getAttribute("schedule") != null) {
                    ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                    for (Schedule x : schedule) {
                        if (x.getStudentEmail().equals(username) && x.getStatus().equals("approved")) {
            %>
            <tr>
                <td><%= x.getTeacherEmail() %></td>
                <td><%= x.getCourse() %></td>
                <td><%= x.getSchedule() %></td>
            </tr>
            <%
                        }
                    }
                } else
                    System.out.println("Null Value");
            %>
        </table>

        <!-- Student Course PENDING Schedules -->
        <h1>Pending Schedules</h1>
        <table>
            <tr>
                <th>Teacher</th>
                <th>Course</th>
                <th>Schedule</th>
            </tr>
            <%
                if (session.getAttribute("schedule") != null) {
                    ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                    for (Schedule x : schedule) {
                        if (x.getStudentEmail().equals(username) && x.getStatus().equals("pending")) {
            %>
            <tr>
                <td><%= x.getTeacherEmail() %></td>
                <td><%= x.getCourse() %></td>
                <td><%= x.getSchedule() %></td>
            </tr>
            <%
                        }
                    }
                } else
                    System.out.println("Null Value");
            %>
        </table>
        <jsp:include page="footer.jsp" />
    </body>
</html>
