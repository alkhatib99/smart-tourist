package com.example.smarttourist;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= findViewById(R.id.textView);
        textView.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.bangers_regular));

//
//        Typeface typeface = Typeface.createFromAsset(
//                getAssets(),
//                "bangers_regular.ttf");
//        textView.setTypeface(typeface);

    }


}