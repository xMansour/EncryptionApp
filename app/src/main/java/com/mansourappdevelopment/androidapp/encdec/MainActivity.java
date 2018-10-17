package com.mansourappdevelopment.androidapp.encdec;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnCopy:
                Toast.makeText(this, "Copy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnShare:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EncryptionFragment()).commit();
        BottomNavigationView mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navEnc:
                        selectedFragment = new EncryptionFragment();
                        break;

                    case R.id.navDec:
                        selectedFragment = new DecryptionFragment();
                        break;

                    case R.id.navInfo:
                        selectedFragment = new InformationFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                return true;
            }
        });
    }
}
