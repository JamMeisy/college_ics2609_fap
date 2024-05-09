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
<%@ page import="exceptions.AuthorizationException" %>

<%
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");
    String role = (String) session.getAttribute("role");
    System.out.println("-- Current User:" + username);
    if (username == null || username.isEmpty()) {
        throw new AuthorizationException("Unauthorized Access");
    }
    if (!role.equals("admin")) {
        throw new AuthorizationException("Unauthorized Access");
    }
%>

<html>
    <head>
        <title>Title</title>
        <script src="generatedata.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="/data"/>
        <h2>Generate Schedules</h2>
        <!-- All Student-Teacher Schedules -->
        <form action="generate-report" method="POST">
            <input type="hidden" name="email" value="<%= username %>">
            <input type="hidden" name="password" value="<%= password %>">
            <input type="hidden" name="role" value="<%= role %>">

            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate">
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate">

            <button type="submit" name="reportType" value="schedule_admin">Generate Schedule</button>
        </form>
        <h2>All Schedules</h2>
        <table>
            <tr>
                <th>Student</th>
                <th>Teacher</th>
                <th>Course</th>
                <th>Schedule</th>
                <th>Status</th>
            </tr>
            <%
                if (session.getAttribute("schedule") != null) {
                    ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                    for (Schedule x : schedule) {
            %>
            <tr>
                <td><%= x.getStudentEmail() %></td>
                <td><%= x.getTeacherEmail() %></td>
                <td><%= x.getCourse() %></td>
                <td><%= x.getSchedule() %></td>
                <td><%= x.getStatus() %></td>
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
