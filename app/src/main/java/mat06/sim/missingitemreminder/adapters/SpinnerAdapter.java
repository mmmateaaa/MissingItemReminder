package mat06.sim.missingitemreminder.adapters;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mat06.sim.missingitemreminder.R;
import mat06.sim.missingitemreminder.models.CategoryItem;

public class SpinnerAdapter extends BaseAdapter {
    private List<CategoryItem> categoryItemList = new ArrayList<>();

    @Override
    public int getCount() {
        return categoryItemList.size();
    }

    @Override
    public CategoryItem getItem(int i) {
        return categoryItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public void addData(List<CategoryItem> categoryItemList) {
        this.categoryItemList.clear();
        this.categoryItemList.addAll(categoryItemList);
        notifyDataSetChanged();
    }

    public void addNewData(List<CategoryItem> categoryItemList) {
        this.categoryItemList.clear();
        this.categoryItemList.addAll(categoryItemList);
        notifyDataSetChanged();
    }

    private class CategoryViewHolder {
        TextView tvCategory;
    }
}
