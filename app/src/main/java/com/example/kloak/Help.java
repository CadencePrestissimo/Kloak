package com.example.kloak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help extends AppCompatActivity {
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"iit2019011@iiita.ac.in"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Help needed:");
                startActivity(Intent.createChooser(feedbackEmail, "Help Query:"));
            }
        });
    }
}