package com.alabamaor.hometest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.alabamaor.hometest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    private Fragment fragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.sortAreaAsc:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.sortAreaDesc:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.sortNameAsc:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.sortNameDesc:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (DrawerLayout) null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}


