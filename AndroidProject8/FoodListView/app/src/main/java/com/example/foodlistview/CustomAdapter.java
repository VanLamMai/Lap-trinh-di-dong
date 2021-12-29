package com.example.foodlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private ArrayList<Product> arrProduct;

    public CustomAdapter(Context context, int resource, ArrayList<Product> arrProduct) {
        super(context, resource, arrProduct);

        this.context=context;
        this.resource=resource;
        this.arrProduct=arrProduct;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.ivAvatar =(ImageView) convertView.findViewById(R.id.iv_Avatar);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_Name);
            viewHolder.tvDescription =(TextView) convertView.findViewById(R.id.tv_Description);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_Price);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = arrProduct.get(position);

        viewHolder.ivAvatar.setImageResource(product.getImage());
        viewHolder.tvName.setText(product.getName());
        viewHolder.tvDescription.setText(product.getDescription());
        viewHolder.tvPrice.setText(product.getPrice() + "đ");

        return convertView;
    }
    public class ViewHolder{
        ImageView ivAvatar;
        TextView tvName, tvDescription, tvPrice;
    }
}
