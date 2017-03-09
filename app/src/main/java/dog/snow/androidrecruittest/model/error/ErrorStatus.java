package dog.snow.androidrecruittest.model.error;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorStatus {
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getStatus(){
        return status;
    }
}
