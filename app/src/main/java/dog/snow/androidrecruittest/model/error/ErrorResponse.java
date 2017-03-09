package dog.snow.androidrecruittest.model.error;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("error")
    @Expose
    private Error error;


    public Error getError() {
        return error;
    }
}
