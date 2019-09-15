package unicorn.hust.myapplication.model;

public class PostObject {

    private int avatarId;
    private int imageId;
    private String username;
    private String locationWithTime;

    public PostObject(int avatarId, int imageId, String username, String locationWithTime) {
        this.avatarId = avatarId;
        this.imageId = imageId;
        this.username = username;
        this.locationWithTime = locationWithTime;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
}
