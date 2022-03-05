package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5;
    Button startDownload;

    private String TAGNAME = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          

    }

    public void onClickStartDownload(View v){
        Log.e(TAGNAME, "onClickStartDownload");
        ArrayList<String> allFiles = getFiles();

        Intent serviceIntent =  new Intent(this, MyBackgroundService.class);
        Bundle b = new Bundle();
        b.putStringArrayList("allFiles", allFiles);
        serviceIntent.putExtras(b);
        startService(serviceIntent);
    }
    private ArrayList<String> getFiles(){

        ;

        e1 = (EditText) findViewById(R.id.file1);
        String s1 = e1.getText().toString();
        e2 = (EditText) findViewById(R.id.file2);
        String s2 = e2.getText().toString();
        e3 = (EditText) findViewById(R.id.file3);
        String s3 = e3.getText().toString();
        e4 = (EditText) findViewById(R.id.file4);
        String s4 = e4.getText().toString();
        e5 = (EditText) findViewById(R.id.file5);
        String s5 = e5.getText().toString();

        ArrayList<String> allFiles = new ArrayList<String>();
        allFiles.add(s1);
        allFiles.add(s2);
        allFiles.add(s3);
        allFiles.add(s4);
        allFiles.add(s5);
        Log.e(TAGNAME, "get Files" + allFiles);

        return allFiles;
    }


}