package com.codekul.listview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by aniruddha on 15/6/17.
 */

public class DialogUtil extends DialogFragment {

    public static final String TAG_ADDRESS = "address";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = null;
        if(getTag().equals(TAG_ADDRESS)) dialog = address();
        return dialog;
    }

    private Dialog address() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("Yes", null);

        return builder.create();
    }
}
