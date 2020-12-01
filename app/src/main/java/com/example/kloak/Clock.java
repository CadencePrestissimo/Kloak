package com.example.kloak;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock {

    public String clock ()
    {
        final Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") String dat=new SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy").format(calendar.getTime());
        String s1=dat.substring(0,3);
        String s2=dat.substring(4,10);
        String s3=dat.substring(29);
        String s4=s2+" "+s3+" , "+s1;
        return s4;
    }
}
