/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package console;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import objects.*;

/**
 *
 * @author Jam
 */
public class DataServlet extends HttpServlet {

    String mysqlDriver, mysqlUrl, mysqlUser, mysqlPass;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mysqlDriver = getServletContext().getInitParameter("mysqlDriver");
        mysqlUrl = getServletContext().getInitParameter(" mysqlUrl");
        mysqlUser = getServletContext().getInitParameter("mysqlUser");
        mysqlPass = getServletContext().getInitParameter("mysqlPass");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("---------------------------------------------");
        try {
            // Load Driver & Establishing Connection
            Class.forName(mysqlDriver);
            System.out.println("1) Loaded Driver: " + mysqlDriver);
            Connection conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
            System.out.println("2) Connected to: " + mysqlUrl);

            // Transfer data
            Statement stmt = conn.createStatement();
            String query1 = "SELECT * FROM users ORDER BY email ASC";
            String query2 = "SELECT * FROM student ORDER BY users_email ASC";
            String query3 = "SELECT * FROM teacher ORDER BY users_email ASC";
            String query4 = "SELECT * FROM courses ORDER BY course_name ASC";
            String query5 = "SELECT * FROM teacher_courses ORDER BY teacher_users_email ASC";
            String query6 = "SELECT * FROM schedule ORDER BY date ASC";

            ResultSet rs1 = stmt.executeQuery(query1);
            ResultSet rs2 = stmt.executeQuery(query2);
            ResultSet rs3 = stmt.executeQuery(query3);
            ResultSet rs4 = stmt.executeQuery(query4);
            ResultSet rs5 = stmt.executeQuery(query5);
            ResultSet rs6 = stmt.executeQuery(query6);

            System.out.println("3) Executed Queries:");
            System.out.println(query1);
            System.out.println(query2);
            System.out.println(query3);
            System.out.println(query4);
            System.out.println(query5);
            System.out.println(query6);

            ArrayList<User> users = new ArrayList<>();
            ArrayList<Student> student = new ArrayList<>();
            ArrayList<Teacher> teacher = new ArrayList<>();
            ArrayList<Courses> courses = new ArrayList<>();
            ArrayList<TeacherCourses> teacher_courses = new ArrayList<>();
            ArrayList<Schedule> schedule = new ArrayList<>();

            System.out.println("4) Recording Queries...");

            while (rs1.next())
                users.add(new User(
                        rs1.getString("email"),
                        rs1.getString("password"),
                        rs1.getString("role")));

            while (rs2.next())
                student.add(new Student(
                        rs2.getString("users_email"),
                        rs2.getString("firstname"),
                        rs2.getString("lastname"),
                        rs2.getDate("birthdate")));

            while (rs3.next())
                teacher.add(new Teacher(
                        rs3.getString("users_email"),
                        rs3.getString("firstname"),
                        rs3.getString("lastname"),
                        rs3.getString("resume"),
                        rs3.getString("status"),
                        rs3.getDate("birthdate")));

            while (rs4.next())
                courses.add(new Courses(
                        rs4.getString("course_name"),
                        rs4.getString("course_description"),
                        rs4.getString("course_difficulty"),
                        rs4.getString("course_hours")));

            while (rs5.next())
                teacher_courses.add(new TeacherCourses(
                        rs5.getString("teacher_users_email"),
                        rs5.getString("courses_course_name")));

            while (rs6.next())
                schedule.add(new Schedule(
                        rs6.getString("STUDENT_USERS_email"),
                        rs6.getString("TEACHER_USERS_email"),
                        rs6.getString("COURSES_course_name"),
                        rs6.getString("status"),
                        rs6.getDate("date")));

            System.out.println(users);
            System.out.println(student);
            System.out.println(teacher);
            System.out.println(courses);
            System.out.println(teacher_courses);
            System.out.println(schedule);

            // Close the connection
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            rs5.close();
            rs6.close();
            stmt.close();
            conn.close();

            System.out.println("5) Data recorded... Transferring data");

            HttpSession session = request.getSession();

            session.setAttribute("users", users);
            session.setAttribute("users", users);
            session.setAttribute("users", users);
            session.setAttribute("users", users);
            session.setAttribute("users", users);
            session.setAttribute("users", users);

            String page = (String) session.getAttribute("page");

            request.getRequestDispatcher(page).forward(request, response);
            System.out.println("6) Data transferred successfully!");
            System.out.println("7) Reloading current page.");

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
