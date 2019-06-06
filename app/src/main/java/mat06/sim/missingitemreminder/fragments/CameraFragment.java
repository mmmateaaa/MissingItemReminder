package mat06.sim.missingitemreminder.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mat06.sim.missingitemreminder.AddItemActivity;
import mat06.sim.missingitemreminder.R;
import mat06.sim.missingitemreminder.database.RealmDatabase;
import mat06.sim.missingitemreminder.models.MissingItem;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {
    public static final int CAMERA_REQUEST = 1001;
    public static final String TAG = "CameraFragment";

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private AddItemActivity addItemActivity;
    private Bitmap bitmap;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItemActivity = (AddItemActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        MissingItem missingItem = addItemActivity.getItem();
        if(missingItem.getImage() != null) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(missingItem.getImage(),0, missingItem.getImage().length));
        }
    }

    @OnClick(R.id.fab)
    void onCameraClick(View view) {
        checkPermission();
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            addItemActivity.getItem().setImage(baos.toByteArray());
        }
        RealmDatabase.storeMissingItem(addItemActivity.getItem());
        addItemActivity.finish();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        } else {
            openCamera();
        }
    }

    /*@Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST) {
            if(resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                bitmap = photo;
                imageView.setImageBitmap(photo);
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
