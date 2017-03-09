package dog.snow.androidrecruittest.model;


public class Item{

    private Long id;
    private String name;
    private String description;
    private String icon;
    private Long timestamp;
    private String url;

    private Item(Long id, String name, String description, String icon, Long timestamp, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.timestamp = timestamp;
        this.url = url;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", timestamp=" + timestamp +
                ", url='" + url + '\'' +
                '}';
    }

    public static class Builder{

        private Long id;
        private String name;
        private String description;
        private String icon;
        private Long timestamp;
        private String url;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Item build() {
            return new Item(id, name, description, icon, timestamp, url);
        }
    }
}
