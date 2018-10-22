package com.vdroid.tyc.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.vdroid.tyc.AppExecutors;
import com.vdroid.tyc.R;
import com.vdroid.tyc.database.AppDatabase;
import com.vdroid.tyc.database.entities.CourseEntity;
import com.vdroid.tyc.model.CsvSource;
import com.vdroid.tyc.ui.dialogs.EditDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.siegmar.fastcsv.reader.CsvContainer;

public class CourseListActivity extends AppCompatActivity {

    private final String LOG_TAG = CourseListActivity.class.getSimpleName();
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

                try {
                    // try to load
                    CsvContainer container = CsvSource.load(this, data.getData());
                    CsvSource.checkFirstLine(container);
                } catch (Exception ex) {
                    Log.e(LOG_TAG, "", ex);
                    Toast.makeText(this, getString(R.string.e_cannot_read_csv), Toast.LENGTH_LONG).show();
                    return;
                }

                final CourseEntity entity = new CourseEntity();
                entity.setFileUri(data.getData().toString());
                EditDialog editDialog = new EditDialog(this, text -> {
                    if (TextUtils.isEmpty(text)) {
                        text = "bez názvu";
                    }
                    entity.setName(text);
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        try {
                            AppDatabase.getInstance(this).courseDao().insert(entity);
                        } catch (Exception ex) {
                            // TODO handle
                            Log.e(LOG_TAG, "", ex);
                        }
                    });
                });
                editDialog.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

}