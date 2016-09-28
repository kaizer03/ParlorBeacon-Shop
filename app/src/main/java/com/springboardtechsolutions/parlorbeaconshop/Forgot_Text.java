package com.springboardtechsolutions.parlorbeaconshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Forgot_Text extends AppCompatActivity {

    @Bind(R.id.editText_forgot_pin) EditText forgot_pin_text;

    RequestQueue requestQueue;
    String forgot_check_url = "http://parlorbeacon.com/androidapp/shopkeeper/forgot_check.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__text);
        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(Forgot_Text.this);
    }

    public void ForgotPassConfirm(View view)
    {
        final String forgot_pin = forgot_pin_text.getText().toString();

        final ProgressDialog progressDialog = ProgressDialog.show(this,"Checking...","Please wait...",false,false);

        if (isNetworkAvailable()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, forgot_check_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                if (response.equals("Success")) {
                                    startActivity(new Intent(Forgot_Text.this,Reset_Password.class));
                                    finish();
                                } else {
                                    Toast.makeText(Forgot_Text.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(Forgot_Text.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(Forgot_Text.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<>();
                            parameters.put("email", loadData());
                            parameters.put("pin",forgot_pin);
                            return parameters;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
            Toast.makeText(Forgot_Text.this, "Please Connect to internet and try again", Toast.LENGTH_SHORT).show();
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

}
