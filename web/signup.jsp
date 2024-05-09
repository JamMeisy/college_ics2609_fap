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
        <link
                rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400&display=swap"/>
        <link rel="stylesheet" href="css/styles-signup.css"/>

        <script>
            function student() {
                document.getElementById("role").value = "student";
                document.getElementById("resume").hidden = true;
                document.getElementById("resume-label").hidden = true;
                document.getElementById("student-btn").classList.add("selected");
                document.getElementById("teacher-btn").classList.remove("selected");
            }
            function teacher() {
                document.getElementById("role").value = "teacher";
                document.getElementById("resume").hidden = false;
                document.getElementById("resume-label").hidden = false;
                document.getElementById("teacher-btn").classList.add("selected");
                document.getElementById("student-btn").classList.remove("selected");
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <div class="signup-box">
            <!-- Error Prompt -->
            <% if (session.getAttribute("signup-error") != null) { %>
            <h1> <%= (String) session.getAttribute("signup-error") %></h1>
            <%
                    session.setAttribute("signup-error", null);
                }
            %>
            <h3>Are you a</h3>
            <div class="role-box" id="role-box">
                <button type="button" id="student-btn" class="user-btn selected" onclick="student()">Student</button>
                <button type="button" id="teacher-btn" class="user-btn" onclick="teacher()">Teacher</button>
            </div>
            <!-- Form -->
            <form action="signup" method="POST">
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
                    <input type="email" name="username" id="username" placeholder=" Email Address" required/>

                    <label for="password">Password <span>*</span></label>
                    <input type="password" name="password" id="password" placeholder="  Password" required/>

                    <label for="confirmpassword">Confirm password</label>
                    <input type="password" name="confirmpassword" id="confirmpassword" placeholder="  Confirm password" required/>

                    <label for="bday">Birth Date <span>*</span></label>
                    <input type="date" name="bday" id="bday" required>

                    <label for="resume" id="resume-label" hidden="true">Resume Link <span>*</span></label>
                    <input type="text" name="resume" id="resume" hidden="true" placeholder="Paste your resume link here">
                </div>

                <input name="role" value="student" id="role" hidden/>
                <button type="submit" name="submit-btn" id="submit-btn" class="submit-btn">Create a free account</button>
            </form>
            <a href="index.jsp" id="link-login">Log In instead?</a>
        </div>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
