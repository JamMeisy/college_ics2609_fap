<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:34 pm
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
//    if (!role.equals("teacher")) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ActiveLearning PH</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles-global.css"/>
        <link rel="stylesheet" href="css/styles-student-findcourses.css"/>
        <link
                rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400&display=swap"
        />
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="/data"/>
        <!-- Teacher Course PENDING Schedules -->
        <table>
            <tr>
                <th>Student</th>
                <th>Course</th>
                <th>Schedule</th>
            </tr>
            <%
                if (session.getAttribute("schedule") != null) {
                    ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                    for (Schedule x : schedule)
                        if (x.getTeacherEmail().equals(username) && x.getStatus().equals("approved")) {
            %>
            <tr>
                <td><%= x.getStudentEmail() %>
                </td>
                <td><%= x.getCourse() %>
                </td>
                <td><%= x.getSchedule() %>
                </td>
            </tr>
            <%
                        }
                } else
                    System.out.println("Null Value");
            %>
        </table>

        <!-- Teacher Course PENDING Schedules -->
        <table>
            <tr>
                <th>Student</th>
                <th>Course</th>
                <th>Schedule</th>
                <th>Accept/Reject</th>
            </tr>
            <%
                if (session.getAttribute("schedule") != null) {
                    ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                    for (Schedule x : schedule)
                        if (x.getTeacherEmail().equals(username) && x.getStatus().equals("pending")) {
            %>
            <tr>
                <td><%= x.getStudentEmail() %></td>
                <td><%= x.getCourse() %></td>
                <td><%= x.getSchedule() %></td>
                <td>

                    <form action="request-decision" method="POST">
                        <input name="username" value="<%= x.getEntry()%>" hidden/>
                        <button type="submit" name="decision" value="accept">Accept</button>
                    </form>
                    <form action="request-decision" method="POST">
                        <input name="username" value="<%= x.getEntry() %>" hidden/>
                        <button type="submit" name="decision" value="reject">Reject</button>
                    </form>
                </td>
            </tr>
            <%
                            }
                } else
                    System.out.println("Null Value");
            %>
        </table>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
