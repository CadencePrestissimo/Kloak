package com.example.kloak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class MainActivity2 extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        noteId = intent.getIntExtra("noteId",-1);
        EditText editText=(EditText) findViewById(R.id.PersonName);

        if(noteId!=-1)
        {editText.setText(Remainder.notes.get(noteId));}
        else
        {
            Remainder.notes.add("");
            noteId=Remainder.notes.size()-1;
            Remainder.arrayAdapter.notifyDataSetChanged();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Remainder.notes.set(noteId,String.valueOf(s));
               Remainder.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences1234=getApplicationContext().getSharedPreferences("com.example.take_notes", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(Remainder.notes);
                sharedPreferences1234.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}