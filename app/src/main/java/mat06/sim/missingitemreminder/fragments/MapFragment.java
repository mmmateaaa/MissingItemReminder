package mat06.sim.missingitemreminder.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mat06.sim.missingitemreminder.AddItemActivity;
import mat06.sim.missingitemreminder.R;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    public static final int REQUEST_LOCATION = 1001;
    public static final String TAG = "MapFragment";

    private AddItemActivity addItemActivity;
    private GoogleMap googleMap;
    private Marker marker;
    private SupportMapFragment mapFragment;
    private LatLng latLng;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        addItemActivity = (AddItemActivity) getActivity();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps);
        if (googleMap == null)
            mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.fab)
    void onFabClick(View view) {
        if (latLng != null) {
            addItemActivity.getItem().setLatitude(latLng.latitude);
            addItemActivity.getItem().setLongitude(latLng.longitude);
        }
        addItemActivity.loadCameraFragment();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.latLng = latLng;
        addPin(latLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(this);
        checkUserPermission();
        if(addItemActivity.getItem().getLatitude() != 0 && addItemActivity.getItem().getLongitude() != 0) {
            LatLng latLng = new LatLng(addItemActivity.getItem().getLatitude(), addItemActivity.getItem().getLongitude());
            addPin(latLng);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    private void addPin(LatLng latLng) {
        if(marker == null) {
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin)));
        } else
            marker.setPosition(latLng);
    }

    private void checkUserPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_LOCATION);
            else
                showUserLocation();
        } else {
            showUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION && permissions.length != 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                showUserLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void showUserLocation() {
        googleMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
