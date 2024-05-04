package objects;

import java.util.Date;

public class Teacher extends User {

    String email, fname, lname, resume, status;
    Date bday;

    public Teacher(String email, String fname, String lname, String resume, String status, Date bday) {
        super(email);
        this.fname = fname;
        this.lname = lname;
        this.resume = resume;
        this.status = status;
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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }
}
