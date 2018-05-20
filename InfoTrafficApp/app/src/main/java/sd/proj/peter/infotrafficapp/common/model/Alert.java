package sd.proj.peter.infotrafficapp.common.model;

public class Alert {
    private int id;
    private String type;
    private String location;
    private String submission_time;
    private String status;
    private int submitting_user;

    public Alert() {}

    public Alert(String type, String location, String submission_time, String status, int submitting_user) {
        this.type = type;
        this.location = location;
        this.submission_time = submission_time;
        this.status = status;
        this.submitting_user = submitting_user;
    }

    public Alert(int id, String type, String location, String submission_time, String status, int submitting_user) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.submission_time = submission_time;
        this.status = status;
        this.submitting_user = submitting_user;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getSubmission_time() {
        return submission_time;
    }

    public String getStatus() {
        return status;
    }

    public int getSubmitting_user() {
        return submitting_user;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
