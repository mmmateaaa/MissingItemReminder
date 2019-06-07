package mat06.sim.missingitemreminder.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mat06.sim.missingitemreminder.AddItemActivity;
import mat06.sim.missingitemreminder.R;
import mat06.sim.missingitemreminder.database.RealmDatabase;
import mat06.sim.missingitemreminder.models.MissingItem;

public class PreviewFragment extends Fragment implements OnMapReadyCallback {
    public static final String TAG = "PreviewFragment";

    @BindView(R.id.tv_title)
    TextView tvName;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private SupportMapFragment mapFragment;

    private AddItemActivity addItemActivity;
    private GoogleMap googleMap;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItemActivity = (AddItemActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps);
        MissingItem missingItem = addItemActivity.getItem();

        if (missingItem.getName() != null)
            tvName.setText(missingItem.getName());
        if (missingItem.getDescription() != null)
            tvDescription.setText(missingItem.getDescription());
        if (missingItem.getCategory() != null)
            tvCategory.setText(missingItem.getCategory());
        if (missingItem.getImage() != null)
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(missingItem.getImage(), 0, missingItem.getImage().length));
        if (googleMap == null)
            mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.fab)
    void onFabClick(View view) {
        RealmDatabase.storeMissingItem(addItemActivity.getItem());
        addItemActivity.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
