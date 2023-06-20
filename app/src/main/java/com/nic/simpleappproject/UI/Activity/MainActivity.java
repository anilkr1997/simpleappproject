package com.nic.simpleappproject.UI.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.nic.simpleappproject.R;
import com.nic.simpleappproject.UI.Fragment.MapFragment;
import com.nic.simpleappproject.Uttile;
import com.nic.simpleappproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        getSupportActionBar().setTitle("Location");
        Uttile.setFragment(new MapFragment(),true,this,R.id.nav_host_fragment_activity_bottem_navigation);
    }
}