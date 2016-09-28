package com.springboardtechsolutions.parlorbeaconshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SinglePicture extends ArrayAdapter<String> {

    public Activity context;
    public String[] pic_urls;

    @Bind(R.id.SinglePic) ImageView singleimage;

    public SinglePicture(Activity context, String[] pic_urls) {
        super(context, R.layout.single_pic,pic_urls);
        this.context = context;
        this.pic_urls = pic_urls;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.single_pic, null, true);

        ButterKnife.bind(this,rowView);

        Picasso.with(context).load(pic_urls[position]).into(singleimage);

        return rowView;
    }

}
