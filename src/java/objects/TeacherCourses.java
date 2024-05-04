package objects;

public class TeacherCourses {
    String teacherEmail, cname;

    public TeacherCourses(String teacherEmail, String cname) {
        this.teacherEmail = teacherEmail;
        this.cname = cname;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
