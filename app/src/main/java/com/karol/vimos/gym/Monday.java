package com.karol.vimos.gym;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.collection.LLRBNode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by Vimos on 22/11/16.
 */

public class Monday extends Fragment {


    //Overriden method onCreateView

    TextView reserve_time_firstslot,reserve_time_secondslot,
            book_fslot,book_sslot,
            unreserve_time_firstslot,unreserve_time_secondslot,
            tot_fslot,tot_sslot;
    int number=0;
    //Boolean firstres=false,secondres=false;
    SharedPreferences sharedPref;
    private Firebase mref;
    int total_fslot,total_sslot;
    final Semaphore semaphore = new Semaphore(0);




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
       View infl = inflater.inflate(R.layout.monday, container, false);

        book_fslot  = (TextView)infl.findViewById(R.id.first_slot);
        book_sslot  = (TextView)infl.findViewById(R.id.second_slot);
        tot_fslot  = (TextView)infl.findViewById(R.id.tv_fslot);
        tot_sslot  = (TextView)infl.findViewById(R.id.tv_sslot);


        Firebase.setAndroidContext(getActivity());
        mref = new Firebase("https://gym-reserve.firebaseio.com/Monday");

        /*mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Integer> itemPrice = dataSnapshot.getValue(Map.class);
                total_fslot = itemPrice.get("Total_Fslot");
                total_sslot = itemPrice.get("Total_Sslot");

                tot_fslot.setText(String.valueOf(total_fslot));
                tot_sslot.setText(String.valueOf(total_sslot));


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        reserve_time_firstslot  = (TextView)infl.findViewById(R.id.reserve_fslot);
        sharedPref=getActivity().getPreferences(Context.MODE_PRIVATE);
        String mark1 = sharedPref.getString("c1","");
        String mark2 = sharedPref.getString("c2","");

        //boolean fslot = sharedPref.getBoolean("c3",false);
        book_fslot.setText(mark1);
        book_sslot.setText(mark2);

        //int fslotnum = Integer.valueOf(book_fslot.getText().toString());






        reserve_time_firstslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reserve_time_firstslot.getText().toString().equals("Reserve")) {


                    if (book_fslot.getText().toString()==null) {

                        book_fslot.setText(String.valueOf("0"));

                    }
                    if (Integer.valueOf(book_fslot.getText().toString()) == 0) {


                       fetch_data();
                   /* number1++;
                    book_fslot.setText(String.valueOf(number1));
                    sharedPref.edit().putString("c1",book_fslot.getText().toString()).apply();
                    //sharedPref.edit().putBoolean("c3",firstres).apply();
*/
                        reserve_slot(number, "c1", book_fslot, total_fslot, "Total_Fslot");
                        //updateReserveSlot(total_fslot,"Total_Fslot");
                        sharedPref.edit().putString("c4","RED").apply();

                        reserve_time_firstslot.setText("Unreserve");
                        reserve_time_firstslot.setBackgroundColor(Color.parseColor("#ff0000"));




                    }


                }else {

                    fetch_data();

                    if(Integer.valueOf(book_fslot.getText().toString())==1) {
                        int num = Integer.valueOf(book_fslot.getText().toString());
                   /* num1--;
                    book_fslot.setText(String.valueOf(num1));
                    number1=0;
                    sharedPref.edit().putString("c1",book_fslot.getText().toString()).apply();

                    updateUnreserveSlot(total_fslot,"Total_Fslot");
*/
                        sharedPref.edit().putString("c4","NOTRED").apply();
                        unReserved_slot(num,book_fslot,"c1",total_fslot,"Total_Fslot");
                        reserve_time_firstslot.setText("Reserve");
                        reserve_time_firstslot.setBackgroundColor(Color.parseColor("#00cc00"));

                    }

                }


            }

        });


