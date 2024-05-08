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
        <script src="generatedata.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="/data"/>
        <!-- All Student-Teacher Schedules -->
        <table>
            <tr>
                <th>Student</th>
                <th>Teacher</th>
                <th>Course</th>
                <th>Schedule</th>
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
