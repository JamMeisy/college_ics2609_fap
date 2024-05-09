<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 10:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="css/styles-footer.css">
<footer>
    <footer class="footer">
        <div class="logo-section">
            <img src="assets/logo.png" alt="Active Learning Logo" class="logo">
            <div class="footer-section">
                <a href="#">Courses</a>
                <a href="#">Project Management</a>
                <a href="#">Java</a>
                <a href="#">Cyber Security</a>
            </div>
        </div>
        <div class="footer-section">
            <h3>Home</h3>
            <a href="#">About Us</a>
            <a href="#">Contact Us</a>
        </div>
        <div class="footer-section">
            <h3>Search</h3>
            <a href="#">Promos</a>
            <a href="#">Acknowledgement</a>
        </div>
    </footer>
    <div class="footerloweralign">
        <div class="copyright">
            Copyright © 2CSC-Group7 2024. All Rights Reserved
        </div>
        <div class="team">
            <%= request.getServletContext().getInitParameter("footer") %>
        </div>
    </div>
</footer>