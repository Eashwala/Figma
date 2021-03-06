package com.ipl.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ipl.user.commonutils.MyCognito;
import com.ipl.user.commonutils.SharedPreferenceManager;
import com.ipl.user.screens.HomeFragment;
import com.ipl.user.screens.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    MyCognito cognito;
    Fragment active = fragment1;
    SharedPreferenceManager sharedPreferenceManager;
    // NetworkCheck networkCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        cognito = new MyCognito(getApplicationContext());

        sharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.action_profile:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            // MainActivity.super.onBackPressed();
                            finish();
                            moveTaskToBack(true);
                        }
                    }).create().show();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}