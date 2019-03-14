package mat06.sim.missingitemreminder;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import butterknife.BindView;

public class AddItemActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }
}
