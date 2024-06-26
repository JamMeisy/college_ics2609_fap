<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:44 pm
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
    if (!role.equals("admin")) {
        throw new AuthorizationException("Unauthorized Access");
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ActiveLearning PH</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles-admin-registration.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <jsp:include page="/data" />

        <!-- Pending Registration Applications -->
        <!-- TODO: Make Javascript to hide this message after action -->
        <% if (session.getAttribute("registration-message") != null) { %>
            <p><%= session.getAttribute("registration-message") %></p>
        <%
            session.setAttribute("registration-message", null);
            }
        %>
        <table>
            <tr>
                <th>Email</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Birthdate</th>
                <th>Accept/Deny</th>
            </tr>

        <%
            if (session.getAttribute("teacher") != null) {
                ArrayList<Teacher> teacher = (ArrayList<Teacher>) session.getAttribute("teacher");
                for (Teacher x : teacher) {
                    if (x.getStatus().equals("pending")) {
        %>
            <tr>
                <td><%= x.getEmail() %></td>
                <td><%= x.getLname() %></td>
                <td><%= x.getFname() %></td>
                <td><%= x.getBday() %></td>
                <td class="option">
                    <form action="signup-decision" method="POST">
                        <input name="username" value="<%= x.getEmail()%>" hidden/>
                        <button type="submit" name="decision" value="accept">Accept</button>
                    </form>
                    <form action="signup-decision" method="POST">
                        <input name="username" value="<%= x.getEmail()%>" hidden/>
                        <button type="submit" name="decision" value="reject">Reject</button>
                    </form>
                </td>
            </tr>
        <%
                    }
                }
            }
            else
                System.out.println("Null Value");
        %>
        </table>
        <jsp:include page="footer.jsp" />
    </body>
</html>
