package mat06.sim.missingitemreminder.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CategoryItem extends RealmObject {

    @PrimaryKey
    private String categoryName;

    public CategoryItem(String categoryName) { this.categoryName = categoryName; }

    public CategoryItem() {
    }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

}
