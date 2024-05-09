package console;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import authentication.Security;

public class SignUpServlet extends HttpServlet {

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
        String role = request.getParameter("role");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirmpassword");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        Date bday = Date.valueOf(request.getParameter("bday"));

        String resume = request.getParameter("resume");
        
        // Inserted password is being encrypted
        String encryptedPassword =  sec.encrypt(password);       
        System.out.println("0) Encrypting Password ");
        System.out.println("-- Password: " + password);
        System.out.println("-- Encrypted Password: " + encryptedPassword);
               
        try {
            System.out.println("--- Initializing Preliminary Safety Protocols...");
            // Role Checking
            if (!role.equals("student") && !role.equals("teacher")) {
                System.out.println("-- Error: Invalid Signup");
                session.setAttribute("error", "Invalid Signup!");
                response.sendRedirect("signup.jsp");
            }
            // Password Checking
            if (!password.equals(confirmpassword)) {
                System.out.println("-- Error: Passwords do not match!");
                session.setAttribute("error", "Passwords do not match!");
                response.sendRedirect("signup.jsp");
                return;
            }
            // Bday Checking
//            if (bday.before()) {
//                if (bday.after(Date)) {
//                    System.out.println("-- Error: Cannot set birthday in the future!");
//                    session.setAttribute("error", "Cannot set birthday in the future!");
//                }
//                else {
//                    System.out.println("-- Error: Person is not at least 5 years old!");
//                    session.setAttribute("error", "Person is not at least 5 years old!");
//                }
//
//                response.sendRedirect("signup.jsp");
//            }


            
            // Load Driver & Establishing Connection
            Class.forName(derbyDriver);
            System.out.println("1) Loaded Driver: " + derbyDriver);
            Class.forName(mysqlDriver);
            System.out.println("1) Loaded Driver: " + mysqlDriver);

            Connection connDer = DriverManager.getConnection(derbyUrl, derbyUser, derbyPass);
            System.out.println("2) Connected to: " + derbyUrl);
            Connection connSql = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
            System.out.println("2) Connected to: " + mysqlUrl);

            // Inserting User in Database
            String queryDer = "INSERT INTO user_info VALUES (?, ?, ?)";
            String querySql = "INSERT INTO users VALUES (?, ?, ?)";
            PreparedStatement insertDer = connDer.prepareStatement(queryDer);
            PreparedStatement insertSql = connSql.prepareStatement(querySql);

            System.out.println("3) User " + username + " is being added !");
            insertDer.setString(1, username);
            insertDer.setString(2, encryptedPassword);
            insertDer.setString(3, role);
            insertSql.setString(1, username);
            insertSql.setString(2, encryptedPassword);
            insertSql.setString(3, role);
            int rows1 = insertDer.executeUpdate();
            int rows2 = insertSql.executeUpdate();

            if (rows1 == 0 || rows2 == 0) {
                System.out.println("-- Error: User already exists! ");
                session.setAttribute("error", "Email already exists!");
                response.sendRedirect("signup.jsp");
                return;
            }

            // Insert Info for Student / Teacher
            if (role.equals("student")) {
                String querySqlInfo = "INSERT INTO student VALUES (?, ?, ?, ?)";
                PreparedStatement insertSqlStudent = connSql.prepareStatement(querySqlInfo);
                insertSqlStudent.setString(1, username);
                insertSqlStudent.setString(2, fname);
                insertSqlStudent.setString(3, lname);
                insertSqlStudent.setDate(4, bday);

                insertSqlStudent.executeUpdate();
                insertSqlStudent.close();

                // Setting Session Information
                session.setAttribute("username", username);
                session.setAttribute("password", password);
                session.setAttribute("role", role);
                response.sendRedirect("student-mycourses.jsp");
            }
            else if (role.equals("teacher")) {
                String querySqlInfo = "INSERT INTO teacher VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertSqlTeacher = connSql.prepareStatement(querySqlInfo);
                insertSqlTeacher.setString(1, username);
                insertSqlTeacher.setString(2, fname);
                insertSqlTeacher.setString(3, lname);
                insertSqlTeacher.setDate(4, bday);
                insertSqlTeacher.setString(5, resume);
                insertSqlTeacher.setString(6, "pending");

                insertSqlTeacher.executeUpdate();
                insertSqlTeacher.close();
                response.sendRedirect("signup.jsp");
            }

            // Close the connection
            insertDer.close();
            insertSql.close();
            connDer.close();
            connSql.close();

        } catch (SQLException | ClassNotFoundException sqle) {
            sqle.printStackTrace();
            response.sendRedirect("signup.jsp");
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
