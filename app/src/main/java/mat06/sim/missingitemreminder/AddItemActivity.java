package mat06.sim.missingitemreminder;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mat06.sim.missingitemreminder.database.RealmDatabase;
import mat06.sim.missingitemreminder.fragments.CameraFragment;
import mat06.sim.missingitemreminder.fragments.DescribeMissingItemFragment;
import mat06.sim.missingitemreminder.fragments.MapFragment;
import mat06.sim.missingitemreminder.fragments.PreviewFragment;
import mat06.sim.missingitemreminder.models.MissingItem;

public class AddItemActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    private MissingItem item;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_item);

        long id = getIntent().getLongExtra(EXTRA_ID, -1L);
        if (id != -1) {
            item = RealmDatabase.queryMissingItemById(id);
        } else
            item = new MissingItem(System.currentTimeMillis());

        loadDescribeMissingItemFragment();
    }

    void loadDescribeMissingItemFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new DescribeMissingItemFragment(), DescribeMissingItemFragment.TAG).commit();
    }

    public void loadCameraFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new CameraFragment(), CameraFragment.TAG);
        fragmentTransaction.addToBackStack(CameraFragment.TAG);
        fragmentTransaction.commit();
    }

    public void loadMapFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new MapFragment(), MapFragment.TAG);
        fragmentTransaction.addToBackStack(MapFragment.TAG);
        fragmentTransaction.commit();
    }

    public void loadPreviewFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new PreviewFragment(), PreviewFragment.TAG);
        fragmentTransaction.addToBackStack(PreviewFragment.TAG);
        fragmentTransaction.commit();
    }

    public MissingItem getItem() {
        return item;
    }
}
