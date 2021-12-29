package com.example.foodlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvProduct;
    private ArrayList<Product> arrayList;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProduct = (ListView) findViewById(R.id.lv_Product);

        arrayList = new ArrayList<>();

        Product product = new Product(R.drawable.hamburger,"Hamburger","Bánh mì kẹp thịt tròn",12000);
        arrayList.add(product);

        Product product1 = new Product(R.drawable.banhmi, "Bánh mì", "Bánh mì kẹp thịt", 10000);
        arrayList.add(product1);

        Product product2 = new Product(R.drawable.banhbao,"Bánh bao", "Bánh bao nhân thịt trứng", 12000);
        arrayList.add(product2);

        Product product3 = new Product(R.drawable.banhu, "Bánh ú","Bánh ú dùng cho tết, lễ", 5000);
        arrayList.add(product3);

        Product product4 = new Product(R.drawable.banhgiochay,"Bánh giò chay", "Bánh giò chay bằng nếp hoặc tẻ", 6000);
        arrayList.add(product4);

        Product product5 = new Product(R.drawable.banhgionhanthit, "Bánh giò nhân thịt", "Bánh giò nếp, tẻ có nhân thịt", 8000);
        arrayList.add(product5);

        adapter = new CustomAdapter(this, R.layout.item_listview, arrayList);

        lvProduct.setAdapter(adapter);
    }
}