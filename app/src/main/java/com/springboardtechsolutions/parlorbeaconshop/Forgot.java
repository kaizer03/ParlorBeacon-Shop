package com.springboardtechsolutions.parlorbeaconshop;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Forgot extends AppCompatActivity {

    RequestQueue requestQueue;
    String forgot_url = "http://parlorbeacon.com/androidapp/shopkeeper/forgot.php";

    @Bind(R.id.email_forget)
    EditText emailtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(Forgot.this);

        requestQueue = Volley.newRequestQueue(Forgot.this);

        if(!loadData().equals(""))
        {
            startActivity(new Intent(Forgot.this,Forgot_Text.class));
            finish();
        }

    }

    @OnClick(R.id.forget_button)
    public void onClick1(View view)
    {
        final String email = emailtext.getText().toString();

        if (isNetworkAvailable()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, forgot_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (response.equals("Success")) {
                            saveemail_forgot(email);
                            startActivity(new Intent(Forgot.this,Forgot_Text.class));
                            finish();
                        } else {
                            Toast.makeText(Forgot.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Forgot.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Forgot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("email", email);
                    return parameters;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(Forgot.this, "Please Connect to internet and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    protected String loadData() {
        String FILENAME = "email_forgot.txt";
        String out = "";
        try {
            FileInputStream fis1 = getApplication().openFileInput(FILENAME);
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

    public void saveemail_forgot(String pin)
    {
        String FILENAME = "email_forgot.txt";

        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(pin.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
