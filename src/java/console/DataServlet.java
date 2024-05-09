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
        mysqlUrl = getServletContext().getInitParameter("mysqlUrl");
        mysqlUser = getServletContext().getInitParameter("mysqlUser");
        mysqlPass = getServletContext().getInitParameter("mysqlPass");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("---------------------------------------------");
        try {
            // Load Driver & Establishing Connection
            System.out.println("1) Loading Driver: " + mysqlDriver);
            Class.forName(mysqlDriver);
            System.out.println("2) Connecting to: " + mysqlUrl);
            Connection conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);

            // Transfer data
            Statement stmt = conn.createStatement();
            String query1 = "SELECT * FROM users ORDER BY email ASC";
            String query2 = "SELECT * FROM student ORDER BY USERS_student_email ASC";
            String query3 = "SELECT * FROM teacher ORDER BY USERS_teacher_email ASC";
            String query4 = "SELECT * FROM courses ORDER BY course_name ASC";
            String query5 = "SELECT * FROM teacher_courses ORDER BY teacher_users_email ASC";
            String query6 = "SELECT * FROM schedule ORDER BY date ASC";

            System.out.println("3) Executing & Recording Queries:");
            ResultSet rs;

            rs = stmt.executeQuery(query1);
            System.out.println(query1);
            ArrayList<User> users = new ArrayList<>();
            while (rs.next())
                users.add(new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")));

            System.out.println(query2);
            rs = stmt.executeQuery(query2);
            ArrayList<Student> student = new ArrayList<>();
            while (rs.next())
                student.add(new Student(
                        rs.getString("USERS_student_email"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birthdate")));

            System.out.println(query3);
            rs = stmt.executeQuery(query3);
            ArrayList<Teacher> teacher = new ArrayList<>();
            while (rs.next())
                teacher.add(new Teacher(
                        rs.getString("USERS_teacher_email"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("resume"),
                        rs.getString("status"),
                        rs.getDate("birthdate")));

            System.out.println(query4);
            rs = stmt.executeQuery(query4);
            ArrayList<Courses> courses = new ArrayList<>();
            while (rs.next())
                courses.add(new Courses(
                        rs.getString("course_name"),
                        rs.getString("course_description"),
                        rs.getString("course_difficulty"),
                        rs.getString("course_hours")));

            System.out.println(query5);
            rs = stmt.executeQuery(query5);
            ArrayList<TeacherCourses> teacher_courses = new ArrayList<>();
            while (rs.next())
                teacher_courses.add(new TeacherCourses(
                        rs.getString("TEACHER_USERS_email"),
                        rs.getString("COURSES_course_name")));

            System.out.println(query6);
            rs = stmt.executeQuery(query6);
            ArrayList<Schedule> schedule = new ArrayList<>();
            while (rs.next())
                schedule.add(new Schedule(
                        rs.getInt("entry"),
                        rs.getString("STUDENT_USERS_email_schedule"),
                        rs.getString("TEACHER_USERS_email_schedule"),
                        rs.getString("COURSES_course_name_schedule"),
                        rs.getString("status"),
                        rs.getDate("date")));

            // Close the connection
            rs.close();
            stmt.close();
            conn.close();

            System.out.println("4) Data recorded... Transferring data");

            HttpSession session = request.getSession();

            session.setAttribute("users", users);
            session.setAttribute("student", student);
            session.setAttribute("teacher", teacher);
            session.setAttribute("courses", courses);
            session.setAttribute("teacher_courses", teacher_courses);
            session.setAttribute("schedule", schedule);

            System.out.println("5) Data transferred successfully!");

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
