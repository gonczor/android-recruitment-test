package dog.snow.androidrecruittest.model.error;


import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("status")
    private Integer status;

    public int getStatus() {
        return status;
    }
}
