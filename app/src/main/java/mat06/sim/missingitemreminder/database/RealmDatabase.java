package mat06.sim.missingitemreminder.database;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import mat06.sim.missingitemreminder.R;
import mat06.sim.missingitemreminder.models.CategoryItem;
import mat06.sim.missingitemreminder.models.MissingItem;

public class RealmDatabase {

    public static void storeCategory(final CategoryItem categoryItem) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(categoryItem);
            }
        });
        realm.close();
    }

    public static List<CategoryItem> getAllCategories(Context context) {
        Realm realm = Realm.getDefaultInstance();
        List<CategoryItem> categoryItemList = realm.copyFromRealm(realm.where(CategoryItem.class).findAll());
        realm.close();
        if(categoryItemList.isEmpty()){
            categoryItemList = initCategoryList(context);
        }
        return categoryItemList;
    }

    private static List<CategoryItem> initCategoryList(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.default_categories);
        final List<CategoryItem> categoryItemList = new ArrayList<>();
        for(String item :
            Arrays.asList(stringArray)) {
            categoryItemList.add(new CategoryItem(item));
        }
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(categoryItemList);
            }
        });
        realm.close();
        return categoryItemList;
    }

    public static void storeMissingItem(final MissingItem missingItem) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(missingItem);
            }
        });
        realm.close();
    }

    public static List<MissingItem> getMissingItems() {
        Realm realm = Realm.getDefaultInstance();
        List<MissingItem> missingItemList = realm.copyFromRealm(realm.where(MissingItem.class).findAll());
        realm.close();
        return missingItemList;
    }

    public static MissingItem queryMissingItemById(long id) {
        Realm realm = Realm.getDefaultInstance();
        MissingItem missingItem = realm.copyFromRealm(realm.where(MissingItem.class).equalTo(MissingItem.ID, id).findFirst());
        realm.close();
        return missingItem;
    }

    public static void removeItem(final MissingItem missingItem) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.where(MissingItem.class).equalTo(MissingItem.ID, missingItem.getId()).findAll().deleteAllFromRealm();
            }
        });
    }
}
