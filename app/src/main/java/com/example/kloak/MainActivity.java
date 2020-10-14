package com.example.kloak;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.lang.Integer.valueOf;

public class MainActivity extends AppCompatActivity {

    TextClock textView;
    TextView textView2;
    TextView textView4;
    ImageView imageView;
    AnalogClock img;
    ImageButton imageButton2;
    ImageButton imageButton;
    String l1,l2;
    String final_temp="";
    TextView textView3;
    String final_wea="";


    int[] color = {Color.rgb(0, 191, 255),
            Color.rgb(193, 4, 4),
            Color.rgb(0, 143, 65),
            Color.rgb(240, 242, 9),
            Color.rgb(247, 52, 122),
            Color.rgb(135, 97, 200),
            Color.rgb(50, 153, 153),
            Color.rgb(255, 255, 255)
    };


    int a, b, c, d;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a = getIntent().getIntExtra("a", 7);
        b = getIntent().getIntExtra("b", 6);
        c = getIntent().getIntExtra("c", 6);
        d = getIntent().getIntExtra("d", 0);
        textView3 = findViewById(R.id.textView3);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        imageView = findViewById(R.id.imageView);
        imageButton2=findViewById(R.id.imageButton2);
        imageButton=findViewById(R.id.imageButton);
        img = findViewById(R.id.imageView5);

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

        if (d == 0)
            textView.setFormat12Hour("hh:mm:ss a");
        if (d == 1) {
            textView.setFormat24Hour("hh:mm:ss a");
        }
        textView.setBackgroundTintList(ColorStateList.valueOf(color[b]));
        img.setBackgroundTintList(ColorStateList.valueOf(color[c]));
        textView.setTextColor(ColorStateList.valueOf(color[a]));
        clock();


        try {
           l1 = String.valueOf(0);
           l2 = String.valueOf(0);
            Log.i("a", l1);
            Log.i("b", l2);

            String CityName = "?lat=" + l1 + "&lon=" + l2 + "&appid=2f380aa3cb6aa979aefc701a23cbd642";

            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/weather" + CityName);


        } catch (Exception e) {
            e.printStackTrace();

        }

        //get location
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };



            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }
     //--------------End of On create----------------//

     //-----------------date Settings-------------------//

  public void clock ()
  {
      final Calendar calendar = Calendar.getInstance();
      @SuppressLint("SimpleDateFormat") String dat=new SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy").format(calendar.getTime());
      String s1=dat.substring(0,3);
      String s2=dat.substring(4,10);
      String s3=dat.substring(29);
      String s4=s2+" "+s3+" , "+s1;
      textView2.setText(s4);
  }
    public void timer()
    {
        Intent intent2 = new Intent(MainActivity.this, Timer.class);
        startActivity(intent2);
        finish();
    }
    public void alarm()
    {
        Intent intent3 = new Intent(MainActivity.this, Alarm.class);
        startActivity(intent3);
        finish();
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
                Intent intent=new Intent(this, Settings.class);
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
                finish();
                break;

        }
        return true;
    }

//-------------------------Menu section end -------------------------------//

//--------------Weather report---------------------------//

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
            }
            return null;
        }
        String icon;
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

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
                JSONObject wind=jsonObject.getJSONObject("wind");
                String speed=wind.getString("speed");
                String deg=wind.getString("deg");
                final_wea="Visibility: "+vis+"\nWind =>\nSpeed: "+speed+"\nDeg: "+deg;

                final_temp="Temp: "+main1+" K\nPressure: "+pressure+" mb\nHumidity: "+humidity+"\nMin Temp: "+temp_MIN+" K\nMax Temp: "+temp_MAX+" K";
                JSONArray arr = new JSONArray(weatherInfo);
                for (int i = 0; i < arr.length(); i++) {
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
                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
                }


            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);

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