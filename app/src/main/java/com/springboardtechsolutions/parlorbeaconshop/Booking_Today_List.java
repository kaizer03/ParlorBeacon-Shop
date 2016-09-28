package com.springboardtechsolutions.parlorbeaconshop;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Booking_Today_List extends ArrayAdapter<String> {

    RequestQueue requestQueue,requestQueue2;
    String booking_status_change_url = "http://parlorbeacon.com/androidapp/shopkeeper/bookingstatuschange.php";

    @Bind(R.id.BookingCustName) TextView CustName;
    @Bind(R.id.BookingCustEmail) TextView CustEmail;
    @Bind(R.id.BookingCustService) TextView CustService;
    @Bind(R.id.BookingCustServTime) TextView CustServTime;

    @Bind(R.id.ButtonStatus) RelativeLayout relativeLayout;
    @Bind(R.id.BookingCustStatus) TextView CustServStatus;

    private final Activity context;
    public String[] custname,custemail,custservice,custservtime,status;

    public Booking_Today_List(Activity context,String[] custname,String[] custemail,String[] custservice,String[] custservtime,String[] status) {
        super(context, R.layout.booking_today_single,custname);
        this.context = context;
        this.custname = custname;
        this.custemail = custemail;
        this.custservice = custservice;
        this.custservtime = custservtime;
        this.status=status;
        requestQueue = Volley.newRequestQueue(context);
        requestQueue2 = Volley.newRequestQueue(context);
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.booking_today_single, null, true);
        ButterKnife.bind(this,rowView);

        CustName.setText(custname[position]);
        CustEmail.setText(custemail[position]);
        CustService.setText(custservice[position]);
        CustServTime.setText(custservtime[position]);
        switch (status[position]) {
            case "accepted":
                CustServStatus.setVisibility(View.VISIBLE);
                CustServStatus.setText(status[position]);
                final Button cancel_button = (Button) rowView.findViewById(R.id.BookingCancelButton);
                cancel_button.setVisibility(View.VISIBLE);
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, booking_status_change_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        if (response.equals("Success")) {
                                            RelativeLayout relativeLayout1 = (RelativeLayout) rowView.findViewById(R.id.ButtonStatus);
                                            TextView CustServStatus = (TextView) rowView.findViewById(R.id.BookingCustStatus);
                                            CustServStatus.setText("rejected");
                                            CustServStatus.setVisibility(View.VISIBLE);
                                            relativeLayout1.setVisibility(View.GONE);
                                            cancel_button.setVisibility(View.GONE);
                                            Toast.makeText(context, "Done for " + custservice[position] + " at " + custservtime[position], Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Failed for " + custservice[position] + " at " + custservtime[position], Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<>();
                                    parameters.put("shopemail", loadData());
                                    parameters.put("custemail", custemail[position]);
                                    parameters.put("service", custservice[position]);
                                    parameters.put("servicetime", custservtime[position]);
                                    parameters.put("status", "no");

                                    return parameters;
                                }
                            };
                            requestQueue.add(stringRequest);
                        } else {
                            Toast.makeText(context, "Please Connect to internet and try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case "rejected":
                CustServStatus.setVisibility(View.VISIBLE);
                CustServStatus.setText(status[position]);
                break;
            case "pending":
                relativeLayout.setVisibility(View.VISIBLE);

                Button acceptbutton = (Button) rowView.findViewById(R.id.BookingAccept);
                acceptbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, booking_status_change_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        if (response.equals("Success")) {
                                            RelativeLayout relativeLayout1 = (RelativeLayout) rowView.findViewById(R.id.ButtonStatus);
                                            TextView CustServStatus = (TextView) rowView.findViewById(R.id.BookingCustStatus);
                                            CustServStatus.setText("accepted");
                                            CustServStatus.setVisibility(View.VISIBLE);
                                            relativeLayout1.setVisibility(View.GONE);
                                            Toast.makeText(context, "Done for " + custservice[position] + " at " + custservtime[position], Toast.LENGTH_SHORT).show();
                                            Button cancel_button = (Button) rowView.findViewById(R.id.BookingCancelButton);
                                            cancel_button.setVisibility(View.VISIBLE);
                                            status[position] = "accepted";
                                            notifyDataSetChanged();
                                        } else {
                                            Toast.makeText(context, "Failed for " + custservice[position] + " at " + custservtime[position], Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<>();
                                    parameters.put("shopemail", loadData());
                                    parameters.put("custemail", custemail[position]);
                                    parameters.put("service", custservice[position]);
                                    parameters.put("servicetime", custservtime[position]);
                                    parameters.put("status", "yes");

                                    return parameters;
                                }
                            };
                            requestQueue.add(stringRequest);
                        } else {
                            Toast.makeText(context, "Please Connect to internet and try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Button rejectbutton = (Button) rowView.findViewById(R.id.BookingReject);
                rejectbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, booking_status_change_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        if (response.equals("Success")) {
                                            RelativeLayout relativeLayout1 = (RelativeLayout) rowView.findViewById(R.id.ButtonStatus);
                                            TextView CustServStatus = (TextView) rowView.findViewById(R.id.BookingCustStatus);
                                            CustServStatus.setText("rejected");
                                            CustServStatus.setVisibility(View.VISIBLE);
                                            relativeLayout1.setVisibility(View.GONE);
                                            Toast.makeText(context, "Done for " + custservice[position] + " at " + custservtime[position], Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Failed for " + custservice[position] + " at " + custservtime[position], Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<>();
                                    parameters.put("shopemail", loadData());
                                    parameters.put("custemail", custemail[position]);
                                    parameters.put("service", custservice[position]);
                                    parameters.put("servicetime", custservtime[position]);
                                    parameters.put("status", "no");

                                    return parameters;
                                }
                            };
                            requestQueue.add(stringRequest);
                        } else {
                            Toast.makeText(context, "Please Connect to internet and try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
        return rowView;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    protected String loadData() {
        String FILENAME = "emailshop.txt";
        String out = "";

        try {
            FileInputStream fis1 = context.openFileInput(FILENAME);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String sLine1;

            while (((sLine1 = br1.readLine()) != null)) {
                out += sLine1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return out;
    }

}
