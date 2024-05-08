<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 04/05/2024
  Time: 6:09 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("page", "error-invalidsession.jsp"); %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <h1>INVALID SESSION</h1>
        <jsp:include page="footer.jsp" />

    </body>
</html>
