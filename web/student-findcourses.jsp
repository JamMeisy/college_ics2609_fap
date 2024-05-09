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
<%@ page import="exceptions.AuthorizationException" %>

<%
    // Disable Caching
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies

    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    System.out.println("-- Current User:" + username);
    if (username == null || username.isEmpty()) {
        throw new AuthorizationException("Unauthorized Access");
    }
    if (!role.equals("student")) {
        throw new AuthorizationException("Unauthorized Access");
    }
%>

<% session.setAttribute("page", "student-findcourses.jsp"); %>
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
        <jsp:include page="header.jsp" />
        <jsp:include page="/data" />
        <img class="img-photo" src="assets/UST.jpg" alt="UST">
        <div class="label-container">
            <p class="p1">Explore Programs</p>
            <h2>Our Most Popular Classes</h2>
            <p class="p2">Let's join our famous class, the knowledge provided will definately be useful to you.</p>
        </div>
        <% if (session.getAttribute("course-request-error") != null) { %>
        <h1> <%= (String) session.getAttribute("course-request-error") %></h1>
        <%
                session.setAttribute("course-request-error", null);
            }
        %>
        <!-- Course Application Card-->
        <%
            ArrayList<Courses> courses = (ArrayList<Courses>) session.getAttribute("courses");
            ArrayList<Teacher> teachers = (ArrayList<Teacher>) session.getAttribute("teacher");
            ArrayList<TeacherCourses> teacher_courses = (ArrayList<TeacherCourses>) session.getAttribute("teacher_courses");
            if (courses != null) {
                // ! Inconsistency : Creates template for all courses even if there is not teacher
                // ! Inconsistency : Teacher is added regardless of verified or not
                // ! Quick Fix: Ensure Database is populated so that each course has a verified teacher
                int count = 1;
                for (Courses x : courses) {
        %>
      
        <div class="course-card">
            <img class="course-card-img" src="assets/UST.jpg" alt="UST">
            <h3><%= x.getCname() %></h3>
            <p><%= x.getCdescription() %></p>
            <p>Course Hours: <%= x.getChours() %></p>
            <form method="post" action="request" id="course<%= count %>">
                <i>Choose Teacher</i>
                <select name="teacher" id="teacher" required>
                    <!-- Generates Options (Teacher) for the Course -->
                    <%
                        if (teacher_courses != null)
                            for (TeacherCourses link : teacher_courses)
                                if (link.getCname().equals(x.getCname()))
                                    for (Teacher y : teachers)
                                        if (link.getTeacherEmail().equals(y.getEmail())) {
                    %>
                    <option value="<%= link.getTeacherEmail()%>"><%= y.getFname() %> <%= y.getLname() %></option>
                    <%
                                    break;
                                }
                    %>
                </select>

                <i>Choose your sessions</i>
                <i>Note that choosing the same date indicates more hours</i>
                <div class="date-container">
                    <input type="date" name="date1" required>
                    <input type="date" name="date2" required>
                    <input type="date" name="date3" required>
                    <input type="date" name="date4" required>
                </div>

                <input type="hidden" name="course" value="<%= x.getCname() %>">
                <button type="submit" form="course<%= count++ %>">Apply</button>
            </form>
        </div>
    <%
            }
    %>
    </div> <!-- End of .container -->
    <% } %>
</body>

</html>
