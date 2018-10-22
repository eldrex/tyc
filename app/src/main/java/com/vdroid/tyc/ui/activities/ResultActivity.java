package com.vdroid.tyc.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vdroid.tyc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    public final static String EXTRA_COUNT_OF_CORRECT_ANSWERS = "EXTRA_COUNT_OF_CORRECT_ANSWERS";
    public final static String EXTRA_COUNT_OF_TOTAL_ANSWERS = "EXTRA_COUNT_OF_TOTAL_ANSWERS";

    private int mCountOfCorrectAnswers = 0;
    private int mCountOfTotalAnswers = 0;

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.rb_result)
    RatingBar rbResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_result);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        mCountOfCorrectAnswers = getIntent().getIntExtra(EXTRA_COUNT_OF_CORRECT_ANSWERS, 0);
        mCountOfTotalAnswers = getIntent().getIntExtra(EXTRA_COUNT_OF_TOTAL_ANSWERS, 0);

        tvResult.setText(String.format("Výsledok %s/%s", mCountOfCorrectAnswers, mCountOfTotalAnswers));
        rbResult.setIsIndicator(true);
        float starCount = rbResult.getNumStars() / (float) mCountOfTotalAnswers * (float) mCountOfCorrectAnswers;
        rbResult.setRating(starCount);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }
}
