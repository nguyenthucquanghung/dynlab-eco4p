package unicorn.hust.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private Integer phoneNumber;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("follow")
    @Expose
    private String follow;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("trackingID")
    @Expose
    private String trackingID;
    @SerializedName("cookies")
    @Expose
    private String cookies;
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;
    @SerializedName("verify")
    @Expose
    private String verify;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("messenger")
    @Expose
    private String messenger;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("imgProfile")
    @Expose
    private String imgProfile;
    @SerializedName("lv2password")
    @Expose
    private Integer lv2password;

    /**
     * No args constructor for use in serialization
     */
    public User() {
    }

    /**
     * @param status
     * @param trackingID
     * @param verify
     * @param block
     * @param lv2password
     * @param messenger
     * @param type
     * @param password
     * @param username
     * @param imgProfile
     * @param phoneNumber
     * @param email
     * @param name
     * @param age
     * @param cookies
     * @param follow
     * @param ipAddress
     */
    public User(String username, String password, String name, Integer age, String email, Integer phoneNumber, String type, String follow, String status, String trackingID, String cookies, String ipAddress, String verify, String block, String messenger, String imgProfile, Integer lv2password) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.follow = follow;
        this.status = status;
        this.trackingID = trackingID;
        this.cookies = cookies;
        this.ipAddress = ipAddress;
        this.verify = verify;
        this.block = block;
        this.messenger = messenger;
        this.imgProfile = imgProfile;
        this.lv2password = lv2password;
    }

    public User(String username, String password, String name, Integer age, String email, Integer phoneNumber, String type, String follow, String status, String trackingID, String cookies, String ipAddress, String verify, String block, String messenger, String message, String imgProfile, Integer lv2password) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.follow = follow;
        this.status = status;
        this.trackingID = trackingID;
        this.cookies = cookies;
        this.ipAddress = ipAddress;
        this.verify = verify;
        this.block = block;
        this.messenger = messenger;
        this.message = message;
        this.imgProfile = imgProfile;
        this.lv2password = lv2password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(String trackingID) {
        this.trackingID = trackingID;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public Integer getLv2password() {
        return lv2password;
    }

    public void setLv2password(Integer lv2password) {
        this.lv2password = lv2password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}