package unicorn.hust.myapplication.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("listStatus")
    @Expose
    private List<Post> posts = null;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public class Post {

        @SerializedName("sendID")
        @Expose
        private String sendID;
        @SerializedName("passWord")
        @Expose
        private String passWord;
        @SerializedName("type")
        @Expose
        private Object type;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("receiveId")
        @Expose
        private List<String> receiveId = null;
        @SerializedName("contentText")
        @Expose
        private String contentText;
        @SerializedName("base64Image")
        @Expose
        private String base64Image;
        @SerializedName("message")
        @Expose
        private List<String> message = null;

        public String getSendID() {
            return sendID;
        }

        public void setSendID(String sendID) {
            this.sendID = sendID;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<String> getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(List<String> receiveId) {
            this.receiveId = receiveId;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public String getBase64Image() {
            return base64Image;
        }

        public void setBase64Image(String base64Image) {
            this.base64Image = base64Image;
        }

        public List<String> getMessage() {
            return message;
        }

        public void setMessage(List<String> message) {
            this.message = message;
        }
    }
}