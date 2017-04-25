package com.codekul.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by aniruddha on 21/4/17.
 */

public class MyDialog extends DialogFragment {

    public static final String TAG_ALERT = "alert";
    public static final String TAG_DATE = "datePicker";
    public static final String TAG_TIME = "timePicker";
    public static final String TAG_PROGRESS = "progress";
    public static final String TAG_CUSTOM = "custom";

    private DialogInterface.OnClickListener alertClick;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private LayoutInflater inflater;

    public void setAlertClick(DialogInterface.OnClickListener alertClick) {
        this.alertClick = alertClick;
    }

    public void setDateSetListener(DatePickerDialog.OnDateSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
    }

    public void setTimeSetListener(TimePickerDialog.OnTimeSetListener timeSetListener) {
        this.timeSetListener = timeSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        inflater = LayoutInflater.from(getActivity());

        if (getTag().equals(TAG_ALERT)) dialog = alert();

        if (getTag().equals(TAG_DATE))
            dialog = new DatePickerDialog(getActivity(), dateSetListener, 2017, 4, 21);
        if (getTag().equals(TAG_TIME))
            dialog = new TimePickerDialog(getActivity(), timeSetListener, 7, 46, false);
        if(getTag().equals(TAG_PROGRESS)) dialog = progress();
        if(getTag().equals(TAG_CUSTOM)) dialog = custom();

        return dialog;
    }

    private Dialog alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Title")
                .setMessage("Message")
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton("yes", alertClick)
                .setNegativeButton("no", alertClick)
                .setNeutralButton("neutral", alertClick);

        return builder.create();
    }

    private Dialog progress() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Message");
        pd.setTitle("Title");
        pd.setIcon(R.mipmap.ic_launcher_round);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return pd;
    }

    private Dialog custom() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();

        View view = inflater.inflate(R.layout.custom_dialog, null, false);
        dialog.setView(view);

        return dialog;
    }
}
