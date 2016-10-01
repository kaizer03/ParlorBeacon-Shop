package com.springboardtechsolutions.parlorbeaconshop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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


public class LoginShop extends AppCompatActivity {

    RequestQueue requestQueue;
    String login_url = "http://parlorbeacon.com/androidapp/shopkeeper/login.php";
    String login_name_url = "http://parlorbeacon.com/androidapp/shopkeeper/login_name.php";

    @Bind(R.id.login_shop_email)
    EditText emailtext;
    @Bind(R.id.login_shop_password)
    EditText passtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_shop);

        if(!NoInternetToast.isNetworkAvailable(LoginShop.this))
        {
            NoInternetToast.nointernettoast(LoginShop.this);
        }

        if (Build.VERSION.SDK_INT >= 23) {

            if (!Settings.canDrawOverlays(getApplicationContext()))
                new AlertDialog.Builder(this)

                        .setMessage("Starting from Android 6, " + getResources().getString(R.string.app_name) + " needs permission to display notifications. Click enable to proceed")
                        .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                androidM();
                            }
                        })

                        .show();

        }


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ButterKnife.bind(this);

        if(!loadData().equalsIgnoreCase("") && !loadData2().equals(""))
        {
            startActivity(new Intent(LoginShop.this,MS_Shop.class));
            finish();
        }

    }

    public void androidM() {
        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 123);
    }


    @OnClick(R.id.login_shop_button)
    public void onClick1(View view)
    {
        String email = emailtext.getText().toString();
        String pass = passtext.getText().toString();

        if(!email.contains("@") && !email.contains(".com"))
        {
            Toast.makeText(LoginShop.this,"Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoginCheck(email,pass);
        }
    }

    public void LoginCheck(final String email, final String password) {
        if (NoInternetToast.isNetworkAvailable(LoginShop.this)) {
            final ProgressDialog loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    try {
                        if (response.equalsIgnoreCase(String.valueOf(0))) {
                            saveEmailShop(email);
                            savenameShop();
                            startActivity(new Intent(LoginShop.this,MS_Shop.class));
                            finish();
                        } else if (response.equalsIgnoreCase(String.valueOf(1))) {
                            Toast.makeText(LoginShop.this, "Your Request has not been accepted please wait.", Toast.LENGTH_SHORT).show();
                        } else if (response.equalsIgnoreCase(String.valueOf(2))) {
                            Toast.makeText(LoginShop.this, "Sorry You haven't Registered Yet", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(LoginShop.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(LoginShop.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("email", email);
                    parameters.put("password", password);
                    return parameters;
                }
            };
            requestQueue.add(stringRequest);

        } else {
            NoInternetToast.nointernettoast(LoginShop.this);
        }
    }

    @OnClick(R.id.login_shop_signup)
    public void onClick2(View view)
    {
        startActivity(new Intent(LoginShop.this,SignUpShop.class));
    }

    @OnClick(R.id.login_shop_forgot)
    public void onClick3(View view)
    {
        startActivity(new Intent(LoginShop.this,Forgot.class));
    }

    public void saveEmailShop(String mailid)
    {
        String FILENAME = "emailshop.txt";

        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(mailid.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String loadData() {
        String FILENAME = "emailshop.txt";
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

    protected String loadData2() {
        String FILENAME = "shopownername.txt";
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

    public void savenameShop()
    {
        if (NoInternetToast.isNetworkAvailable(LoginShop.this)) {
            final ProgressDialog loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_name_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    try {
                        if(!response.equals("Failed")) {
                            String FILENAME = "shopownername.txt";
                            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
                            fos.write(response.getBytes());
                        }
                    } catch (Exception e) {
                        Toast.makeText(LoginShop.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(LoginShop.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("email", loadData());
                    return parameters;
                }
            };
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(LoginShop.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
        }
    }

}