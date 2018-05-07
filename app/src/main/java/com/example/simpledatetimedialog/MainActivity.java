package com.example.simpledatetimedialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textViewDate, textViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new DateDialog(MainActivity.this).DisplayDialog(textViewDate.getText().toString(), new DateDialog.DateListener() {
                        @Override
                        public void onDateSet(String date) {
                            textViewDate.setText(date);
                        }

                        @Override
                        public void onDateCancel() {
                            Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new TimeDialog(MainActivity.this).DisplayDialog(textViewTime.getText().toString(), new TimeDialog.TimeListener() {
                        @Override
                        public void onTimeSet(String time) {
                            textViewTime.setText(time);
                        }

                        @Override
                        public void onTimeCancel() {
                            Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
