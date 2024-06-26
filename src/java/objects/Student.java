package objects;
import java.util.Date;

public class Student extends User {
    String fname, lname;
    Date bday;

    public Student(String email, String fname, String lname, Date bday) {
        super(email);
        this.fname = fname;
        this.lname = lname;
        this.bday = bday;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }
}
