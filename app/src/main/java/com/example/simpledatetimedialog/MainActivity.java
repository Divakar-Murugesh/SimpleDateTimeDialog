package com.example.simpledatetimedialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DateTimeDialog.Builder(MainActivity.this)
                        .setDateTimeListener(new DateTimeDialog.DateTimeListener() {
                            @Override
                            public void onDateTimeSet(String date) {
                                Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDateTimeCancel() {
                                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setInitialDate(new Date())
                        .DateDialog()
                        .displayDialog();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DateTimeDialog.Builder(MainActivity.this)
                        .setDateTimeListener(new DateTimeDialog.DateTimeListener() {
                            @Override
                            public void onDateTimeSet(String time) {
                                Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDateTimeCancel() {
                                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setInitialDate(new Date())
                        .TimeDialog()
                        .displayDialog();
            }
        });
    }
}
