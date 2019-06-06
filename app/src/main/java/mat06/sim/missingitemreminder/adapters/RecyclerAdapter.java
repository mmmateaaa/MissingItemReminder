package mat06.sim.missingitemreminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mat06.sim.missingitemreminder.R;
import mat06.sim.missingitemreminder.models.AdapterWrapper;
import mat06.sim.missingitemreminder.models.MissingItem;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<AdapterWrapper> data = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        switch (i) {
            case R.layout.cell_empty:
                return new EmptyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_empty, viewGroup, false));
            default:
                return new MissingItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_missing_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.cell_missing_item:
                MissingItem missingItem = data.get(position).getMissingItem();
                MissingItemViewHolder missingItemViewHolder = (MissingItemViewHolder) viewHolder;
                //Glide.with(context).asBitmap().load(missingItem.getImage()).into(missingItemViewHolder.imageView);
                missingItemViewHolder.title.setText(missingItem.getName());
                missingItemViewHolder.description.setText(missingItem.getDescription());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<AdapterWrapper> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnAdapterClick {
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MissingItemViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.image_view)
        //ImageView imageView;
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_description)
        TextView description;
        public MissingItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
