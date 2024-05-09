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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
        LocalDate date1 = LocalDate.parse(request.getParameter("date1"));        
        LocalDate date2 = LocalDate.parse(request.getParameter("date2"));        
        LocalDate date3 = LocalDate.parse(request.getParameter("date3"));        
        LocalDate date4 = LocalDate.parse(request.getParameter("date4")); 
               
        try {
            System.out.println("--- Initializing Preliminary Safety Protocols...");

            // Role Checking
//            if (!role.equals("student")) {
//                System.out.println("-- Error: Invalid Signup");
//                session.setAttribute("error", "Invalid Signup!");
//            }

            // TODO: DATE CHECKING RIX
            // Date Checking
            DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();

            //PRINTS
            System.out.println("-- Printing Dates...");
            System.out.println("Current Date : " + localDate);
            System.out.println("User Input Dates : " + date1 + ", " +date2+ ", " +date3 + ", " +date4);
            System.out.println("-- Comparing Dates from User Input");

            if (date1.isBefore(localDate)){
                System.out.println("-- Error: Date 1 should not be in the PAST!");
                session.setAttribute("signup-error", "Date 1 should not be in the PAST!");
                response.sendRedirect("signup.jsp");
            } else if (date2.isBefore(localDate)){
                System.out.println("-- Error: Date 2 should not be in the PAST!");
                session.setAttribute("signup-error", "Date 2 should not be in the PAST!");
                response.sendRedirect("signup.jsp");
            } else if (date3.isBefore(localDate)){
                System.out.println("-- Error: Date 3 should not be in the PAST!");
                session.setAttribute("signup-error", "Date 3 should not be in the PAST!");
                response.sendRedirect("signup.jsp");
            } else if (date4.isBefore(localDate)){
                System.out.println("-- Error: Date 3 should not be in the PAST!");
                session.setAttribute("signup-error", "Date 3 should not be in the PAST!");
                response.sendRedirect("signup.jsp");
            } else if (date1.isEqual(localDate)){
                System.out.println("-- Error: Date 1 should not be equal to PRESENT!");
                session.setAttribute("signup-error", "Date 1 should not be equal to PRESENT!");
                response.sendRedirect("signup.jsp");
            } else if (date2.isEqual(localDate)){
                System.out.println("-- Error: Date 2 should not be equal to PRESENT!");
                session.setAttribute("signup-error", "Date 2 should not be equal to PRESENT!");
                response.sendRedirect("signup.jsp");
            } else if (date3.isEqual(localDate)){
                System.out.println("-- Error: Date 3 should not be equal to PRESENT!");
                session.setAttribute("signup-error", "Date 3 should not be equal to PRESENT!");
                response.sendRedirect("signup.jsp");
            } else if (date4.isEqual(localDate)){
                System.out.println("-- Error: Date 4 should not be equal to PRESENT!");
                session.setAttribute("signup-error", "Date 4 should not be equal to PRESENT!");
                response.sendRedirect("signup.jsp");
            } else {
                System.out.println("signup-success: Dates are VALID!");
            }
            
            java.sql.Date dateone = java.sql.Date.valueOf(date1);
            java.sql.Date datetwo = java.sql.Date.valueOf(date2);
            java.sql.Date datethree = java.sql.Date.valueOf(date3);
            java.sql.Date datefour = java.sql.Date.valueOf(date4);

            // Load Driver & Establishing Connection
            Class.forName(mysqlDriver);
            System.out.println("1) Loaded Driver: " + mysqlDriver);

            Connection connSql = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
            System.out.println("2) Connected to: " + mysqlUrl);

            // Setting Up Request to Insert in Database
            String query = "INSERT INTO schedule (STUDENT_USERS_email_schedule, TEACHER_USERS_email_schedule, " +
                    "COURSES_course_name_schedule, DATE, STATUS) VALUES (?, ?, ?, ?, 'pending')";
            PreparedStatement insert = connSql.prepareStatement(query);

            System.out.println("3) Schedule request of " + student + " is being added !");
            insert.setString(1, student);
            insert.setString(2, teacher);
            insert.setString(3, course);

            // Inserting the Schedule
            insert.setDate(4, dateone);
            insert.executeUpdate();
            insert.setDate(4, datetwo);
            insert.executeUpdate();
            insert.setDate(4, datethree);
            insert.executeUpdate();
            insert.setDate(4, datefour);
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
