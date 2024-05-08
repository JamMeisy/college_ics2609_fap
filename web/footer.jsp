<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 10:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer>
    <h1>FOOTER</h1>
    <%= request.getServletContext().getInitParameter("footer") %>
</footer>