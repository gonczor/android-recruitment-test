package dog.snow.androidrecruittest.rest;


import java.util.List;

import dog.snow.androidrecruittest.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("items")
    Call<List<Item>> getItems();
}
