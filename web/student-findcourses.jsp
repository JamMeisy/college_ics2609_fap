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
<%@ page import="javax.crypto.Cipher" %>

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
    <img class="img-photo" src="assets/UST.jpg" alt="UST">
    <div class="label-container">
        <p class="p1">Explore Programs</p>
        <h2>Our Most Popular Classes</h2>
        <p class="p2">Let's join our famous class, the knowledge provided will definitely be useful to you.</p>
    </div>
  
    <!-- Course Application Card-->
     <jsp:include page="/data" />
    <%
        ArrayList<Courses> courses = (ArrayList<Courses>) session.getAttribute("courses");
        ArrayList<TeacherCourses> teacher_courses = (ArrayList<TeacherCourses>) session.getAttribute("teacher_courses");
        if (courses != null) {
    %>
    
    <div class="container">
    <%    
            for (Courses x : courses) {
    %>
    
        <div class="course-card">
            <img class="course-card-img" src="assets/UST.jpg" alt="UST">
            <h3><%= x.getCname() %></h3>
            <p><%= x.getCdescription() %></p>
            <p class="Chours">Completion Hours: <%= x.getChours() %></p>
            <form method="post" action="course-request">
                <i>Choose Teacher</i>
                <select name="request-teacher" id="request-teacher" required>
                    <!-- Generates Options (Teacher) for the Course -->
                    <%
                        if (teacher_courses != null)
                            for (TeacherCourses y : teacher_courses)
                                if (y.getCname().equals(x.getCname())) {
                                    String teach = y.getTeacherEmail();
                    %>
                    <option value="<%= teach %>"> <%= teach %> </option>
                    <%
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

                <input type="hidden" name="<%= x.getCname() %>">
                <button type="submit">Click here to Apply   >   </button>
            </form>
        </div>
    <%
            }
    %>
    </div> <!-- End of .container -->
    <% } %>
</body>

</html>
