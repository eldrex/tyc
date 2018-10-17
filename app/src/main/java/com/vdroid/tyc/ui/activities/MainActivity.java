package com.vdroid.tyc.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.vdroid.tyc.R;

public class MainActivity extends AppCompatActivity {

    private MainFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        if (savedInstanceState == null) {
            mFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mFragment)
                    .commit();
        }

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            try {
                mFragment.setCsvUri(intent.getData());
            } catch (Exception ex) {
                // TODO handle
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        }

        return super.onOptionsItemSelected(item);
    }

}
