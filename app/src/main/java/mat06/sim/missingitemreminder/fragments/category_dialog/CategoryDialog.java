package mat06.sim.missingitemreminder.fragments.category_dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mat06.sim.missingitemreminder.R;

public class CategoryDialog extends DialogFragment {

    @BindView(R.id.et_category)
    EditText etCategory;

    private Unbinder unbinder;

    private CategoryDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.tv_ok)
    void onClick(View view) {
        String category = etCategory.getText().toString();
        if(!category.isEmpty()) {
            listener.saveCustomCategory(category);
        }
        dismiss();
    }

    public void setListener(CategoryDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
