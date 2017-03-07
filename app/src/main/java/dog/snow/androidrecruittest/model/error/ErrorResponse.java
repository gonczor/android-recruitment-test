package dog.snow.androidrecruittest.model.error;


import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("error")
    private Error error;

    public String getMessage() {
        return message;
    }

    public Error getError() {
        return error;
    }
}
