package com.example.kloak;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

   int a,b,c,d;
   TextView ChangeTime;
   boolean sre;
    SwitchCompat s4;


    String []spinnerTitles = new String[]{"Blue","Red","Green","Yellow","Pink","Purple","Sea Green","White"};
    int [] spinnerImages = new int[]{ R.drawable.blue, R.drawable.red, R.drawable.green,R.drawable.yellow,R.drawable.pink,R.drawable.purple,R.drawable.seagreen,R.drawable.whitecolor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Spinner mSpinner = (Spinner) findViewById(R.id.static_spinner);
        Spinner mSpinner1 = (Spinner) findViewById(R.id.static_spinner1);
        ChangeTime=findViewById(R.id.textView16);
        Spinner mSpinner2 = (Spinner) findViewById(R.id.static_spinner2);
        s4 = findViewById(R.id.switch4);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sre = shared.getBoolean("choice", false);
        s4.setChecked((boolean)sre);

        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor Editor1 = shared.edit();
                Editor1.putBoolean("choice", isChecked);
                Editor1.apply();
                if(sre==true)d=0;
                if(sre==false)d=1;
            }
        });

        ChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timechange=new Intent(getApplicationContext(),timeChange.class);
                startActivity(timechange);

            }
        });

       CustomAdapter mCustomAdapter = new CustomAdapter(this, spinnerTitles, spinnerImages);
        mSpinner.setAdapter(mCustomAdapter);
        //mSpinner.setSelection(8);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
              a= i;
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("spinnerChoice",a);
                prefEditor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });
        CustomAdapter mCustomAdapter1 = new CustomAdapter(this, spinnerTitles, spinnerImages);
        mSpinner1.setAdapter(mCustomAdapter1);
        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                b= i;
                SharedPreferences sharedPref1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefEditor1 = sharedPref1.edit();
                prefEditor1.putInt("spinnerChoice1",b);
                prefEditor1.apply();
            }


            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
         });
         CustomAdapter mCustomAdapter2 = new CustomAdapter(this, spinnerTitles, spinnerImages);
         mSpinner2.setAdapter(mCustomAdapter2);
         mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                c= i;
                SharedPreferences sharedPref2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefEditor2 = sharedPref2.edit();
                prefEditor2.putInt("spinnerChoice2",c);
                prefEditor2.apply();
            }


            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });

        SharedPreferences sharedPref =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int sp1=sharedPref.getInt("spinnerChoice",0);
        mSpinner.setSelection(sp1);

        SharedPreferences sharedPref1 =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int sp2=sharedPref1.getInt("spinnerChoice1",0);
        mSpinner1.setSelection(sp2);

        SharedPreferences sharedPref2 =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int sp3=sharedPref2.getInt("spinnerChoice2",0);
        mSpinner2.setSelection(sp3);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("a",a);
        intent.putExtra("b",b);
        intent.putExtra("c",c);
        intent.putExtra("d",d);
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}