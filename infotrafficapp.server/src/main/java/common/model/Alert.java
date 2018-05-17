package common.model;

public class Alert {
    private String type;
    private String location;
    private String submission_time;
    private String status;

    public Alert() {}

    public Alert(String type, String location, String submission_time, String status) {
        this.type = type;
        this.location = location;
        this.submission_time = submission_time;
        this.status = status;
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

    public void setStatus(String status) {
        this.status = status;
    }
}
