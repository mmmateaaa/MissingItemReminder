package mat06.sim.missingitemreminder.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MissingItem extends RealmObject {
    public static final String ID = "id";
    public static final String CATEGORY = "category";
    @PrimaryKey
    private long id;
    private String name;
    private String description;
    private String category;
    private byte[] image;
    private double latitude;
    private double longitude;

    public MissingItem(){

    }

    public MissingItem(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) { this.image = image; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
