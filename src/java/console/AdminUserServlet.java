package console;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import authentication.Security;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class AdminUserServlet extends HttpServlet {

    String derbyDriver, derbyUrl, derbyUser, derbyPass;
    String mysqlDriver, mysqlUrl, mysqlUser, mysqlPass;
    String key, cipher;
    Security sec;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();

        mysqlDriver = getServletContext().getInitParameter("mysqlDriver");
        mysqlUrl = getServletContext().getInitParameter("mysqlUrl");
        mysqlUser = getServletContext().getInitParameter("mysqlUser");
        mysqlPass = getServletContext().getInitParameter("mysqlPass");

        derbyDriver = context.getInitParameter("derbyDriver");
        derbyUrl = context.getInitParameter("derbyUrl");
        derbyUser = context.getInitParameter("derbyUser");
        derbyPass = context.getInitParameter("derbyPass");

        // Authentication
        key = context.getInitParameter("key");
        cipher = context.getInitParameter("cipher");
        sec = new Security(key, cipher);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        
        System.out.println("---------------------------------------------");

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String type = request.getParameter("type");

        // Role Checking
//            if (!role.equals("teacher")) {
//                System.out.println("-- Error: Invalid ");
//                session.setAttribute("error", "Invalid Signup!");
//            }

        try {
            // Load Driver & Establishing Connection
            Class.forName(derbyDriver);
            System.out.println("1) Loaded Driver: " + derbyDriver);
            Class.forName(mysqlDriver);
            System.out.println("1) Loaded Driver: " + mysqlDriver);

            Connection connDer = DriverManager.getConnection(derbyUrl, derbyUser, derbyPass);
            System.out.println("2) Connected to: " + derbyUrl);
            Connection connSql = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
            System.out.println("2) Connected to: " + mysqlUrl);

            // <editor-fold defaultstate="collapsed" desc="Insert ADMIN Method (Click to expand)">
            if (type.equals("insert")) {
                // Initializing Parameters and Encryption
                String password = request.getParameter("password");
                String confirmpassword = request.getParameter("confirmpassword");
                String encryptedPassword =  sec.encrypt(password);       
                System.out.println("#) Encrypting Password ");
                System.out.println("-- Password: " + password);
                System.out.println("-- Encrypted Password: " + encryptedPassword);
                
                System.out.println("--- Initializing Preliminary Safety Protocols...");
                // Password Checking
                if (!password.equals(confirmpassword)) {
                    System.out.println("-- Error: Passwords do not match!");
                    session.setAttribute("error", "Passwords do not match!");
                    response.sendRedirect("admin-users.jsp");
                    return;
                }

                // Inserting User in Database
                String queryDer = "INSERT INTO user_info VALUES (?, ?, ?)";
                String querySql = "INSERT INTO users VALUES (?, ?, ?)";
                PreparedStatement stmtDer = connDer.prepareStatement(queryDer);
                PreparedStatement stmtSql = connSql.prepareStatement(querySql);

                System.out.println("3) Admin User " + username + " is being added !");
                stmtDer.setString(1, username);
                stmtDer.setString(2, encryptedPassword);
                stmtDer.setString(3, "admin");
                stmtSql.setString(1, username);
                stmtSql.setString(2, encryptedPassword);
                stmtSql.setString(3, "admin");
                int rows1 = stmtDer.executeUpdate();
                int rows2 = stmtSql.executeUpdate();

                if (rows1 == 0 || rows2 == 0) {
                    System.out.println("-- Error: Admin User already exists! ");
                    session.setAttribute("error", "Email already exists!");
                    response.sendRedirect("admin-users.jsp");
                    return;
                }

                stmtDer.close();
                stmtSql.close();
                response.sendRedirect("admin-users.jsp");
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Update Method (Click to expand)">
            if (type.equals("update")) {
                // Initializing Parameters and Encryption
                String password = request.getParameter("password");
                String confirmpassword = request.getParameter("confirmpassword");
                String encryptedPassword =  sec.encrypt(password);       
                System.out.println("#) Encrypting Password ");
                System.out.println("-- Password: " + password);
                System.out.println("-- Encrypted Password: " + encryptedPassword);
                
                System.out.println("--- Initializing Preliminary Safety Protocols...");
                // Password Checking
                if (!password.equals(confirmpassword)) {
                    System.out.println("-- Error: Passwords do not match!");
                    session.setAttribute("error", "Passwords do not match!");
                    response.sendRedirect("admin-users.jsp");
                    return;
                }

                // Updating User in Database
                String queryDer = "UPDATE user_info SET password=? WHERE username=?";
                String querySql = "UPDATE users SET password=? WHERE email=?";
                PreparedStatement stmtDer = connDer.prepareStatement(queryDer);
                PreparedStatement stmtSql = connSql.prepareStatement(querySql);

                System.out.println("3) Admin User " + username + " is being updated !");
                stmtDer.setString(1, encryptedPassword);
                stmtDer.setString(2, username);
                stmtSql.setString(1, encryptedPassword);
                stmtSql.setString(2, username);
                int rows1 = stmtDer.executeUpdate();
                int rows2 = stmtSql.executeUpdate();

                if (rows1 == 0 || rows2 == 0) {
                    System.out.println("-- Error: Cannot update admin user, something went wrong! ");
                    session.setAttribute("error", "Updating user went wrong!");
                    response.sendRedirect("admin-users.jsp");
                    return;
                }

                stmtDer.close();
                stmtSql.close();
                response.sendRedirect("admin-users.jsp");
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Delete Method (Click to expand)">
            if (type.equals("delete")) {
                System.out.println("--- Initializing Preliminary Safety Protocols...");
                // Password Checking
                if (session.getAttribute("username").equals(username)) {
                    System.out.println("-- Error: Cannot delete current admin user!");
                    session.setAttribute("error", "Cannot delete current admin user!");
                    response.sendRedirect("admin-users.jsp");
                    return;
                }

                // Delete User from Database
                String queryDer = "DELETE FROM user_info WHERE username=?";
                String querySql = "DELETE FROM users WHERE email=?";
                PreparedStatement stmtDer = connDer.prepareStatement(queryDer);
                PreparedStatement stmtSql = connSql.prepareStatement(querySql);

                System.out.println("3) Admin User " + username + " is being deleted !");
                stmtDer.setString(1, username);
                stmtSql.setString(1, username);
                int rows1 = stmtDer.executeUpdate();
                int rows2 = stmtSql.executeUpdate();

                if (rows1 == 0 || rows2 == 0) {
                    System.out.println("-- Error: Cannot delete admin user, something went wrong! ");
                    session.setAttribute("error", "Deleting user went wrong!");
                    response.sendRedirect("admin-users.jsp");
                    return;
                }

                stmtDer.close();
                stmtSql.close();
                response.sendRedirect("admin-users.jsp");
            }
            // </editor-fold>

            // Close the connection
            connDer.close();
            connSql.close();

        } catch (SQLException | ClassNotFoundException sqle) {
            sqle.printStackTrace();
            response.sendRedirect("admin-users.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
