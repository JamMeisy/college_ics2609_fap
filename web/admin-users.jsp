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
<%@ page import="authentication.Security" %>
<%@ page import="exceptions.AuthorizationException" %>

<%
    // Disable Caching
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies

    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");
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
        <link rel="stylesheet" href="css/styles-admin-user.css"/>
    </head>

    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="/data"/>
        <!-- Admin Prompt -->
        <h2>Admin Console</h2>
        <%
            if (session.getAttribute("users") != null) {
                ArrayList<User> users = (ArrayList<User>) session.getAttribute("users"); %>

        <% if (session.getAttribute("admin-insert-error") != null) {%>
        <h1><%= (String) session.getAttribute("admin-insert-error")%>
        </h1>
        <%
                session.setAttribute("admin-insert-error", null);
            }
        %>
        <div class="console">
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
                    <% for (User x : users) {%>
                    <option value='<%= x.getEmail()%>'><%=x.getEmail()%>
                    </option>
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
                    <% for (User x : users) {%>
                    <option value='<%= x.getEmail()%>'><%=x.getEmail()%>
                    </option>
                    <% }%>
                </select>

                <input name="type" value="delete" hidden/>
                <button type="submit" form="delete">Delete</button>
            </form>
        </div>
        <h2>Generate Users</h2>
        <div class="generate-wrapper">
            <form action="generate-report" method="POST">
                <input type="hidden" name="email" value="<%= username%>">
                <input type="hidden" name="password" value="<%= password%>">
                <input type="hidden" name="role" value="<%= role%>">

                <button type="submit" name="reportType" value="user_list">Generate User</button>
            </form>
        </div>

        <span></span>
        <h2>Generate Admin Record</h2>
        <div class="generate-wrapper">
            <form action="generate-report" method="POST">
                <input type="hidden" name="email" value="<%= username%>">
                <input type="hidden" name="password" value="<%= password%>">
                <input type="hidden" name="role" value="<%= role%>">

                <button type="submit" name="reportType" value="admin_record">Generate Admin Record</button>
            </form>
        </div>
        <span></span>
        <!-- All Users -->
        <h2>User Database</h2>
        <table>
            <tr>
                <th>User</th>
                <!-- For monitoring purposes only -->
                <%--                <th>Decrypted Password</th>--%>

                <th>Role</th>
            </tr>
            <%
                for (User x : users) {
            %>
            <tr>
                <td><%= x.getEmail()%>
                </td>
                <!-- For monitoring purposes only -->
                <%--                <%--%>
                <%--                    String key = request.getServletContext().getInitParameter("key");--%>
                <%--                    String cipher = request.getServletContext().getInitParameter("cipher");--%>
                <%--                    Security sec = new Security(key, cipher);--%>
                <%--                %>--%>
                <%--                <td><%= sec.decrypt(x.getPassword())%>--%>
                <%--                </td>--%>

                <td><%= x.getRole() %>
                </td>
            </tr>
            <%
                    }
                } else
                    System.out.println("Null Value");
            %>
        </table>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
