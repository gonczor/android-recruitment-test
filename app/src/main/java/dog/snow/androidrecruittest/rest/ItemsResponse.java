package dog.snow.androidrecruittest.rest;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import dog.snow.androidrecruittest.model.Item;

public class ItemsResponse {
    @SerializedName("results")
    private List<Item> results;

    public List<Item> getResults() {
        return results;
    }

    public void setResults(List<Item> results) {
        this.results = results;
    }
}
