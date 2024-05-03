/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package console;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lejan Juico
 */
@WebServlet("/generate-report")
public class GenerateReport extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve header and footer from web.xml
        String pdfHeader = getServletContext().getInitParameter("pdfHeader");
        String pdfFooter = getServletContext().getInitParameter("pdfFooter");

        // Sample data arrays
        String[] dates = {"April 29", "April 30", "May 1"};
        String[][] courses = {
            {"ICS2609", "CS2618", "CS2819"},
            {"ENG2101", "MATH2203", "PHY2304"},
            {"ART2402", "HIS2505", "GEO2706"}
        };

        // Create a PDF document with landscape orientation
        Document document = new Document(PageSize.A4.rotate());
        try {
            // Setting up response headers for PDF
            response.setContentType("application/pdf");

            // Filename with date and time stamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = "report_" + dateFormat.format(new Date()) + ".pdf";
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            // Creating PDF writer
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            // Add header
            document.add(new Paragraph(pdfHeader));

            // Create a table with two columns
            PdfPTable table = new PdfPTable(2);

            // Add headers
            PdfPCell header1 = new PdfPCell(new Paragraph("Date"));
            PdfPCell header2 = new PdfPCell(new Paragraph("Courses"));
            table.addCell(header1);
            table.addCell(header2);

            // Iterate through the data arrays
            for (int i = 0; i < dates.length; i++) {
                // Add date
                PdfPCell dateCell = new PdfPCell(new Paragraph(dates[i]));
                table.addCell(dateCell);

                // Add courses
                PdfPCell courseCell = new PdfPCell();
                for (String course : courses[i]) {
                    courseCell.addElement(new Paragraph(course));
                }
                table.addCell(courseCell);
            }

            // Add the table to the document
            document.add(table);

            // Add footer
            document.add(new Paragraph(pdfFooter));

            // Close the document
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
