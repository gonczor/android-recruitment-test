package dog.snow.androidrecruittest.model;


import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class Item {
    @SerializedName("id")
    private int ID;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private URL iconUrl;

    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("url")
    private URL url;

    private Item(Builder builder){
        this.ID = builder.ID;
        this.name = builder.name;
        this.description = builder.description;
        this.iconUrl = builder.iconUrl;
        this.timestamp = builder.timestamp;
        this.url = builder.url;
    }

    public static class Builder{
        private int ID;
        private String name;
        private String description;
        private URL iconUrl;
        private long timestamp;
        private URL url;

        public Builder ID(final int ID){
            this.ID = ID;
            return this;
        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder description(final String description){
            this.description = description;
            return this;
        }

        public Builder iconUrl(final URL url){
            this.iconUrl = url;
            return this;
        }

        public Builder timestamp(final long timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public Builder url(final URL url){
            this.url = url;
            return this;
        }

        public Item build(){
            return new Item(this);
        }
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public URL getIconUrl() {
        return iconUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public URL getUrl() {
        return url;
    }
}
