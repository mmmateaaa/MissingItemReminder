package mat06.sim.missingitemreminder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mat06.sim.missingitemreminder.adapters.RecyclerAdapter;
import mat06.sim.missingitemreminder.adapters.SpinnerAdapter;
import mat06.sim.missingitemreminder.database.RealmDatabase;
import mat06.sim.missingitemreminder.fragments.PreviewFragment;
import mat06.sim.missingitemreminder.models.AdapterWrapper;
import mat06.sim.missingitemreminder.models.CategoryItem;
import mat06.sim.missingitemreminder.models.MissingItem;

import static mat06.sim.missingitemreminder.AddItemActivity.EXTRA_ID;
import static mat06.sim.missingitemreminder.AddItemActivity.EXTRA_TYPE;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RecyclerAdapter.OnAdapterClick {

    @BindView(R.id.s_category)
    Spinner spinner;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private RecyclerAdapter adapter = new RecyclerAdapter(this);
    private SpinnerAdapter spinnerAdapter = new SpinnerAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spinnerAdapter.addData(createSpinnerAdapterData(RealmDatabase.getAllCategories(this)));
        filterItems(spinner.getSelectedItemPosition());
    }

    private void filterItems(int position) {
        SpinnerAdapter spinnerAdapter = (SpinnerAdapter) spinner.getAdapter();
        adapter.addData(createRecyclerAdapterData(RealmDatabase.getMissingItems()));
    }

    private List<AdapterWrapper> createRecyclerAdapterData(List<MissingItem> items) {
        List<AdapterWrapper> list = new ArrayList<>();
        if (items.isEmpty()) {
            list.add(new AdapterWrapper(R.layout.cell_empty, null));
        } else {
            for (MissingItem item: items) {
                list.add(new AdapterWrapper(R.layout.cell_missing_item, item));
            }
        }
        return list;
    }

    private List<CategoryItem> createSpinnerAdapterData(List<CategoryItem> categoryItemList) {
        List<CategoryItem> list = new ArrayList<>();
        list.add(new CategoryItem(getString(R.string.all_category)));
        list.addAll(categoryItemList);
        return list;
    }

    @OnClick(R.id.fab)
    void onClick(View view) {
        Intent toAddItemActivity = new Intent(this, AddItemActivity.class);
        startActivity(toAddItemActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        filterItems(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterWrapper adapterWrapper) {
        Intent toAddItemActivity = new Intent(this, AddItemActivity.class);
        toAddItemActivity.putExtra(EXTRA_ID, adapterWrapper.getMissingItem().getId());
        startActivity(toAddItemActivity);
    }

    @Override
    public void onMenuClick(AdapterWrapper adapterWrapper, View view, int position) {
        createMenu(view, position, adapterWrapper.getMissingItem());
    }

    private void createMenu(View view, final int position, final MissingItem missingItem) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.item_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.preview) {
                    Intent toAddItemActivity = new Intent(getBaseContext(), AddItemActivity.class);
                    toAddItemActivity.putExtra(EXTRA_ID, missingItem.getId());
                    toAddItemActivity.putExtra(EXTRA_TYPE, AddItemActivity.TYPE_PREVIEW);
                    startActivity(toAddItemActivity);
                } else if (item.getItemId() == R.id.edit) {
                    Intent toAddItemActivity = new Intent(getBaseContext(), AddItemActivity.class);
                    toAddItemActivity.putExtra(EXTRA_ID, missingItem.getId());
                    toAddItemActivity.putExtra(EXTRA_TYPE, AddItemActivity.TYPE_EDIT);
                    startActivity(toAddItemActivity);
                } else
                    RealmDatabase.removeItem(missingItem);
                return false;
            }
        });
        popupMenu.show();
    }
}
