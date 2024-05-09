package objects;
import java.util.Date;

public class Schedule {
    int entry;
    String studentEmail, teacherEmail, course, status;
    Date schedule;
    public Schedule(int entry, String studentEmail, String teacherEmail, String course, String status, Date schedule) {
        this.entry = entry;
        this.studentEmail = studentEmail;
        this.teacherEmail = teacherEmail;
        this.course = course;
        this.status = status;
        this.schedule = schedule;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }
}
