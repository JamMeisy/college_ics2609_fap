<%--
  Created by IntelliJ IDEA.
  User: Jam
  Date: 01/05/2024
  Time: 7:45 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="objects.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="exceptions.InvalidSessionException" %>

<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    System.out.println("-- Current User:" + username);
//    if (username == null || username.isEmpty()) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
//    if (!role.equals("admin")) {
//        throw new InvalidSessionException("Unauthorized Access");
//    }
%>

<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="/data"/>

        <!-- Admin Prompt -->
        <h2>Admin Console</h2>
        <%
            if (session.getAttribute("users") != null) {
                ArrayList<User> users = (ArrayList<User>) session.getAttribute("users"); %>
        <form action="admin-user" method="POST" id="insert">
            <h3>ADD ADMIN</h3>
            <label for="add-username">Username</label>
            <input type="email" name="username" id="add-username" required>

            <label for="add-password">Password</label>
            <input type="password" name="password" id="add-password" required>

            <label for="add-confirmpassword">Confirm Password</label>
            <input type="password" name="confirmpassword" id="add-confirmpassword" required>

            <input name="type" value="insert" hidden/>
            <button type="submit" form="insert">Insert</button>
        </form>

        <form action="admin-user" method="POST" id="update">
            <h3>Update User</h3>
            <label for="update-username">Username</label>
            <select name="username" id="update-username">
                <% for (User x : users) { %>
                    <option value='<%= x.getEmail() %>'><%=x.getEmail()%></option>
                <% } %>
            </select>

            <label for="update-password">Password</label>
            <input type="password" name="password" id="update-password" required>

            <label for="update-confirmpassword">Confirm Password</label>
            <input type="password" name="confirmpassword" id="update-confirmpassword" required>

            <input name="type" value="update" hidden/>
            <button type="submit" form="update">Update</button>
        </form>

        <form action="admin-user" method="POST" id="delete">
            <h3>Delete User</h3>
            <label for="delete-username">Username</label>
            <select name="username" id="delete-username">
                <% for (User x : users) { %>
                    <option value='<%= x.getEmail() %>'><%=x.getEmail()%></option>
                <% } %>
            </select>

            <input name="type" value="delete" hidden/>
            <button type="submit" form="delete">Delete</button>
        </form>

        <!-- All Users -->
        <table>
            <tr>
                <th>User</th>
                <th>Role</th>
            </tr>
            <%
                for (User x : users) {
            %>
            <tr>
                <td><%= x.getEmail() %></td>
                <td><%= x.getRole() %></td>
            </tr>
            <%
                    }
                } else
                    System.out.println("Null Value");
            %>
        </table>
    </body>
</html>
