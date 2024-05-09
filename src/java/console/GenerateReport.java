/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package console;

import authentication.Security;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import exceptions.InvalidSessionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import objects.Schedule;
import objects.User;

/**
 *
 * @author Lejan Juico
 */
public class GenerateReport extends HttpServlet {

    String driver, url, dbuser, dbpass, key, cipher;
    Security sec;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        driver = getServletContext().getInitParameter("mysqlDriver");
        url = getServletContext().getInitParameter("mysqlUrl");
        dbuser = getServletContext().getInitParameter("mysqlUser");
        dbpass = getServletContext().getInitParameter("mysqlPass");
        key = getServletContext().getInitParameter("key");
        cipher = getServletContext().getInitParameter("cipher");
        sec = new Security(key, cipher);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        // Generating Data
        System.out.println("LOADING REPORT / CREDENTIALS");
        System.out.println("---------------------------------------------");
        try {
            // Load Driver & Establishing Connection
            Class.forName(driver);
            System.out.println("1) Loaded Driver: " + driver);
            ArrayList<User> data;
            ArrayList<Schedule> schedule;
            try (Connection conn = DriverManager.getConnection(url, dbuser, dbpass)) {
                System.out.println("2) Connected to: " + url);
                // Transfer data for users
                Statement stmtUsers = conn.createStatement();
                String queryUsers = "SELECT * FROM users ORDER BY email ASC";
                ResultSet rsUsers = stmtUsers.executeQuery(queryUsers);
                System.out.println("3) Executed Query: " + queryUsers);
                data = new ArrayList<>();
                System.out.println("4) Recording Users...");
                while (rsUsers.next()) {
                    data.add(new User(rsUsers.getString("email"), rsUsers.getString("password"), rsUsers.getString("role")));
                }   // Close the users result set and statement
                rsUsers.close();
                stmtUsers.close();
                // Transfer data for schedule
                Statement stmtSchedule = conn.createStatement();
                String querySchedule = "SELECT * FROM schedule ORDER BY TEACHER_USERS_email";
                ResultSet rsSchedule = stmtSchedule.executeQuery(querySchedule);
                System.out.println("5) Executed Query: " + querySchedule);
                schedule = new ArrayList<>();
                System.out.println("6) Recording Schedule...");
                while (rsSchedule.next()) {
                    schedule.add(new Schedule(rsSchedule.getString("STUDENT_USERS_email"), rsSchedule.getString("TEACHER_USERS_email"),
                            rsSchedule.getString("COURSES_course_name"), rsSchedule.getString("status"), rsSchedule.getDate("date")));
                }   // Close the schedule result set and statement
                rsSchedule.close();
                stmtSchedule.close();
            }

            System.out.println("5) Data recorded... Generating...");

            /* 
            String username = (String) request.getSession().getAttribute("username");
            String userRole = (String) request.getSession().getAttribute("role");
            String userPass = (String) request.getSession().getAttribute("password"); 
            String reportType = request.getParameter("reportType");*/
            // SAMPLE USER ONLY
            String ex_email = "cherry@gmail.com";
            String ex_password = "teacher";
            String ex_role = "teacher";
            String ex_reportType = "schedule_teacher";    // OTHER CHOICE: schedule_student, schedule_teachers, user_list;

            // Retrieve header and footer from web.xml
            String pdfHeader = getServletContext().getInitParameter("pdfHeader");
            String pdfFooter = getServletContext().getInitParameter("pdfFooter");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormatRange = new SimpleDateFormat("yyyy-MM-dd");
            String dateTime = dateFormat.format(new Date());

            /* GET START AND END DATE PARAMETER
            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate"); */
            
            // SAMPLE DATE RANGE
            Date startDate = dateFormatRange.parse("2024-05-05");
            Date endDate = dateFormatRange.parse("2024-05-07");

            /* Date startDate = null;
            Date endDate = null;*/

            /* try {
            startDate = dateFormat.parse(startDateString);
            endDate = dateFormat.parse(endDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            // SECURITY
            /* boolean authorized = false;
            for (User i : data) {
                if (i.getEmail().equals(ex_email)) {
                    if (i.getPassword().equals(sec.encrypt(ex_password))) {
                        authorized = true;
                        break;
                    } else {
                        break;
                    }
                }
            }

            if (!authorized) {
                throw new InvalidSessionException("Unauthorized Access");
            }  */
            
            try {
                response.setContentType("application/pdf");

                Document document = new Document(PageSize.A4.rotate());
                PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

                switch (ex_reportType) {
                    case "schedule_admin":
                        String title_sched_admin = "Schedule List Report";
                        String filename_sched_admin = "schedule_list_" + dateFormat.format(new Date()) + ".pdf";
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename_sched_admin + "\"");

                        PdfHeaderFooter headerFooter_sched_admin = new PdfHeaderFooter(title_sched_admin, ex_email, schedule.size(), 8, dateTime, pdfFooter, pdfHeader, ex_role, null, schedule, startDate, endDate);
                        writer.setPageEvent(headerFooter_sched_admin);

                        document.open();
                        generateScheduleAdminReport(response, document, writer, ex_email, schedule, startDate, endDate);
                        document.close();

                        break;

                    case "user_list":
                        // Generate header here
                        String title = "User List Report";
                        String filename_user_list = "user_list_" + dateFormat.format(new Date()) + ".pdf";
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename_user_list + "\"");

                        PdfHeaderFooter headerFooter_user_list = new PdfHeaderFooter(title, ex_email, data.size(), 15, dateTime, pdfFooter, pdfHeader, ex_role, data, null, null, null);
                        writer.setPageEvent(headerFooter_user_list);
                        document.open();
                        // Call the method to generate user list report
                        generateUserListReport(response, document, ex_email, data);
                        document.close();

                        break;

                    case "schedule_student":
                        String title_sched_student = "Student Schedule";
                        String filename_sched_student = "student_schedule_" + dateFormat.format(new Date()) + ".pdf";
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename_sched_student + "\"");

