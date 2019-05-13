package mat06.sim.missingitemreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mat06.sim.missingitemreminder.fragments.DescribeMissingItemFragment;
import mat06.sim.missingitemreminder.models.MissingItem;

public class AddItemActivity extends AppCompatActivity {

    private MissingItem item;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_item);

        loadDescribeMissingItemFragment();
    }

    void loadDescribeMissingItemFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new DescribeMissingItemFragment(), DescribeMissingItemFragment.TAG).commit();
    }

    public MissingItem getItem() {
        return item;
    }
}
