package com.vdroid.tyc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.vdroid.tyc.ui.main.MainFragment;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private final int ACTION_OPEN_DOCUMENT = 654;

    private MainFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        if (savedInstanceState == null) {
            mFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mFragment)
                    .commitNow();
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
            case R.id.mi_import_words: {
                final Intent getContentIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                getContentIntent.addCategory(Intent.CATEGORY_OPENABLE);
                getContentIntent.setType("*/*");
                //getContentIntent.setType("text/csv");
                final Intent chooserIntent = Intent.createChooser(getContentIntent, getString(R.string.intent_chooser));
                startActivityForResult(chooserIntent, ACTION_OPEN_DOCUMENT);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_OPEN_DOCUMENT) {
            if (resultCode == RESULT_OK && data.getData() != null && data.getData().getPath() != null) {
                try {
                    InputStream fis = getContentResolver().openInputStream(data.getData());
                    mFragment.setCsv(fis);
                } catch (Exception ex) {

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
