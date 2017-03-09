package dog.snow.androidrecruittest.model.error;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("error")
    @Expose
    private ErrorStatus errorStatus;

    public String getMessage(){
        return message;
    }

    public ErrorStatus getErrorStatus(){
        return errorStatus;
    }
}
