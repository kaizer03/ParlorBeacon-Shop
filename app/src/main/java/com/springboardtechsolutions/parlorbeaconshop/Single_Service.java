package com.springboardtechsolutions.parlorbeaconshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Single_Service extends ArrayAdapter<String> {

    @Bind(R.id.ServiceName) TextView ServiceName;
    @Bind(R.id.ServiceDuration) TextView ServiceDuration;

    private final Activity context;
    public String[] servname,servdur;

    public Single_Service(Activity context,String[] servname,String[] servdur) {
        super(context, R.layout.single_service,servname);
        this.context = context;
        this.servname=servname;
        this.servdur=servdur;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.single_service, null, true);
        ButterKnife.bind(this,rowView);

        ServiceName.setText(servname[position]);
        ServiceDuration.setText(servdur[position]);

        return rowView;
    }
}