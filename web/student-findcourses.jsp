<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:36 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>


        <!-- Card Template for Applying Courses -->
        <div>
            <h3>Course Name</h3>
            <p>Course Description</p>
            <p>Course kineme</p>
            <!-- Alternative on being hidden using javascript -->
            <button type="button" hidden>See More</button>
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
                <button type="submit">Apply</button>
            </form>
        </div>
    </body>
</html>
