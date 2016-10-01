package com.springboardtechsolutions.parlorbeaconshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
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
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.arulnadhan.AchievementUnlockedLib.AchievementUnlocked;

public class RegistrationVerification extends AppCompatActivity {

    @Bind(R.id.editText_Register_pin) EditText Register_Pin;

    RequestQueue requestQueue;
    String register_confirm_url = "http://parlorbeacon.com/androidapp/shopkeeper/verify_pin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_verification);
        ButterKnife.bind(this);

        AchievementUnlocked toast = new AchievementUnlocked(RegistrationVerification.this).setTitle("Account Created").setBackgroundColor(Color.parseColor("#333333")).setTitleColor(0xffffffff).setIcon(getDrawableFromRes(R.drawable.tick)).setDuration(1000).alignTop(false).isLarge(false).build();
        toast.show();
        new CountDownTimer(3500, 1000)
        {
            @Override
            public void onFinish() {
                new AchievementUnlocked(RegistrationVerification.this).setTitle("OTP has been sent").setSubTitle("Please check your email id.").setSubtitleColor(0x80000000).setIcon(getDrawableFromRes(R.drawable.chat)).isRounded(false).setDuration(2500).isLarge(true).build().show();
            }

            @Override
            public void onTick(long l) {

            }
        }.start();
        requestQueue = Volley.newRequestQueue(this);

    }

    public void verifyregister(View view)
    {
        final String R_pin = Register_Pin.getText().toString();

        final ProgressDialog progressDialog = ProgressDialog.show(this,"Verifying...","Please wait...",false,false);

        if (NoInternetToast.isNetworkAvailable(RegistrationVerification.this)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, register_confirm_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        if (response.equals("Success")) {
                                File dir = getFilesDir();
                                File file = new File(dir, "emailshop1.txt");
                                file.delete();
                            startActivity(new Intent(RegistrationVerification.this,LoginShop.class));
                            finish();
                        } else {
                            Toast.makeText(RegistrationVerification.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(RegistrationVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationVerification.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("email", loadData());
                    parameters.put("pin",R_pin);
                    return parameters;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            NoInternetToast.nointernettoast(RegistrationVerification.this);
        }
    }

    protected String loadData() {
        String FILENAME = "emailshop1.txt";
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

    private Drawable getDrawableFromRes(int ResID) {
        if (Build.VERSION.SDK_INT >= 21) getDrawable(ResID);
        return getResources().getDrawable((ResID));
    }

}
