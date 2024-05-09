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

public class CourseRequestServlet extends HttpServlet {

    String mysqlDriver, mysqlUrl, mysqlUser, mysqlPass;
    Security sec;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();

        mysqlDriver = getServletContext().getInitParameter("mysqlDriver");
        mysqlUrl = getServletContext().getInitParameter("mysqlUrl");
        mysqlUser = getServletContext().getInitParameter("mysqlUser");
        mysqlPass = getServletContext().getInitParameter("mysqlPass");

    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        
        System.out.println("---------------------------------------------");

        // User Info
        HttpSession session = request.getSession();
        String student = (String) request.getSession().getAttribute("username");
        String role = (String) request.getSession().getAttribute("role");

        // Course Request Parameters
        String teacher = request.getParameter("teacher");
        String course = request.getParameter("course");
        java.sql.Date date1 = java.sql.Date.valueOf(request.getParameter("date1"));
        java.sql.Date date2 = java.sql.Date.valueOf(request.getParameter("date2"));
        java.sql.Date date3 = java.sql.Date.valueOf(request.getParameter("date3"));
        java.sql.Date date4 = java.sql.Date.valueOf(request.getParameter("date4"));
               
        try {
            System.out.println("--- Initializing Preliminary Safety Protocols...");

            // Role Checking
            if (!role.equals("student")) {
                System.out.println("-- Error: Invalid Signup");
                session.setAttribute("error", "Invalid Signup!");
            }

            // TODO: DATE CHECKING RIX
            // Date Checking
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
            Class.forName(mysqlDriver);
            System.out.println("1) Loaded Driver: " + mysqlDriver);

            Connection connSql = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
            System.out.println("2) Connected to: " + mysqlUrl);

            // Setting Up Request to Insert in Database
            String query = "INSERT INTO schedule (STUDENT_USERS_EMAIL, TEACHER_USERS_EMAIL, " +
                    "COURSES_COURSE_NAME, DATE, STATUS) VALUES (?, ?, ?, ?, 'pending')";
            PreparedStatement insert = connSql.prepareStatement(query);

            System.out.println("3) Schedule request of " + student + " is being added !");
            insert.setString(1, student);
            insert.setString(2, teacher);
            insert.setString(3, course);

            // Inserting the Schedule
            insert.setDate(4, date1);
            insert.executeUpdate();
            insert.setDate(4, date2);
            insert.executeUpdate();
            insert.setDate(4, date3);
            insert.executeUpdate();
            insert.setDate(4, date4);
            insert.executeUpdate();

            // Close the connection
            insert.close();
            connSql.close();
            response.sendRedirect("student-mycourses.jsp");
        } catch (SQLException | ClassNotFoundException sqle) {
            sqle.printStackTrace();
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
