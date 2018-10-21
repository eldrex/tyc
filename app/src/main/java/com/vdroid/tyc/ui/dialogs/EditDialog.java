package com.vdroid.tyc.ui.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vdroid.tyc.R;

public class EditDialog {

    public interface OnSaveEditDialogClickListener {
        void OnClick(String text);
    }

    final AlertDialog mDialog;

    public EditDialog(@NonNull Context context,
                      OnSaveEditDialogClickListener positiveAction) {

        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_set_name_title)
                .setView(input);

        if (positiveAction != null) {
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                positiveAction.OnClick(input.getText().toString());
            });
        }
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {

        });
        mDialog = builder.create();
    }

    public void show() {
        mDialog.show();
    }

}


