package unicorn.hust.myapplication.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditAccountResponse {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("message")
    @Expose
    private String[] message = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public EditAccountResponse() {
    }

    /**
     *
     * @param message: List<String>
     * @param type: String
     */
    public EditAccountResponse(String type, String[] message) {
        super();
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

}