<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 10:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="css/styles-footer.css">
<footer class="footer">
    <div class="footer-section">
        <h3>Courses</h3>
        <a href="#">Project Management</a>
        <a href="#">Java</a>
        <a href="#">Cyber Security</a>
    </div>
    <div class="footer-section">
        <h3>Home</h3>
        <a href="#">About Us</aa>
            <a href="#">Contact Us</a>
    </div>
    <div class="footer-section">
        <h3>Search</h3>
        <a href="#">Promos</a>
        <a href="#">Acknowledgement</a>
    </div>
    <div class="footer-section">
        <h3>Group Members</h3>
        <a href="#">BORJA</a>
        <a href="#">CASTANEDA</a>
        <a href="#">DACAYO</a>
        <a href="#">JUICO</a>
        <a href="#">TAN</a>
    </div>
    <div class="copyright">
        <h3>Copyright © <%= request.getServletContext().getInitParameter("footer") %> All Rights Reserved</h3>
    </div>
</footer>