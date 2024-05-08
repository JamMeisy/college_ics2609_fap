package console;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseRequestDecisionServlet extends HttpServlet {

    String mysqlDriver, mysqlUrl, mysqlUser, mysqlPass;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        mysqlDriver = getServletContext().getInitParameter("mysqlDriver");
        mysqlUrl = getServletContext().getInitParameter("mysqlUrl");
        mysqlUser = getServletContext().getInitParameter("mysqlUser");
        mysqlPass = getServletContext().getInitParameter("mysqlPass");

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String decision = request.getParameter("decision");
        String role = (String) request.getSession().getAttribute("role");

        try {
            System.out.println("1) Initializing Preliminary Safety Protocols...");
//            if (role == null || !role.equals("admin"))
//                throw new InvalidSessionException("Unauthorized Action");


            // Load Driver & Establishing Connection
            Class.forName(mysqlDriver);
            System.out.println("2) Loaded Driver: " + mysqlDriver);
            Connection conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
            System.out.println("3) Connected to: " + mysqlUrl);

            // Accept User
            String query;
            if (decision.equals("accept"))
                query = "UPDATE teacher SET status='approved' WHERE USERS_email=?";
            else
                query = "UPDATE teacher SET status='rejected' WHERE USERS_email=?";
            PreparedStatement update = conn.prepareStatement(query);
            update.setString(1, username);
            int rows = update.executeUpdate();

            if (rows > 0) {
                System.out.println("4) Teacher " + username + " has been updated successfully!");
                String message = username + " has been " + decision + "ed!";
                request.getSession().setAttribute("message", message);
            } else {
                System.out.println("-- Error: Something went wrong! ");
            }

            // Close the connection
            update.close();
            conn.close();
            response.sendRedirect("admin-registration.jsp");
        } catch (SQLException | ClassNotFoundException sqle) {
            sqle.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
