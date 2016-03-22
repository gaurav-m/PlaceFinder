package com.example.gauravmittal.hsdemo.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by gauravmittal on 20/03/16.
 */
public class DialogFragment extends android.support.v4.app.DialogFragment {

    private static final String MESSAGE = "dialog_message";
    private static final String BUTTON_TEXT = "button_text";

    public static DialogFragment newInstance(String message, String buttonText) {

        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE, message);
        bundle.putString(BUTTON_TEXT, buttonText);
        DialogFragment DialogFragment = new DialogFragment();
        DialogFragment.setArguments(bundle);
        return DialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(bundle.getString(MESSAGE))
                .setCancelable(false);
        builder.setPositiveButton(bundle.getString(BUTTON_TEXT),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
