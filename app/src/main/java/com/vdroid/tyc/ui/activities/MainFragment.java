package com.vdroid.tyc.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vdroid.tyc.R;
import com.vdroid.tyc.model.CsvSource;
import com.vdroid.tyc.model.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvRow;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_question_word)
    TextView tvQuestionWord;
    @BindViews({R.id.btn_option_1, R.id.btn_option_2, R.id.btn_option_3, R.id.btn_option_4})
    List<Button> btnOptions;
    @BindView(R.id.tv_score)
    TextView tvScore;

    private CsvContainer mCsvContainer;
    private int mIndex = 0;
    private int mCountOfCorrectAnswers = 0;
    private boolean mAnswerChecked = false;
    private Uri mCsvUri;
    MediaPlayer mediaPlayer;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_main, container, false);
        ButterKnife.bind(this, view);

        hideOptionButtons();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        loadCsv();
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.success);
        // TODO: Use the ViewModel
    }

    public void setCsvUri(Uri csvUri) {
        mCsvUri = csvUri;
    }

    public void loadCsv() {
        try {
            mCsvContainer = CsvSource.load(getContext(), mCsvUri);

            showOptionButtons();
            onNewWord(mCsvContainer.getRow(mIndex));

            tvScore.setText("1/" + mCsvContainer.getRowCount());
        } catch (Exception ex) {
            Toast.makeText(getContext(), getString(R.string.e_cannot_read_csv), Toast.LENGTH_LONG).show();
        }
    }

    public void onNewWord(CsvRow row) {

        mAnswerChecked = false;
        tvQuestionWord.setText(row.getField(0));

        for (int i = 0; i < btnOptions.size(); i++) {
            Button btn = btnOptions.get(i);
            btn.setBackgroundColor(getResources().getColor(R.color.default_option_button));
            btn.setText(row.getField(i + 1));
        }
    }

    @OnClick({R.id.btn_option_1, R.id.btn_option_2, R.id.btn_option_3, R.id.btn_option_4})
    public void onOptionsClick(Button button) {

        if (mAnswerChecked) {
            return;
        }
        int correctAnswerIndex = Integer.parseInt(mCsvContainer.getRow(mIndex).getField(5));

        if (Integer.parseInt(button.getTag().toString()) == correctAnswerIndex) {
            mCountOfCorrectAnswers++;
            mediaPlayer.start();
        } else {
            // ak vybrata odpoved nie je spravna
            button.setBackgroundColor(getResources().getColor(R.color.fail_option_button));
        }

        // vyznacenie spravnej odpovede
        btnOptions.get(correctAnswerIndex - 1).setBackgroundColor(getResources().getColor(R.color.correct_option_button));
        mAnswerChecked = true;
    }

    @OnClick({R.id.btn_next})
    public void onNextClick(Button button) {
        if (!mAnswerChecked) {
            return;
        }

        mIndex++;
        if (mCsvContainer.getRowCount() < mIndex + 1) {
            Intent resultIntent = new Intent(getContext(), ResultActivity.class);
            resultIntent.putExtra(ResultActivity.EXTRA_COUNT_OF_CORRECT_ANSWERS, mCountOfCorrectAnswers);
            resultIntent.putExtra(ResultActivity.EXTRA_COUNT_OF_TOTAL_ANSWERS, mCsvContainer.getRowCount());
            startActivity(resultIntent);
            return;
        }
        tvScore.setText(String.valueOf(mIndex + 1) + "/" + mCsvContainer.getRowCount());

        onNewWord(mCsvContainer.getRow(mIndex));
    }

    private void hideOptionButtons() {
        for (Button btn : btnOptions) {
            btn.setVisibility(View.INVISIBLE);
        }
    }

    private void showOptionButtons() {
        for (Button btn : btnOptions) {
            btn.setVisibility(View.VISIBLE);
        }
    }
}