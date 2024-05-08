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
        <link rel="stylesheet" href="css/styles-teacher-myclasses.css"/>
        <link
                rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400&display=swap"
        />
    </head>
       <body>
        <jsp:include page="header.jsp" />
        <jsp:include page="/data" />
        <!-- Teacher Course Schedules -->
        <img class="img-photo" src="assets/UST.jpg" alt="UST">
         <div class="teacher-info">
            <div class="img-container">
                <img class="img-profile-pic" src="assets/teacher.JPG" alt="teacher">
            </div>
            <div class="teacher-description">
                <h4>Professor's title (?)</h4>
                <h2>Professor's name</h2>
                <p>Professor's description:
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Aliquam scelerisque aliquet dictum. Sed rutrum iaculis iaculis. 
                        Praesent in ipsum dapibus, pellentesque turpis viverra, ullamcorper tortor. 
                        Cras elementum pharetra sapien ut molestie. Cras pellentesque tristique diam, 
                        a mollis sem consequat ut. Phasellus volutpat, mi et sodales ornare, 
                        nibh eros auctor massa, vel porttitor urna justo a lorem. 
                        Proin congue, nulla in sollicitudin gravida, est erat ultrices purus, 
                        nec venenatis dolor leo vel nisi.
                    </p>
            </div>
        </div>
        <div class="table-container">
            <table>
                <tr>
                    <th>Student</th>
                    <th>Course</th>
                    <th>Schedule</th>
                    <th>Accept/Reject</th>
                </tr>
                <% if (session.getAttribute("schedule") != null) {
                    ArrayList<Schedule> schedule = (ArrayList<Schedule>) session.getAttribute("schedule");
                    for (Schedule x : schedule) { %>
                <tr>
                    <td><%= x.getStudentEmail() %></td>
                    <td><%= x.getCourse() %></td>
                    <td><%= x.getSchedule() %></td>
                    <td>
                        <form action="request-decision" method="POST">
                            <input name="username" value="<%= x.getStudentEmail()%>" hidden/>
                            <button type="submit" name="decision" value="accept">Accept</button>
                        </form>
                        <form action="request-decision" method="POST">
                            <input name="username" value="<%= x.getStudentEmail()%>" hidden/>
                            <button type="submit" name="decision" value="reject">Reject</button>
                        </form>
                    </td>
                </tr>
                <% }
                    } else
                        System.out.println("Null Value");
                %>
            </table>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>
