package com.soren.sagen.driemsfirebase;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;

public class EnterRegIdActivty extends AppCompatActivity  implements View.OnClickListener {



    Button btnDatePicker;
    EditText txtDate;
    private int mYear, mMonth, mDay;


    private Spinner spinner;
    private static final String[]paths = {"Odd(2016-17)", "Even(2016-17)", "Odd(2017-18)","Even(2017-2018)"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_enter_reg_id_activty );


        MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544/6300978111");

        AdView  mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        btnDatePicker= findViewById(R.id.btn_date);
        txtDate= findViewById(R.id.in_date);


        btnDatePicker.setOnClickListener(this);





        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adpter = new ArrayAdapter<String>(EnterRegIdActivty.this, android.R.layout.simple_spinner_dropdown_item,paths);

        adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adpter);


        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

    }

    @Override
    public void onClick(View view) {
        if (view == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
    public void submitFun(View view)
    {
        Intent intnt=new Intent( EnterRegIdActivty.this ,ExternalResultActivity.class);
        startActivity( intnt );
    }
}
