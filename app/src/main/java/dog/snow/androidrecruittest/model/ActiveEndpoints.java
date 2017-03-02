package dog.snow.androidrecruittest.model;


import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

public class ActiveEndpoints {
    @SerializedName("active_endpoints")
    private List<URL> activeEndpoints;

    public ActiveEndpoints(List<URL> endpoints){
        this.activeEndpoints = endpoints;
    }

    public List<URL> getActiveEndpoints() {
        return activeEndpoints;
    }

    public void setActiveEndpoints(List<URL> activeEndpoints) {
        this.activeEndpoints = activeEndpoints;
    }
}
