package com.africa3d.keeprawteach.quotes.Fragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.africa3d.keeprawteach.quotes.Database.Database;
import com.africa3d.keeprawteach.quotes.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourites extends Fragment implements TextToSpeech.OnInitListener{


    ListView listView;

    ArrayList<String> arrayList = new ArrayList<>();

    Database db;

    AdapterClass adapterClass;

    AdRequest adRequest;

    private AdView mAdView;

    private TextToSpeech tts;

    private TextView textView;

    public Favourites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        db = new Database(getContext());
        tts = new TextToSpeech(getContext(), this);

        listView = (ListView) view.findViewById(R.id.list);

        textView=(TextView)view.findViewById(R.id.nofavor);

        textView.setVisibility(View.GONE);

        mAdView = view.findViewById(R.id.adView);

        adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        adapterClass = new AdapterClass(getContext(), arrayList);

        listView.setAdapter(adapterClass);

        load();

        loadBig();
        return view;
    }
    private void loadBig() {

        final InterstitialAd interstitialAd = new InterstitialAd(getContext());

        interstitialAd.setAdUnitId("ca-app-pub-8847231226622419/6330987093");

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

    private void load() {
        arrayList.clear();

        Cursor cursor = db.search();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String d = cursor.getString(1);

                arrayList.add(d);

                textView.setVisibility(View.GONE);
            }
        }else{
            textView.setVisibility(View.VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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

    private class AdapterClass extends ArrayAdapter {

        ArrayList<String> status;

        public AdapterClass(Context context, ArrayList<String> status) {

            super(context, R.layout.favourite, R.id.textView, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.favourite, parent, false);

            ImageView Copy = (ImageView) view.findViewById(R.id.copy);

            ImageView Share = (ImageView) view.findViewById(R.id.share);

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

            Speak.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    private void openCopy(String share) {

        int sdk_Version = android.os.Build.VERSION.SDK_INT;
        if (sdk_Version < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(share);   // Assuming that you are copying the text from a TextView
            Toast.makeText(getContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", share);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
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

            Toast.makeText(getContext(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
