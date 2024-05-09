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
<%@ page import="exceptions.AuthorizationException" %>

<%
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");
    String role = (String) session.getAttribute("role");
    System.out.println("-- Current User:" + username);
    if (username == null || username.isEmpty()) {
        throw new AuthorizationException("Unauthorized Access");
    }
    if (!role.equals("teacher")) {
        throw new AuthorizationException("Unauthorized Access");
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ActiveLearning PH</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles-global.css"/>
        <link rel="stylesheet" href="css/styles-teacher-myclasses.css"/>
        <link
                rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400&display=swap"
        />
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="/data"/>

        <!-- Teacher Course PENDING Schedules -->
        <div class="table-container">
            <h1>Schedules</h1>
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
        </div>
            
        <!-- Teacher Course PENDING Schedules -->
        <div class="table-container">
        <h1>Pending Requests</h1>
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
                        <input name="entry" value="<%= x.getEntry()%>" hidden/>
                        <button type="submit" id="btn-accept" name="decision" value="accept">Accept</button>
                    </form>
                    <form action="request-decision" method="POST">
                        <input name="entry" value="<%= x.getEntry() %>" hidden/>
                        <button type="submit" id="btn-reject" name="decision" value="reject">Reject</button>
                    </form>
                </td>
            </tr>
            <%
                            }
                } else
                    System.out.println("Null Value");
            %>
        </table>
        </div>
        
        
        <h1 id="gs-label">Generate Schedule</h1>
        <% if (session.getAttribute("schedule-teacher-error") != null) { %>
        <p><%= session.getAttribute("schedule-teacher-error") %></p>
        <%
                session.setAttribute("schedule-teacher-error", null);
            }
        %>
        <form class="generate-report" action="generate-report" method="POST">
            <input type="hidden" name="email" value="<%= username %>">
            <input type="hidden" name="password" value="<%= password %>">
            <input type="hidden" name="role" value="<%= role %>">

            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate">
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate">

            <button type="submit" name="reportType" value="schedule_teacher">Generate Teacher Schedule</button>
        </form>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
