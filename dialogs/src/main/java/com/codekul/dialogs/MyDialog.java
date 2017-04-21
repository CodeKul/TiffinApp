package com.codekul.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by aniruddha on 21/4/17.
 */

public class MyDialog extends DialogFragment {

    public static final String TAG_ALERT = "alert";
    public static final String TAG_DATE = "datePicker";

    private DialogInterface.OnClickListener alertClick;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public void setAlertClick(DialogInterface.OnClickListener alertClick) {
        this.alertClick = alertClick;
    }

    public void setDateSetListener(DatePickerDialog.OnDateSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());

        if (getTag().equals(TAG_ALERT)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Title")
                    .setMessage("Message")
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setPositiveButton("yes", alertClick)
                    .setNegativeButton("no", alertClick)
                    .setNeutralButton("neutral", alertClick);

            dialog = builder.create();
        }
        if (getTag().equals(TAG_DATE)) {
            dialog = new DatePickerDialog(getActivity(), dateSetListener, 2017, 4, 21);
        }

        return dialog;
    }
}
