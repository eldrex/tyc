package com.vdroid.tyc.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vdroid.tyc.R;

public class MainActivity extends AppCompatActivity {

    private MainFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getString(R.string.app_name));

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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_cancel_course);
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            super.onBackPressed();
        });
        builder.setNegativeButton(android.R.string.no, (dialog, which) -> {

        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
