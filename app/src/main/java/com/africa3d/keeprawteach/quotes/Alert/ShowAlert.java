package com.africa3d.keeprawteach.quotes.Alert;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.africa3d.keeprawteach.quotes.R;

public class ShowAlert {

    Context context;

    AlertDialog al;

    public ShowAlert(Context context) {
        this.context = context;
    }

    public void openRate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater lay = LayoutInflater.from(context);

        final View viewdata = lay.inflate(R.layout.alert, null);

        Button Cancel = (Button) viewdata.findViewById(R.id.cancel);

        Button Ok = (Button) viewdata.findViewById(R.id.ok);

        final RatingBar ratingBar = (RatingBar) viewdata.findViewById(R.id.Rate);

        builder.setView(viewdata);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.dismiss();
            }
        });
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float ratingvalue = ratingBar.getRating();

                if (ratingvalue == 0) {

                } else {

                    Toast.makeText(context, "Thank you for giving us " + ratingvalue + " Rating", Toast.LENGTH_SHORT).show();

                    al.dismiss();
                }

            }
        });


        builder.setCancelable(false);

        al = builder.create();

        al.show();
    }
}
