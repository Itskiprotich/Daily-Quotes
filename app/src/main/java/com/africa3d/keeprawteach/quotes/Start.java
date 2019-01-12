package com.africa3d.keeprawteach.quotes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.africa3d.keeprawteach.quotes.Main.Drawer;
import com.africa3d.keeprawteach.quotes.Main.Main;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Start extends AppCompatActivity {
    LinearLayout l1, l2;
    Button btnsub;
    Animation uptodown, downtoup;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        btnsub = (Button) findViewById(R.id.buttonsub);

        l1 = (LinearLayout) findViewById(R.id.l1);

        l2 = (LinearLayout) findViewById(R.id.l2);

        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);

        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        l1.setAnimation(uptodown);

        l2.setAnimation(downtoup);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent intent=new Intent(Start.this, Drawer.class);

                startActivity(intent);

                finish();
            }
        }, 2000);
    }

    public void Proceed(View view) {

        Intent intent=new Intent(this, Main.class);

        startActivity(intent);

        finish();

    }
}
