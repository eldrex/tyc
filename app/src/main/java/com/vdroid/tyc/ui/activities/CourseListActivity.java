package com.vdroid.tyc.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vdroid.tyc.AppExecutors;
import com.vdroid.tyc.R;
import com.vdroid.tyc.database.AppDatabase;
import com.vdroid.tyc.database.entities.CourseEntity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseListActivity extends AppCompatActivity {

    private final int ACTION_OPEN_DOCUMENT = 654;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_course_list);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        final Intent getContentIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        getContentIntent.addCategory(Intent.CATEGORY_OPENABLE);
        getContentIntent.setType("*/*");
        //getContentIntent.setType("text/csv");
        final Intent chooserIntent = Intent.createChooser(getContentIntent, getString(R.string.intent_chooser));
        startActivityForResult(chooserIntent, ACTION_OPEN_DOCUMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_OPEN_DOCUMENT) {
            if (resultCode == RESULT_OK && data.getData() != null && data.getData().getPath() != null) {

                CourseEntity entity = new CourseEntity();
                entity.setFileUri(data.getData().toString());
                // TODO zmenit meno
                entity.setName(data.getData().toString());
                AppExecutors.getInstance().diskIO().execute(() -> {
                    AppDatabase.getInstance(this).courseDao().insert(entity);
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}
