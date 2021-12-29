package com.example.randomemoji;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btnRandom;
    TextView generateIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Random myRandom = new Random();
        btnRandom =(Button)findViewById(R.id.btnRandom);
        final TextView generateIcon=(TextView) findViewById(R.id.generateIcon);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               generateIcon.setText(String.valueOf(myRandom.nextInt(10)));
            }
        });
    }
}
