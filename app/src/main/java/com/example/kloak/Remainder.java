package com.example.kloak;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class Remainder extends AppCompatActivity {
    static ArrayList<String> notes=new ArrayList<String>();
    static ArrayAdapter arrayAdapter;

    public void adding(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);

        ListView listView=(ListView)findViewById(R.id.List);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.kloak", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {

            notes.add("Example note");

        } else {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, R.layout.listres,R.id.list_content, notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int itemtodelete=position;
                new AlertDialog.Builder(Remainder.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Remove Note").setMessage("Are you sure about removing it?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(itemtodelete);
                        arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.kloak", Context.MODE_PRIVATE);
                        HashSet<String> set=new HashSet(Remainder.notes);
                        sharedPreferences.edit().putStringSet("notes",set).apply();
                    }
                }).setNegativeButton("No",null).show();
                return true;
            }
        });

    }
}