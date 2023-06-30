package com.nic.simpleappproject.UI.Fragment;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.RequiresApi;
import androidx.core.location.GnssStatusCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.nic.simpleappproject.Manifest;
import com.nic.simpleappproject.R;

import java.io.IOException;

public class GnssTestFragment extends Fragment  {
    private static final String TAG = "GpsActivity";

    public static final int UART_BAUD = 9600;
    public static final float ACCURACY = 2.5f; // From GPS datasheet

    private LocationManager mLocationManager;

    private GnssTestViewModel mViewModel;

    public static GnssTestFragment newInstance() {
        return new GnssTestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gnss_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GnssTestViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        super.onDestroy();


                mLocationManager.removeUpdates(mLocationListener);
                mLocationManager.unregisterGnssStatusCallback(mStatusCallback);
                mLocationManager.removeNmeaListener(mMessageListener);
            }





    /** Report location updates */
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.v(TAG, "Location update: " + location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    /** Report satellite status */
    @SuppressLint("NewApi")
    private GnssStatus.Callback mStatusCallback = new GnssStatus.Callback() {
        @Override
        public void onStarted() { }

        @Override
        public void onStopped() { }

        @Override
        public void onFirstFix(int ttffMillis) { }

        @Override
        public void onSatelliteStatusChanged(GnssStatus status) {
            Log.v(TAG, "GNSS Status: " + status.getSatelliteCount() + " satellites.");
        }
    };

    /** Report raw NMEA messages */
    @SuppressLint("NewApi")
    private OnNmeaMessageListener mMessageListener = new OnNmeaMessageListener() {
        @Override
        public void onNmeaMessage(String message, long timestamp) {
            Log.v(TAG, "NMEA: " + message);
        }
    };
}