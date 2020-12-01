package com.example.kloak;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextClock textView;
    TextView flip;
    TextView textView2;
    TextView textView4;
    ImageView imageView;
    AnalogClock img;
    ImageButton imageButton2;
    ImageButton ImageButton3;
    ImageButton stopwatchButton;
    ImageButton imageButton;
    String url;
    String final_temp="";
    TextView textView3;
    String final_wea="";
    TextView tmz;
    LinearLayout frame;
    String flipString="";
    private GPSTracker gpsTracker;

    int[] color = {Color.rgb(0, 191, 255),
            Color.rgb(193, 4, 4),
            Color.rgb(0, 143, 65),
            Color.rgb(240, 242, 9),
            Color.rgb(247, 52, 122),
            Color.rgb(135, 97, 200),
            Color.rgb(50, 153, 153),
            Color.rgb(255, 255, 255)
    };


    int a, b, c;
    Boolean d;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        a = getIntent().getIntExtra("a", 7);
        b = getIntent().getIntExtra("b", 6);
        c = getIntent().getIntExtra("c", 6);
        d = getIntent().getBooleanExtra("d", false);


        SharedPreferences  sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        a=sharedPref.getInt("spinnerChoice",7);

        SharedPreferences sharedPref1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b=sharedPref1.getInt("spinnerChoice1",6);

        SharedPreferences sharedPref2=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        c=sharedPref2.getInt("spinnerChoice2",6);

        SharedPreferences shared=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        d=shared.getBoolean("choice",false);


        flip=findViewById(R.id.flipView);
        tmz=findViewById(R.id.tmz);
        frame=findViewById(R.id.layoutframe);
        textView3 = findViewById(R.id.textView3);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        imageView = findViewById(R.id.imageView);
        imageButton2=findViewById(R.id.imageButton2);
        ImageButton3=findViewById(R.id.imageButton3);
        imageButton=findViewById(R.id.imageButton);
        img = findViewById(R.id.imageView5);
        stopwatchButton=findViewById(R.id.stopwatchButton);

       stopwatchButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               v.setAlpha(0.3f);
               v.animate().alpha(1f);
               stopWatch();
           }
       });


        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, final_temp, Toast.LENGTH_LONG).show();
                v.setAlpha(0.3f);
                v.animate().alpha(1f);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, final_wea, Toast.LENGTH_LONG).show();
                v.setAlpha(0.3f);
                v.animate().alpha(1f);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAlpha(0.3f);
                v.animate().alpha(1f);
                timer();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAlpha(0.3f);
                v.animate().alpha(1f);
               alarm();
            }
        });
        ImageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAlpha(0.3f);
                v.animate().alpha(1f);
                reminder();
            }
        });

        if (!d)
            textView.setFormat12Hour("hh:mm:ss a");
        if (d) {
            textView.setFormat24Hour("hh:mm:ss a");
        }


        textView.setBackgroundTintList(ColorStateList.valueOf(color[b]));
        img.setBackgroundTintList(ColorStateList.valueOf(color[c]));
        textView.setTextColor(ColorStateList.valueOf(color[a]));
        Clock cl=new Clock();
        String s4=cl.clock();
        textView2.setText(s4);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            else
            {
                try {
                    gpsTracker = new GPSTracker(MainActivity.this);
                    if (gpsTracker.canGetLocation()) {
                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();
                        String l1=String.valueOf(latitude);
                        String l2=String.valueOf(longitude);
                        flipString=flipString+"Latitude: "+l1+"\nLongitude: "+l2;
                        String cityname="?lat="+l1+"&lon="+l2+"&appid=9425d36f4674a3f6671361a8c71edcae";
                        url="http://api.openweathermap.org/data/2.5/weather"+cityname;
                        new doit().execute();
                    }
                    else {
                        gpsTracker.showSettingsAlert();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        flip.setText(flipString);
    }
     //--------------End of On create----------------//

     //-----------------date Settings-------------------//
    public void timer()
    {
        Intent intent2 = new Intent(MainActivity.this, Timer.class);
        startActivity(intent2);
    }

    public void stopWatch()
    {
        Intent intent5 = new Intent(MainActivity.this, StopWatch.class);
        startActivity(intent5);
    }
    public void alarm()
    {
        Intent intent3 = new Intent(MainActivity.this, MainActivityAlarm.class);
        startActivity(intent3);
    }
    public void reminder()
    {
        Intent intent4 = new Intent(MainActivity.this, Remainder.class);
        startActivity(intent4);
    }



  //----------Menu Section Start-----------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.item1:
                Intent intent=new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item2:
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"iit2019011@iiita.ac.in"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
                break;
            case R.id.item3:
                Intent intent1=new Intent(this, Help.class);
                startActivity(intent1);
                break;

        }
        return true;
    }

//-------------------------Menu section end -------------------------------//

//--------------Weather report---------------------------//

    public class  doit extends AsyncTask<Void, Void, Void>
    {

      String result;
      String icon;
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document data = Jsoup.connect(url).ignoreContentType(true).get();
                result=data.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            try {
                String message = "";
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                JSONObject main2=jsonObject.getJSONObject("main");
                String main1=main2.getString("temp");
                String pressure=main2.getString("pressure");
                String humidity=main2.getString("humidity");
                String temp_MIN=main2.getString("temp_min");
                String temp_MAX=main2.getString("temp_max");
                String vis=jsonObject.getString("visibility");
                String tm= jsonObject.getString("timezone");
                JSONObject wind=jsonObject.getJSONObject("wind");
                String speed=wind.getString("speed");
                String deg=wind.getString("deg");
                final_wea="Visibility: "+vis+"\n---Wind---\nSpeed: "+speed+"\nDeg: "+deg;
                float t=Float.valueOf(main1);
                float t1= (float) (t-272.15);
                final_temp="Temp: "+ new DecimalFormat("##.##").format(t1)+" 'C\nPressure: "+pressure+" mb\nHumidity: "+humidity+"\nMin Temp: "+temp_MIN+" K\nMax Temp: "+temp_MAX+" K";
                JSONArray arr = new JSONArray(weatherInfo);
                String tim2="Timezone: "+tm;
                tmz.setText(tim2);
                for (int i = 0; i < arr.length(); i++)
                {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String main = "";
                    icon="";
                    String description = "";

                    main = jsonPart.getString("main");
                    icon =jsonPart.getString("icon");
                    description = jsonPart.getString("description");

                    if (main != "" && description != "") {
                        message += main ;
                    }
                }

                if (message != "")
                {
                    textView3.setText(message);
                    String temp1=main1+" K";
                    textView4.setText(temp1);
                    switch(icon) {
                        case"01n":
                            imageView.setImageResource(R.drawable.i1);
                            break;
                        case"02n":
                            imageView.setImageResource(R.drawable.i2);
                            break;
                        case"03n":
                            imageView.setImageResource(R.drawable.i3);
                            break;
                        case"04n":
                            imageView.setImageResource(R.drawable.i4);
                            break;
                        case"09n":
                            imageView.setImageResource(R.drawable.i9);
                            break;
                        case"10n":
                            imageView.setImageResource(R.drawable.i10);
                            break;
                        case"11n":
                            imageView.setImageResource(R.drawable.i11);
                            break;
                        case"13n":
                            imageView.setImageResource(R.drawable.i13);
                            break;
                        case"50n":
                            imageView.setImageResource(R.drawable.i50);
                            break;

                    }

                } else {
                    //   Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
                }


            } catch (JSONException e)
            {

                //   Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);

            }




        }
    }


//-------------------------------------------------------------//
    //location permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0  && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }

    //---Complete weather info-----//
    /*


    */


}