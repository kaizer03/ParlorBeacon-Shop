package com.springboardtechsolutions.parlorbeaconshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Reset_Password extends AppCompatActivity {

    @Bind(R.id.new_pass1) EditText pass1;
    @Bind(R.id.new_pass2) EditText pass2;

    RequestQueue requestQueue;
    String new_pass_url = "http://parlorbeacon.com/androidapp/shopkeeper/newpassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(Reset_Password.this);
    }

    public void passchange(View view)
    {

        if(pass1.getText().toString().equals(pass2.getText().toString()) && pass1.length()>8) {
            if (isNetworkAvailable()) {

                final ProgressDialog progressDialog = ProgressDialog.show(this,"Updating...","Please wait...",false,false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, new_pass_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            if (response.equals("Success")) {
                                File dir = getFilesDir();
                                File file = new File(dir, "email_forgot.txt");
                                file.delete();
                                startActivity(new Intent(Reset_Password.this,LoginShop.class));
                                finish();
                            } else {
                                Toast.makeText(Reset_Password.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(Reset_Password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Reset_Password.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("email", loadData());
                        parameters.put("password", pass1.getText().toString());
                        return parameters;
                    }
                };
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(Reset_Password.this, "Please Connect to internet and try again", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Reset_Password.this,"Please enter the same password",Toast.LENGTH_SHORT).show();
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
