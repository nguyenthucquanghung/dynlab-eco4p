package unicorn.hust.myapplication.model;

public class PostObject {

    private String base64Image;
    private int avatarId;
    private String username;
    private String locationWithTime;
    private String content;

    public PostObject(String base64Image, int avatarId, String username, String locationWithTime, String content) {
        this.base64Image = base64Image;
        this.avatarId = avatarId;
        this.username = username;
        this.locationWithTime = locationWithTime;
        this.content = content;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocationWithTime() {
        return locationWithTime;
    }

    public void setLocationWithTime(String locationWithTime) {
        this.locationWithTime = locationWithTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
