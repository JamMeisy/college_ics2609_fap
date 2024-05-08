<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 10:56 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("page", "signup.jsp"); %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ActiveLearning PH</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles-signup.css"/>
        <link
                rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400&display=swap"/>

    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <div class="signup-box">
            <!-- Error Prompt -->
            <% if (session.getAttribute("error") != null) { %>
            <h1> <%= (String) session.getAttribute("error") %></h1>
            <% } %>

            <!-- Form -->
            <form action="signup" method="POST">
                <label for="button-box">Are you a? <span>*</span></label>
                <div class="button-box" id="button-box">
                    <button type="button" name="student-btn" id="student-btn" class="user-btn">Student</button>
                    <button type="button" name="teacher-btn" id="teacher-btn" class="user-btn">Teacher</button>
                </div>
                <input name="role" value="student" />
                <div class="input-box">
                    <div class="name">
                        <div class="fname-box">
                            <label id="lbl-fname" for="fname">First name <span>*</span></label>
                            <input type="text" name="fname" id="fname" placeholder="  First Name" required/>
                        </div>
                        <div class="fname-box">
                            <label id="lbl-lname" for="lname">Last name <span>*</span></label>
                            <input type="text" name="lname" id="lname" placeholder="  Last Name" required/>
                        </div>
                    </div>

                    <label for="username">Email <span>*</span></label>
                    <input type="text" name="username" id="username" placeholder=" Email Address" required/>

                    <label for="password">Password <span>*</span></label>
                    <input type="password" name="password" id="password" placeholder="  Password" required/>

                    <label for="confirmpassword">Confirm password</label>
                    <input type="password" name="confirmpassword" id="confirmpassword" placeholder="  Confirm password" required/>

                    <label for="bday">What's your date of birth <span>*</span></label>
                    <input type="date" name="bday" id="bday" required>

                    <label for="resume">Resume Link <span>*</span></label>
                    <input type="text" name="resume" id="resume">
                </div>
                <button type="submit" name="submit-btn" id="submit-btn" class="submit-btn">Create a free account</button>
            </form>
        </div>
        <img class="img-photo" src="assets/UST.jpg" alt="UST">
        <jsp:include page="footer.jsp"/>
    </body>
</html>
