package com.springboardtechsolutions.parlorbeaconshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EmployeeDetailSet extends ArrayAdapter<String> {

    private final Activity activity;
    private String[] name;
    private String[] email;
    private String[] desig;

    @Bind(R.id.employee_detail_name)
    TextView nametext;
    @Bind(R.id.employee_detail_email)
    TextView emailtext;
    @Bind(R.id.employee_detail_designation)
    TextView designationtext;

    EmployeeDetailSet(Activity activity, String[] name, String[] email, String[] desig) {
        super(activity, R.layout.employee_detail_set,name);
        this.activity=activity;
        this.name=name;
        this.email=email;
        this.desig = desig;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = activity.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.employee_detail_set, null, true);

        ButterKnife.bind(this,rowView);

        nametext.setText(name[i]);
        emailtext.setText(email[i]);
        designationtext.setText(desig[i]);

        return rowView;
    }
}