/*
        unreserve_time_firstslot  = (TextView)infl.findViewById(R.id.unreserve_fslot);
        unreserve_time_firstslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = Integer.valueOf(book_fslot.getText().toString());
                if(Integer.valueOf(book_fslot.getText().toString())==1) {
                    int num = Integer.valueOf(book_fslot.getText().toString());
                   *//* num1--;
                    book_fslot.setText(String.valueOf(num1));
                    number1=0;
                    sharedPref.edit().putString("c1",book_fslot.getText().toString()).apply();

                    updateUnreserveSlot(total_fslot,"Total_Fslot");
*//*

                    unReserved_slot(num,book_fslot,"c1",total_fslot,"Total_Fslot");
                    reserve_time_firstslot.setBackgroundColor(Color.parseColor("#ff0000"));
                }
            }
        });*/


        reserve_time_secondslot  = (TextView)infl.findViewById(R.id.reserve_sslot);
        reserve_time_secondslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(book_sslot)==null) {

                    book_sslot.setText(String.valueOf("0"));

                }
                if(Integer.valueOf(book_sslot.getText().toString())==0)
                {
                    reserve_slot(number,"c2",book_sslot,total_sslot,"Total_Sslot");
                }

            }
        });

        unreserve_time_secondslot  = (TextView)infl.findViewById(R.id.unreserve_sslot);
        unreserve_time_secondslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int num2 = Integer.valueOf(book_sslot.getText().toString());
                if(Integer.valueOf(book_sslot.getText().toString()) == 1)
                {
                    int num = Integer.valueOf(book_sslot.getText().toString());
                    unReserved_slot(num,book_sslot,"c2",total_sslot,"Total_Sslot");
                }
            }
        });

        return  infl;
    }

public void updateReserveSlot(int t_Rslot,String key)
{
              t_Rslot++;

            Toast.makeText(getContext(),String.valueOf(t_Rslot),
                    Toast.LENGTH_SHORT).show();


    HashMap<String, Object> result = new HashMap<>();
    result.put(key, t_Rslot);
    mref.updateChildren(result);

}

    public void updateUnreserveSlot(int t_Uslot,String key)
    {
          t_Uslot--;

        Toast.makeText(getContext(),String.valueOf(t_Uslot),
                Toast.LENGTH_SHORT).show();


        HashMap<String, Object> result = new HashMap<>();
        result.put(key, t_Uslot);
        mref.updateChildren(result);

    }
   public  void reserve_slot(int number_R,String c1,TextView tv,int totalvalue,String firebase_Total)
   {


        number_R++;
        tv.setText(String.valueOf(number_R));
        sharedPref.edit().putString(c1,tv.getText().toString()).apply();


        updateReserveSlot(totalvalue,firebase_Total);
     }

 public  void  unReserved_slot(int num,TextView tv,String c,int total_value,String firebase_Total )
 {
     num--;
     tv.setText(String.valueOf(num));
     sharedPref.edit().putString(c,tv.getText().toString()).apply();
    // num=0;
     //secondres=false;
     updateUnreserveSlot(total_value,firebase_Total);
 }

    @Override
    public void onResume() {
        super.onResume();

        if (sharedPref.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            book_fslot.setText(String.valueOf("0"));
            book_sslot.setText(String.valueOf("0"));

            sharedPref.edit().putBoolean("firstrun", false).apply();
        }
    }


    public  void  strating_data()
    {
        String mark3 = sharedPref.getString("c4","");
        if(mark3.equals("RED"))
        {
            reserve_time_firstslot.setText("Unreserve");
            reserve_time_firstslot.setBackgroundColor(Color.parseColor("#ff0000"));
            /*Toast.makeText(getContext(),"red true",
                    Toast.LENGTH_SHORT).show();*/
            book_fslot.setText(String.valueOf("1"));
        }else{

            reserve_time_firstslot.setText("Reserve");
            reserve_time_firstslot.setBackgroundColor(Color.parseColor("#00cc00"));
            /*Toast.makeText(getContext(),"red false",
                    Toast.LENGTH_SHORT).show();*/
            book_fslot.setText(String.valueOf("0"));

        }
    }

    public void fetch_data()
    {
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Integer> itemPrice = dataSnapshot.getValue(Map.class);
                total_fslot = itemPrice.get("Total_Fslot");
                total_sslot = itemPrice.get("Total_Sslot");

                tot_fslot.setText(String.valueOf(total_fslot));
                tot_sslot.setText(String.valueOf(total_sslot));


                HashMap<String, Object> result = new HashMap<>();
                result.put("update", 420);
                mref.updateChildren(result);

                semaphore.release();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

        fetch_data();
        strating_data();


    }
}
