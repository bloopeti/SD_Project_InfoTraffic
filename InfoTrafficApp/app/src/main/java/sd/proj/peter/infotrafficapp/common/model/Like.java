package sd.proj.peter.infotrafficapp.common.model;

public class Like {
    private int id;
    private int liking_user;
    private int liked_alert;

    public Like() {
    }

    public Like(int liking_user, int liked_alert) {
        this.liking_user = liking_user;
        this.liked_alert = liked_alert;
    }

    public Like(int id, int liking_user, int liked_alert) {
        this.id = id;
        this.liking_user = liking_user;
        this.liked_alert = liked_alert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLiking_user() {
        return liking_user;
    }

    public void setLiking_user(int liking_user) {
        this.liking_user = liking_user;
    }

    public int getLiked_alert() {
        return liked_alert;
    }

    public void setLiked_alert(int liked_alert) {
        this.liked_alert = liked_alert;
    }
}
