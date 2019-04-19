package mat06.sim.missingitemreminder;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mat06.sim.missingitemreminder.adapters.SpinnerAdapter;
import io.realm.Realm;
import mat06.sim.missingitemreminder.database.RealmDatabase;
import mat06.sim.missingitemreminder.models.CategoryItem;
import mat06.sim.missingitemreminder.models.MissingItem;

public class AddItemActivity extends AppCompatActivity {
    private MissingItem item;
    @BindView(R.id.et_name)
    TextInputEditText etName;

    @BindView(R.id.til_name)
    TextInputLayout tilName;

    @BindView(R.id.et_description)
    TextInputEditText etDescription;

    @BindView(R.id.til_description)
    TextInputLayout tilDescription;

    @BindView(R.id.s_category)
    Spinner spinner;

    private MissingItem missingItem;
    private SpinnerAdapter spinnerAdapter = new SpinnerAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ButterKnife.bind(this);

        //populateView();
    }

   private void populateView() {
        if (missingItem.getName() != null)
            etName.setText(missingItem.getName());
        if (missingItem.getDescription() != null)
            etDescription.setText(missingItem.getDescription());
        if (missingItem.getCategory() != null)
            spinner.setSelection(getPreselectPosition(missingItem.getCategory()));
    }

    private int getPreselectPosition(String categoryName) {
        List<CategoryItem> categoryItemList = RealmDatabase.getAllCategories(this);
        int position = 0;
        for (int i = 0; i < categoryItemList.size(); i++) {
            if (categoryName.equals(categoryItemList.get(i).getCategoryName())) {
                position = i;
                break;
            }
        }
        return position;
    }

    private boolean validate(String name, String description){
        if (name.isEmpty()){
            tilName.setError(getString(R.string.empty_entry));
            tilName.setErrorEnabled(true);
        } else {
            tilName.setError(null);
            tilName.setErrorEnabled(false);
        }
        if (description.isEmpty()){
            tilDescription.setError(getString(R.string.empty_entry));
            tilDescription.setErrorEnabled(true);
        } else {
            tilDescription.setError(null);
            tilDescription.setErrorEnabled(false);
        }
        return !name.isEmpty() && !description.isEmpty();
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        if(validate(etName.getText().toString(), etDescription.getText().toString())) {
            missingItem.setName(etName.getText().toString());
            missingItem.setDescription(etDescription.getText().toString());
            String category = spinnerAdapter.getItem(spinner.getSelectedItemPosition()).getCategoryName();
            missingItem.setCategory(category);
            RealmDatabase.storeMissingItem(item);
        }
    }

}
