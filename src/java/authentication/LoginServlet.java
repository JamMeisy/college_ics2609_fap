package authentication;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import exceptions.AuthenticationException;

public class LoginServlet extends HttpServlet {

    String derbyDriver, derbyUrl, derbyUser, derbyPass;
    String key, cipher;
    Security sec;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();

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

        // Invalidating Previous Session if ever
        request.getSession().invalidate();

        System.out.println("---------------------------------------------");
        
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userCaptcha = request.getParameter("captcha");
        String generatedCaptcha = (String) session.getAttribute("captcha");
        
        // Password is being encrypted
        String encryptedPassword = sec.encrypt(password);
        System.out.println("0) Encrypting Password ");
        System.out.println("-- Password: " + password);
        System.out.println("-- Encrypted Password: " + encryptedPassword);
        
        try {
            // <editor-fold defaultstate="collapsed" desc="Login Authentication (Click to expand)">
            // Load Driver & Establishing Connection
            Class.forName(derbyDriver);
            System.out.println("1) Loaded Driver: " + derbyDriver);

            Connection conn = DriverManager.getConnection(derbyUrl, derbyUser, derbyPass);
            System.out.println("2) Connected to: " + derbyUrl);

            // Login Verification
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM user_info";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("3) Executed Query: " + query);

            System.out.println("4) Verifying Login Credentials");

            // Case 1: User is blank
            if (username.isEmpty()) {
                session.setAttribute("login-error", "No Login Credentials");
                throw new AuthenticationException("No Login Credentials");
            }

            boolean userExists = false;
            while (rs.next()) {
                String checkUser = rs.getString("username");
                if (username.equals(checkUser)) {
                    userExists = true;
                    break;
                }
            }
            
            // Case 2 & 3: User does not exist
            if (!userExists) {
                System.out.println("--- Username \"" + username + "\" does not exist");
                System.out.println("--- Password = \"" + password + "\"");
                
                // Case 2: No Password
                if (password.equals("")) {
                    session.setAttribute("login-error", "Incorrect Username, Blank Password");
                    throw new AuthenticationException("Incorrect Username, Blank Password");
                }

                // Case 3: Password is incorrect
                else {
                    session.setAttribute("login-error", "Incorrect Username, Incorrect Password");
                    throw new AuthenticationException("Incorrect Username, Incorrect Password");
                }
            }
            
            System.out.println("--- Username \"" + username + "\" exists!");
            String encryptedVerify = rs.getString("password");
            String role = rs.getString("role");
                
            // Case 4: Correct Username with Incorrect Password
            if (encryptedPassword == null || !encryptedPassword.equals(encryptedVerify)) {
                session.setAttribute("login-error", "Correct Username, Incorrect Password");
                throw new AuthenticationException("Correct Username, Incorrect Password");
            }

            // Case 5: Captcha Failed
            if (generatedCaptcha == null || !generatedCaptcha.equals(userCaptcha)) {
                session.setAttribute("login-error", "CAPTCHA verification failed");
                throw new AuthenticationException("CAPTCHA verification failed");
            }

            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setAttribute("role", role);

            System.out.println("--- Role set in session: " + role);

            System.out.println("5) Captcha & Credential Verification Successful");
            // </editor-fold>

            if (role.equals("admin"))
                response.sendRedirect("admin-registration.jsp");

            else if (role.equals("teacher"))
                response.sendRedirect("teacher-myclasses.jsp");

            else if (role.equals("student"))
                response.sendRedirect("student-mycourses.jsp");

            // Close the connection
            rs.close();
            stmt.close();
            conn.close();

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
