package mat06.sim.missingitemreminder;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mat06.sim.missingitemreminder.models.CategoryItem;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.s_category)
    Spinner spinner;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private SpinnerAdapter spinnerAdapter = new SpinnerAdapter() {
        private List<CategoryItem> categoryItemList = new ArrayList<>();
        @Override
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return categoryItemList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            CategoryViewHolder viewHolder;
            if(view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_category_item, viewGroup, false);
                viewHolder = new CategoryViewHolder();
                viewHolder.tvCategory = view.findViewById(R.id.tv_category);
                view.setTag(viewHolder);
            } else {
                viewHolder = (CategoryViewHolder) view.getTag();
            }
            viewHolder.tvCategory.setText(categoryItemList.get(i).getCategoryName());
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        filterItems(spinner.getSelectedItemPosition());
    }

    private void filterItems(int position) {
        SpinnerAdapter spinnerAdapter = (SpinnerAdapter) spinner.getAdapter();
    }

    private List<CategoryItem> createSpinnerAdapterData(List<CategoryItem> categoryItemList) {
        List<CategoryItem> list = new ArrayList<>();
        list.add(new CategoryItem(getString(R.string.all_category)));
        list.addAll(categoryItemList);
        return list;
    }

    @OnClick(R.id.fab)
    void onClick(View view) {
        Intent toAddItemActicity = new Intent(this, AddItemActivity.class);
        startActivity(toAddItemActicity);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class CategoryViewHolder {
        TextView tvCategory;
    }
}
