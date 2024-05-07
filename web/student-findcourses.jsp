<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:36 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("page", "student-findcourses.jsp"); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ActiveLearning PH</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles-global.css" />
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
            <p class="p2">Let's join our famous class, the knowledge provided will definately be useful to you.</p>
        </div>
        

        <!-- Card Template for Applying Courses -->
        <div class="course-card">
            <img class="course-card-img" src="assets/UST.jpg" alt="UST">
            <h3>Course Name</h3>
            <p>Course Description</p>
            <p>Course kineme</p>
            <form method="post" action="course-request">
                <i>Choose Teacher</i>
                <select name="request-teacher" id="request-teacher" required>
                    <!-- Options in JSP -->
                    <option value="sample">sample</option>
                </select>

                <i>Choose your sessions</i>
                <i>Note that choosing the same date indicates more hours</i>

                <input type="date" name="date1" required>
                <input type="date" name="date2" required>
                <input type="date" name="date3" required>
                <input type="date" name="date4" required>

                <input type="hidden" name="">
                <button type="submit">Apply</button>
            </form>
        </div>
    </body>
</html>
