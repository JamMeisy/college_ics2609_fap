package objects;

public class Courses {

    String cname, cdescription, cdifficulty, chours;

    public Courses(String cname, String cdescription, String cdifficulty, String chours) {
        this.cname = cname;
        this.cdescription = cdescription;
        this.cdifficulty = cdifficulty;
        this.chours = chours;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }

    public String getCdifficulty() {
        return cdifficulty;
    }

    public void setCdifficulty(String cdifficulty) {
        this.cdifficulty = cdifficulty;
    }

    public String getChours() {
        return chours;
    }

    public void setChours(String chours) {
        this.chours = chours;
    }
}
