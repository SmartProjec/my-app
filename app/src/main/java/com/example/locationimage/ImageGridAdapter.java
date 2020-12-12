package com.example.locationimage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageGridAdapter  extends BaseAdapter {
    Context context;
    List<DataModel> AllList=new ArrayList<>();
    LayoutInflater layoutInflater;

    public ImageGridAdapter(Context context, List<DataModel> allList) {
        this.context = context;
        AllList = allList;

        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return AllList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflater.inflate(R.layout.custom_image,null);
        ImageView i=view.findViewById(R.id.MyImage);
        DataModel dataModel=AllList.get(position);
        Glide.with(context).load(""+dataModel.getImage()).centerCrop().placeholder(R.drawable.ic_account_circle_black_24dp).into(i);

        /*i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context,DisplayDataActivity.class);
                i.putExtra("Address",dataModel.getAddress());
                i.putExtra("Image",dataModel.getImage());
                i.putExtra("Document",dataModel.getDocument());

                context.startActivity(i);

            }
        });*/
        return view;
    }
}
