package dog.snow.androidrecruittest.model;


public class Item {
    public Long id;
    public String name;
    public String description;
    public String icon;
    public Long timestamp;
    public String url;

    public Item(Long id, String name, String description, String icon, Long timestamp, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.timestamp = timestamp;
        this.url = url;
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
}
