package dog.snow.androidrecruittest.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import dog.snow.androidrecruittest.model.error.ErrorResponse;

public class ServerResponse {
    // @SerializedName("item")
    public List<Item> items;
    @SerializedName("error")
    public ErrorResponse errorResponse;
}
