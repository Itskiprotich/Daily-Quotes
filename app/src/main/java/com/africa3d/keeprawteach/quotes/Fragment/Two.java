package com.africa3d.keeprawteach.quotes.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.africa3d.keeprawteach.quotes.Each.Each;
import com.africa3d.keeprawteach.quotes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Two extends Fragment {


    ListView simpleList;

    String countryList[] = {"Break Up Status", "Alone Status", "Hurt Status", "Miss You Status", "Condolence Status","Failure Status","Sick Status","Accident Status"};

    public ArrayList<String> arr;

    public ArrayAdapter adapter;

    public Two() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        simpleList = (ListView) view.findViewById(R.id.list);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.two, R.id.textView, countryList);

        simpleList.setAdapter(arrayAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Object o = simpleList.getItemAtPosition(position);

                String str=(String)o;//As you are using Default String Adapter

                passion(str);
            }
        });

        return view;
    }
    private void passion(String passion) {

        Intent intent=new Intent(getContext(), Each.class);

        Bundle bundle = new Bundle();

        bundle.putString("type", passion);

        intent.putExtras(bundle);

        startActivity(intent);
    }

}