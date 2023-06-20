package com.nic.simpleappproject.UI.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nic.simpleappproject.Model.RealDbdatabase;

import com.nic.simpleappproject.R;
import com.nic.simpleappproject.Uttile;
import com.nic.simpleappproject.databinding.FragmentMapBinding;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class MapFragment extends Fragment implements LocationListener,

        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, View.OnClickListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnPolylineClickListener, ResultCallback<LocationSettingsResult>, OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

// set map zoom lave
    private static final Float DEFAULT_ZOOM = 15f;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;


    private MapViewModel mViewModel;
    // fragment binding with view
    private FragmentMapBinding binding;


    private GoogleMap mGoogleMap;

    private SupportMapFragment touchableMapFragment;


    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location mCurrentLocation = null;
    private Location lastKnownLocation;
    // default location
    private final LatLng defaultLocation = new LatLng(20.7580154, 72.1113358);
    private LatLng latloglocation;
    private Realm realm;
    private CameraPosition cameraPosition = null;


    public FragmentMapBinding getBinding() {
        return binding;
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private RealmResults<RealDbdatabase> realDbdatabases;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// location service
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
// view binding with fragment
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialise database instance
        realm = Realm.getDefaultInstance();
        // Initialise modelview
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());



        // button of add item

        binding.fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latloglocation != null) {
                    // go to add fragment
                    Uttile.setFragment(new AddDataFragment(latloglocation), false, getActivity(), R.id.nav_host_fragment_activity_bottem_navigation);
                } else {
                    Uttile.Showmassage(getContext(), binding.fbAdd, "please select your location");
                }


            }
        });
        // button of view all list
        binding.fbList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // database Item size or list of data
                if (realm.where(RealDbdatabase.class).findAll().size() > 0) {
                    // open fragment list of all data
                    Uttile.setFragment(new ViewAllDataFragment(), false, getActivity(), R.id.nav_host_fragment_activity_bottem_navigation);


                } else {
                    // show Error massage
                    Uttile.Showmassage(getContext(), binding.fbList, "Please add Task");

                }

            }
        });
// map
        touchableMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        touchableMapFragment.getMapAsync(this);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        latloglocation = latLng;
        mGoogleMap.clear();
        // add marker
        mGoogleMap.addMarker(new MarkerOptions().position(latLng));
        addalltasklocation();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {

    }
//
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.clear();

        mGoogleMap = googleMap;
// check location Permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        // in map set map load callback
        mGoogleMap.setOnMapLoadedCallback(this::onMapLoaded);


    }
// get Device Current Location
    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {

        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    lastKnownLocation = task.getResult();
                    if (lastKnownLocation != null) {
// set zoom for current lat-log or location
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                      // get current lat
                        latloglocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        mGoogleMap.addMarker(new MarkerOptions().position(latloglocation));
                    }
                } else if (task.isComplete()) {
                    Log.e("TAG", "onComplete: ");
                } else if (task.isCanceled()) {
                    Log.e("TAG", "isCanceled: ");
                } else {
                    Log.d("TAG", "Current location is null. Using defaults.");
                    Log.e("TAG", "Exception: %s", task.getException());
                    mGoogleMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                }

            }

        });
    }

    @Override
    public void onMapLoaded() {

        mGoogleMap.setOnMapClickListener(this::onMapClick);

        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

            }
        });
        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

            }
        });
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick() {

                return true;
            }
        });

        getDeviceLocation();

        addalltasklocation();
    }

    private void addalltasklocation() {
        if (realm.where(RealDbdatabase.class).findAll().size() > 0) {
            realDbdatabases = realm.where(RealDbdatabase.class).findAll();
            for (int i = 0; i <= realDbdatabases.size() - 1; i++) {
// add marker
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(realDbdatabases.get(i).getLat(), realDbdatabases.get(i).getLon())).title(realDbdatabases.get(i).getTaskname()).icon(Uttile.BitmapFromVector(getContext().getApplicationContext(), R.drawable.baseline_location_city_24)));

            }
        }

    }
}