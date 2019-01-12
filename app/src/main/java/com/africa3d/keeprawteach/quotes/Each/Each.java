package com.africa3d.keeprawteach.quotes.Each;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.africa3d.keeprawteach.quotes.Alert.ShowAlert;
import com.africa3d.keeprawteach.quotes.Database.Database;
import com.africa3d.keeprawteach.quotes.Model.Data;
import com.africa3d.keeprawteach.quotes.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Each extends AppCompatActivity implements TextToSpeech.OnInitListener {

    Toolbar toolbar;

    ProgressDialog progressDialog;

    FirebaseDatabase database;

    DatabaseReference reference;

    AdapterClass adapterClass;

    ArrayList<String> arrayList = new ArrayList<>();

    ListView listView;

    ArrayAdapter<String> arrayAdapter;

    Data data;

    Spacecraft spacecraft;

    ArrayList<Spacecraft> spacecraftArrayList;

    private RewardedVideoAd mRewardedVideoAd;

    private AdView mAdView;

    ShowAlert showAlert;

    AdRequest adRequest;

    Database db;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each);

        db = new Database(this);

        tts = new TextToSpeech(this, this);

        showAlert = new ShowAlert(this);

        mAdView = findViewById(R.id.adView);

        adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        listView = (ListView) findViewById(R.id.listview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        String type = getIntent().getStringExtra("type");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(type);

        adapterClass = new AdapterClass(getApplicationContext(), arrayList);

        listView.setAdapter(adapterClass);

        addData(type);

        loadBig();

    }

    private void loadBig() {

        final InterstitialAd interstitialAd = new InterstitialAd(getApplicationContext());

        interstitialAd.setAdUnitId("ca-app-pub-6606854533776811/9351458569");

        interstitialAd.loadAd(adRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }


            @Override
            public void onAdLoaded() {

                interstitialAd.show();

                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                SpeakOut("");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


    private class Spacecraft {
        String name;

        public Spacecraft() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class AdapterClass extends ArrayAdapter {

        ArrayList<String> status;

        public AdapterClass(Context context, ArrayList<String> status) {

            super(context, R.layout.each, R.id.textView, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.each, parent, false);

            ImageView Copy = (ImageView) view.findViewById(R.id.copy);

            ImageView Share = (ImageView) view.findViewById(R.id.share);

            ImageView Favourite = (ImageView) view.findViewById(R.id.favourite);

            ImageView Speak = (ImageView) view.findViewById(R.id.speak);

            TextView textView = (TextView) view.findViewById(R.id.textView);

            textView.setText(status.get(position));

            final String share = textView.getText().toString();

            Copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCopy(share);
                }
            });
            Share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openShare(share);
                }
            });

            Favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addtoDb(share);
                }
            });

            Speak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpeakOut(share);
                }
            });

            return view;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void SpeakOut(String share) {


        CharSequence text = share;

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,"id1");
    }

    private void addtoDb(String share) {

        boolean isInserted = db.addFavor(share);

        if (isInserted == true) {
            Snackbar.make(listView, "Added to Favourite", Snackbar.LENGTH_LONG).setAction("Action", null).show();


        } else {

            Snackbar.make(listView, "Failed", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }

    private void openCopy(String share) {

        int sdk_Version = android.os.Build.VERSION.SDK_INT;
        if (sdk_Version < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(share);   // Assuming that you are copying the text from a TextView
            Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", share);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openShare(String share) {

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);

        whatsappIntent.setType("text/plain");

        whatsappIntent.setPackage("com.whatsapp");

        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "" + share);

        try {

            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }


    private void addData(String type) {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading,please wait...");

        progressDialog.show();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("" + type);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                arrayList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    spacecraft = new Spacecraft();

                    String name = (String) messageSnapshot.child("name").getValue();

                    arrayList.add(name);

                    adapterClass.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            showAlert.openRate();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
