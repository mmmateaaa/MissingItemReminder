package mat06.sim.missingitemreminder.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mat06.sim.missingitemreminder.AddItemActivity;
import mat06.sim.missingitemreminder.R;
import mat06.sim.missingitemreminder.adapters.SpinnerAdapter;
import mat06.sim.missingitemreminder.database.RealmDatabase;
import mat06.sim.missingitemreminder.fragments.category_dialog.CategoryDialog;
import mat06.sim.missingitemreminder.fragments.category_dialog.CategoryDialogListener;
import mat06.sim.missingitemreminder.models.CategoryItem;
import mat06.sim.missingitemreminder.models.MissingItem;

public class DescribeMissingItemFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "DescribeMissingItemFragment";

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

    private AddItemActivity addItemActivity;
    private MissingItem missingItem;
    private SpinnerAdapter spinnerAdapter = new SpinnerAdapter();
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItemActivity = (AddItemActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_describe_missing_item, container, false);
    }

    @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        spinnerAdapter.addNewData(RealmDatabase.getAllCategories(getContext()));
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        missingItem = addItemActivity.getItem();
        populateView();
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
        List<CategoryItem> categoryItemList = RealmDatabase.getAllCategories(getContext());
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

    @OnClick(R.id.iv_add_category)
    void onCategoryClick() {
        showCategoryDialog();
    }

    private void showCategoryDialog() {
        CategoryDialog dialog = new CategoryDialog();
        dialog.setListener((CategoryDialogListener) this);
        dialog.show(getFragmentManager(), dialog.getTag());
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        if(validate(etName.getText().toString(), etDescription.getText().toString())) {
            missingItem.setName(etName.getText().toString());
            missingItem.setDescription(etDescription.getText().toString());
            String category = spinnerAdapter.getItem(spinner.getSelectedItemPosition()).getCategoryName();
            missingItem.setCategory(category);
            RealmDatabase.storeMissingItem(addItemActivity.getItem());
            addItemActivity.loadMapFragment();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