                        PdfHeaderFooter headerFooter_sched_student = new PdfHeaderFooter(title_sched_student, ex_email, schedule.size(), 8, dateTime, pdfFooter, pdfHeader, ex_role, null, schedule, startDate, endDate);
                        writer.setPageEvent(headerFooter_sched_student);
                        document.open();

                        generateStudentReport(response, document, writer, ex_email, schedule, startDate, endDate);
                        document.close();
                        break;

                    case "schedule_teacher":
                        String title_sched_teacher = "Teacher Schedule";
                        String filename_sched_teacher = "teacher_schedule_" + dateFormat.format(new Date()) + ".pdf";
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename_sched_teacher + "\"");

                        PdfHeaderFooter headerFooter_sched_teacher = new PdfHeaderFooter(title_sched_teacher, ex_email, schedule.size(), 8, dateTime, pdfFooter, pdfHeader, ex_role, null, schedule, startDate, endDate);
                        writer.setPageEvent(headerFooter_sched_teacher);
                        document.open();

                        generateTeacherReport(response, document, writer, ex_email, schedule, startDate, endDate);
                        document.close();
                        break;

                    default:
                        response.getWriter().println("Invalid report type.");
                        break;
                }
            } catch (DocumentException | IOException e) {
                // Handle any exceptions that occur during processing
                response.getWriter().println("An error occurred: " + e.getMessage());
            }
        } catch (SQLException | ClassNotFoundException sqle) {
            response.getWriter().println("Error generating report.");
        }
    }

    public void generateScheduleAdminReport(HttpServletResponse response, Document document, PdfWriter writer, String loggedInUser, ArrayList<Schedule> schedule, Date startDate, Date endDate) throws IOException {
        try {
            // Define column widths
            float[] columnWidths = {40f, 40f, 40f, 40f, 40f}; // Adjust column widths as needed

            // Create Table with column widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100); // Width 100%
            table.setSpacingBefore(10f); // Space before table

            // Add data from the schedule ArrayList to table cells
            int cellCount = 0;
            boolean isFirstRow = true;
            addHeaderRowToTable(table);

            for (Schedule s : schedule) {
                Date scheduleDate = s.getSchedule();

                // Check if the schedule date falls within the specified date range
                if ((startDate == null || scheduleDate.after(startDate) || scheduleDate.equals(startDate))
                        && (endDate == null || scheduleDate.before(endDate) || scheduleDate.equals(endDate))) {

                    // Add header row for the first row of each page
                    if (isFirstRow) {
                        isFirstRow = false;
                    }

                    // Add data rows
                    addCellToTable(new PdfPCell(new Phrase(s.getTeacherEmail())), table, false);
                    addCellToTable(new PdfPCell(new Phrase(s.getStudentEmail())), table, false);
                    addCellToTable(new PdfPCell(new Phrase(s.getCourse())), table, false);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(scheduleDate);

                    addCellToTable(new PdfPCell(new Phrase(formattedDate)), table, false);
                    addCellToTable(new PdfPCell(new Phrase(s.getStatus())), table, false);

                    // Increment cell count
                    cellCount++;

                    // Check if the cell count reaches 8
                    if (cellCount >= 8) {
                        document.add(table);
                        document.newPage();
                        table.deleteBodyRows();
                        cellCount = 0;
                        isFirstRow = true; // Reset isFirstRow for the next page

                        // Add header row for the next page
                        addHeaderRowToTable(table);
                    }
                }
            }

            // Add the remaining rows if any
            if (cellCount > 0) {
                document.add(table);
            }

            // Close Document
            document.close();
        } catch (DocumentException e) {
            response.getWriter().println("An error occurred while generating the PDF: " + e.getMessage());
        }
    }

    private void addHeaderRowToTable(PdfPTable table) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

        PdfPCell cellTeacher = new PdfPCell(new Phrase("Teacher", headerFont));
        PdfPCell cellStudent = new PdfPCell(new Phrase("Student", headerFont));
        PdfPCell cellCourse = new PdfPCell(new Phrase("Course", headerFont));
        PdfPCell cellDate = new PdfPCell(new Phrase("Date", headerFont));
        PdfPCell cellStatus = new PdfPCell(new Phrase("Status", headerFont));

        addCellToTable(cellTeacher, table, true);
        addCellToTable(cellStudent, table, true);
        addCellToTable(cellCourse, table, true);
        addCellToTable(cellDate, table, true);
        addCellToTable(cellStatus, table, true);
    }

    private void addCellToTable(PdfPCell cell, PdfPTable table, boolean isHeader) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(8);
        cell.setFixedHeight(40);
        cell.setBorderWidth(1f);
        cell.setBorderColor(BaseColor.BLACK);

        // Set background color
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Header row color
        } else {
            cell.setBackgroundColor(BaseColor.WHITE); // Non-header row color
        }

        table.addCell(cell);
    }

    public void generateUserListReport(HttpServletResponse response, Document document, String loggedInUser, ArrayList<User> data) throws IOException {
        try {
            // Define column widths
            float[] columnWidths = {8f, 40f, 20f};

            // Create Table with column widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100); // Width 100%
            table.setSpacingBefore(10f); // Space before table

            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 15);

            // Add Header Cells to Table
            addUserListHeaderRowToTable(table);

            int cellCount = 0;
            int numCell = 1;
            for (User user : data) {
                // Add data to table cells
                PdfPCell numCellCell = new PdfPCell(new Phrase(String.valueOf(numCell), regularFont));
                numCellCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell usernameCell = new PdfPCell(new Phrase(user.getEmail(), regularFont));
                usernameCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell roleCell = new PdfPCell(new Phrase(user.getRole(), regularFont));
                roleCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Check if the current user corresponds to the logged-in user
                if (loggedInUser != null && loggedInUser.equals(user.getEmail())) {
                    usernameCell.setPhrase(new Phrase(user.getEmail() + "*", regularFont));
                }

                // Add cells to the table
                table.addCell(numCellCell);
                table.addCell(usernameCell);
                table.addCell(roleCell);

                numCell++;
                cellCount++;

                if (cellCount % 15 == 0) {
                    // If cellCount is a multiple of 15, add header row
                    document.add(table);
                    document.newPage();
                    table.deleteBodyRows();
                    addUserListHeaderRowToTable(table); // Add header row for the next page
                }
            }

            // Add remaining rows if any
            if (cellCount % 15 != 0) {
                document.add(table);
            }

            // Close Document
            document.close();
        } catch (DocumentException e) {
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }

    private void addUserListHeaderRowToTable(PdfPTable table) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

        PdfPCell cellNum = new PdfPCell(new Phrase("No.", headerFont));
        cellNum.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellNum.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellNum.setPadding(5);
        cellNum.setFixedHeight(40);
        cellNum.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellNum.setBorderWidth(1f);
        cellNum.setBorderColor(BaseColor.BLACK);

        PdfPCell cellUsername = new PdfPCell(new Phrase("Email", headerFont));
        cellUsername.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellUsername.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellUsername.setPadding(5);
        cellUsername.setFixedHeight(40);
        cellUsername.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellUsername.setBorderWidth(1f);
        cellUsername.setBorderColor(BaseColor.BLACK);

        PdfPCell cellRole = new PdfPCell(new Phrase("Role", headerFont));
        cellRole.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellRole.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellRole.setPadding(8);
        cellRole.setFixedHeight(40);
        cellRole.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellRole.setBorderWidth(1f);
        cellRole.setBorderColor(BaseColor.BLACK);

        // Add Header Cells to Table
        table.addCell(cellNum);
        table.addCell(cellUsername);
        table.addCell(cellRole);
    }

    public void generateStudentReport(HttpServletResponse response, Document document, PdfWriter writer, String loggedInUser, ArrayList<Schedule> schedule, Date startDate, Date endDate) throws IOException {
        try {
            // Define column widths
            float[] columnWidths = {50f, 50f, 50f, 50f}; // Adjust column widths as needed

            // Create Table with column widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100); // Width 100%
            table.setSpacingBefore(10f); // Space before table

            // Set Table Header
            addStudentReportHeaderRowToTable(table);

            // Add data from the schedule ArrayList to table cells for the logged-in user within the specified date range
            int cellCount = 0;
            for (Schedule s : schedule) {
                // Check if the schedule belongs to the logged-in user and falls within the specified date range
                Date scheduleDate = s.getSchedule();
                if (s.getStudentEmail().equals(loggedInUser)
                        && (startDate == null || scheduleDate.after(startDate) || scheduleDate.equals(startDate))
                        && (endDate == null || scheduleDate.before(endDate) || scheduleDate.equals(endDate))) {

                    addCellToTable(new PdfPCell(new Phrase(s.getTeacherEmail())), table, false);
                    addCellToTable(new PdfPCell(new Phrase(s.getCourse())), table, false);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(s.getSchedule());

                    addCellToTable(new PdfPCell(new Phrase(formattedDate)), table, false);
                    addCellToTable(new PdfPCell(new Phrase(s.getStatus())), table, false);

                    cellCount++;

                    // Check if the cell count reaches 8
                    if (cellCount >= 8) {
                        document.add(table);
                        document.newPage();
                        table.deleteBodyRows();
                        cellCount = 0;
                        // Add Table Header again for each page
                        addStudentReportHeaderRowToTable(table);
                    }
                }
            }

            // Add Table to Document
            document.add(table);
        } catch (DocumentException e) {
            response.getWriter().println("An error occurred while generating the PDF: " + e.getMessage());
        } finally {
            // Close Document
            document.close();
        }
    }

    private void addStudentReportHeaderRowToTable(PdfPTable table) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

        PdfPCell cellTeacher = new PdfPCell(new Phrase("Teacher", headerFont));
        addCellToTable(cellTeacher, table, true);

        PdfPCell cellCourse = new PdfPCell(new Phrase("Course", headerFont));
        addCellToTable(cellCourse, table, true);

        PdfPCell cellDate = new PdfPCell(new Phrase("Date", headerFont));
        addCellToTable(cellDate, table, true);

        PdfPCell cellStatus = new PdfPCell(new Phrase("Status", headerFont));
        addCellToTable(cellStatus, table, true);
    }

    public void generateTeacherReport(HttpServletResponse response, Document document, PdfWriter writer, String loggedInUser, ArrayList<Schedule> schedule, Date startDate, Date endDate) throws IOException {
        try {
            // Define column widths
            float[] columnWidths = {50f, 50f, 50f, 50f}; // Adjust column widths as needed

            // Create Table with column widths
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100); // Width 100%
            table.setSpacingBefore(10f); // Space before table

            // Set Table Header
            addTeacherReportHeaderRowToTable(table);

            // Add data from the schedule ArrayList to table cells for the logged-in teacher within the specified date range
            int cellCount = 0;
            for (Schedule s : schedule) {
                // Check if the schedule belongs to the logged-in teacher and falls within the specified date range
                Date scheduleDate = s.getSchedule();
                if (s.getTeacherEmail().equals(loggedInUser)
                        && (startDate == null || scheduleDate.after(startDate) || scheduleDate.equals(startDate))
                        && (endDate == null || scheduleDate.before(endDate) || scheduleDate.equals(endDate))) {

                    addCellToTable(new PdfPCell(new Phrase(s.getStudentEmail())), table, false);
                    addCellToTable(new PdfPCell(new Phrase(s.getCourse())), table, false);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(s.getSchedule());

                    addCellToTable(new PdfPCell(new Phrase(formattedDate)), table, false);
                    addCellToTable(new PdfPCell(new Phrase(s.getStatus())), table, false);

                    cellCount++;

                    // Check if the cell count reaches 8
                    if (cellCount >= 8) {
                        document.add(table);
                        document.newPage();
                        table.deleteBodyRows();
                        cellCount = 0;
                        // Add Table Header again for each page
                        addTeacherReportHeaderRowToTable(table);
                    }
                }
            }

            // Add Table to Document
            document.add(table);

            // Close Document
            document.close();
        } catch (DocumentException e) {
            response.getWriter().println("An error occurred while generating the PDF: " + e.getMessage());
        }
    }

    private void addTeacherReportHeaderRowToTable(PdfPTable table) {
        // Set Table Header
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

        // Add Header Cells to Table
        PdfPCell cellStudent = new PdfPCell(new Phrase("Student", headerFont));
        addCellToTable(cellStudent, table, true);

        PdfPCell cellCourse = new PdfPCell(new Phrase("Course", headerFont));
        addCellToTable(cellCourse, table, true);

        PdfPCell cellDate = new PdfPCell(new Phrase("Date", headerFont));
        addCellToTable(cellDate, table, true);

        PdfPCell cellStatus = new PdfPCell(new Phrase("Status", headerFont));
        addCellToTable(cellStatus, table, true);
    }

    class PdfHeaderFooter extends PdfPageEventHelper {

        private final String title;
        private final String email;
        private int maxPage;
        private final String dateTime;
        private final String pdfFooter;
        private final String pdfHeader;
        private final Date startDate;
        private final Date endDate;

        public PdfHeaderFooter(String title, String email, int dataLength, int maxRecord, String dateTime, String pdfFooter, String pdfHeader, String role, ArrayList<User> userData, ArrayList<Schedule> scheduleData, Date startDate, Date endDate) {
            this.title = title;
            this.email = email;
            this.dateTime = dateTime;
            this.pdfFooter = pdfFooter;
            this.pdfHeader = pdfHeader;
            this.startDate = startDate;
            this.endDate = endDate;

            if (role.equals("admin")) {
                if (startDate != null && endDate != null) {
                    // If start date and end date are provided, count only the records within the date range
                    int recordsWithinRange = 0;
                    for (Schedule schedule : scheduleData) {
                        Date scheduleDate = schedule.getSchedule();
                        if (scheduleDate.after(startDate) && scheduleDate.before(endDate)) {
                            recordsWithinRange++;
                        }
                    }
                    this.maxPage = (int) Math.ceil((double) recordsWithinRange / maxRecord);
                } else {
                    // If start date and end date are not provided, consider all records
                    this.maxPage = (int) Math.ceil((double) dataLength / maxRecord);
                }
            } else {
                // For roles like student and teacher, consider only their records
                int userRecords = 0;
                for (int i = 0; i < dataLength; i++) {
                    // Assuming data is a list where each element contains teacher and student emails
                    String teacherEmail = scheduleData.get(i).getTeacherEmail();
                    String studentEmail = scheduleData.get(i).getStudentEmail();
                    if (role.equals("teacher") && teacherEmail.equals(email)) {
                        userRecords++;
                    } else if (role.equals("student") && studentEmail.equals(email)) {
                        userRecords++;
                    }
                }
                this.maxPage = (int) Math.ceil((double) userRecords / maxRecord);
            }

            if (maxPage < 1) {
                maxPage = 1;
            }
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy"); // Change the format pattern

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Paragraph headerParagraph = new Paragraph(pdfHeader + " - " + title, headerFont);
            headerParagraph.setAlignment(Element.ALIGN_CENTER);

            // Create a header paragraph for the Date Range and Current User
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
            Paragraph dateRangeUserParagraph = new Paragraph();
            dateRangeUserParagraph.setAlignment(Element.ALIGN_LEFT);

            if (startDate != null && endDate != null) {
                // If date range is provided, format and add it to the paragraph
                String formattedStartDate = dateFormat.format(startDate);
                String formattedEndDate = dateFormat.format(endDate);
                dateRangeUserParagraph.add(new Chunk("RECORDS FROM: " + formattedStartDate + " to " + formattedEndDate, normalFont));
            } else {
                // If no date range is provided, add "ALL RECORDS" to the paragraph
                dateRangeUserParagraph.add(new Chunk("ALL RECORDS", normalFont));
            }

            // Add a space between date range and current user
            dateRangeUserParagraph.add(Chunk.NEWLINE);

            // Add the Current User to the paragraph
            dateRangeUserParagraph.add(new Chunk("Current User: " + email, normalFont));
            dateRangeUserParagraph.setAlignment(Element.ALIGN_RIGHT);

            try {
                // Add the header
                document.add(headerParagraph);
                document.add(Chunk.NEWLINE);

                // Add the Date Range and Current User paragraph to the header
                document.add(dateRangeUserParagraph);

            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document
        ) {
            PdfContentByte cb = writer.getDirectContent();
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC);

            // Footer - Date and Time
            cb.beginText();
            cb.setFontAndSize(footerFont.getBaseFont(), 10);
            cb.setTextMatrix(document.leftMargin(), document.bottomMargin() - 10);
            cb.showText("Date and Time Generated: " + dateTime);
            cb.endText();

            // Footer from web.xml
            cb.beginText();
            cb.setFontAndSize(footerFont.getBaseFont(), 10);
            cb.setTextMatrix((document.right() + document.left()) / 2 - 100, document.bottomMargin() - 10);
            cb.showText(pdfFooter);
            cb.endText();

            // Page number
            int pageNumber = writer.getPageNumber();
            cb.beginText();
            cb.setFontAndSize(footerFont.getBaseFont(), 10);
            cb.setTextMatrix(document.right() - 100, document.bottomMargin() - 10);
            cb.showText("Page " + pageNumber + " of " + maxPage);
            cb.endText();
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
