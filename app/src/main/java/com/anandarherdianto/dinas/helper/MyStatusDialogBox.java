package com.anandarherdianto.dinas.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anandarherdianto.dinas.R;
import com.anandarherdianto.dinas.util.FButton;

/**
 * Created by Ananda R Herdianto on 31/07/2017.
 */

public class MyStatusDialogBox extends DialogFragment {

    private MyInterface mListener;

    private String message, type;

    public void setMessage(String message) {
        this.message = message;
    }
    public void setType(String type) {
        this.type = type;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.mystatus_dialogbox, null);

        RelativeLayout bgStatusIcon = (RelativeLayout) v.findViewById(R.id.bgStatusIcon);
        ImageView statusIcon = (ImageView) v.findViewById(R.id.statusIcon);
        FButton btnSuccess = (FButton) v.findViewById(R.id.btnSuccess);
        TextView txtMessage = (TextView) v.findViewById(R.id.successMsg);
        TextView sTxt = (TextView) v.findViewById(R.id.sTxt);

        Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/VastShadow-Regular.ttf");
        sTxt.setTypeface(myFont);

        if(type.equals("error")){
            bgStatusIcon.setBackgroundResource(R.drawable.circle_error_bg);
            statusIcon.setImageResource(R.drawable.cross);
            btnSuccess.setButtonColor(ResourcesCompat.getColor(getResources(), R.color.colorRed, null));
            sTxt.setText("Gagal!");
        }else if(type.equals("success")){
            bgStatusIcon.setBackgroundResource(R.drawable.circle_success_bg);
            statusIcon.setImageResource(R.drawable.check);
            btnSuccess.setButtonColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
            sTxt.setText("Sukses!");
        }

        txtMessage.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        final Dialog dialog = builder.create();

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("success")) {
                    mListener.onSuccess();
                    dialog.dismiss();
                }else if(type.equals("error")){
                    mListener.onError();
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }

    public interface MyInterface{
        void onSuccess();
        void onError();
    }

    @Override
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
