package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
String[]titles = new String[]{"Android", "iOS", "WindowPhone"};
String[]contents = new String[]{"Đây là hệ điều hành Android",
                                "Đây là hệ điều hành iOS",
                                "Đây là hệ điều hành WindowPhone"};
int[]imgs = {R.drawable.android,
             R.drawable.ios,
             R.drawable.window_mobile};
ListView lvMain;
TextView txtDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = findViewById(R.id.lvMain);
        txtDisplay = findViewById(R.id.txtDisplay);
        ArrayList<Product> list = new ArrayList<>();
        for (int i = 0; i<titles.length; i++){
            list.add(new Product(imgs[i], titles[i], contents[i]));
        }
        MyListViewAdapter adapter = new MyListViewAdapter(list);
        Toast.makeText(getApplicationContext(), ""+adapter.getCount(), Toast.LENGTH_SHORT).show();
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                txtDisplay.setText("Bạn chon :" + titles[position]);
            }
        });
    }
}