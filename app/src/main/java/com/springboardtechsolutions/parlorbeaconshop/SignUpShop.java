package com.springboardtechsolutions.parlorbeaconshop;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.arulnadhan.AchievementUnlockedLib.AchievementUnlocked;

public class SignUpShop extends AppCompatActivity {

    RequestQueue requestQueue;
    String signup_url = "http://parlorbeacon.com/androidapp/shopkeeper/register.php";

    EditText shopkeepernametext,shopnametext,emailtext,passtext,shopphonetext,shopaddrtext,shopcitytext,shopziptext,shopopentext,shopclosetext;

    CheckBox tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_shop);

        requestQueue = Volley.newRequestQueue(SignUpShop.this);

        if(!loadData().equals(""))
        {
            startActivity(new Intent(SignUpShop.this,RegistrationVerification.class));
            finish();
        }

        shopkeepernametext = (EditText)findViewById(R.id.signup_shop_shopkeepername) ;
        shopnametext = (EditText)findViewById(R.id.signup_shop_shopname);
        emailtext = (EditText)findViewById(R.id.signup_shop_email);
        passtext = (EditText)findViewById(R.id.signup_shop_pass);
        shopphonetext = (EditText)findViewById(R.id.signup_shop_shopphone);
        shopaddrtext = (EditText)findViewById(R.id.signup_shop_shopaddress);
        shopcitytext = (EditText)findViewById(R.id.signup_shop_shopcity);
        shopziptext = (EditText)findViewById(R.id.signup_shop_shopzip);
        shopopentext = (EditText)findViewById(R.id.signup_shop_shopopen);
        shopclosetext = (EditText)findViewById(R.id.signup_shop_shopclose);
        tc = (CheckBox)findViewById(R.id.AcceptTC);
    }

    public void onClick1(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SignUpShop.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String openTime;
                if(selectedHour>12) {
                    openTime = String.valueOf(selectedHour-12)+":"+ String.valueOf(selectedMinute) + "PM";
                    shopopentext.setText(openTime);
                } else {
                    openTime = String.valueOf(selectedHour)+":"+ String.valueOf(selectedMinute) + "AM";
                    shopopentext.setText(openTime);
                }
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Open Time");
        mTimePicker.show();

    }

    public void onClick2(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SignUpShop.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String closeTime;
                if(selectedHour>12) {
                    closeTime = String.valueOf(selectedHour-12)+":"+ String.valueOf(selectedMinute) + "PM";
                    shopclosetext.setText(closeTime);
                } else {
                    closeTime = String.valueOf(selectedHour)+":"+ String.valueOf(selectedMinute) + "AM";
                    shopclosetext.setText(closeTime);
                }
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Close Time");
        mTimePicker.show();
    }

    public void onClick3(View view)
    {
        final String shopkeepername = shopkeepernametext.getText().toString();
        final String shopname = shopnametext.getText().toString();
        final String shopemail = emailtext.getText().toString();
        final String shoppass = passtext.getText().toString();
        final String shopphone = shopphonetext.getText().toString();
        final String shopaddr = shopaddrtext.getText().toString();
        final String shopcity = shopcitytext.getText().toString();
        final String shopzip = shopziptext.getText().toString();
        final String shopopen = shopopentext.getText().toString();
        final String shopclose = shopclosetext.getText().toString();

        if(shopkeepername.equals("") || shopname.equals("") || shopemail.equals("") || shoppass.equals("") || shopphone.equals("") || shopaddr.equals("") || shopcity.equals("") || shopzip.equals("") || shopopen.equals("") || shopclose.equals(""))
        {
            Toast.makeText(SignUpShop.this,"Please enter all the Details", Toast.LENGTH_SHORT).show();
        }
        else if(!tc.isChecked())
        {
            Toast.makeText(SignUpShop.this,"Please agree with Terms and Conditions", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!shopemail.contains("@") && !shopemail.contains(".com"))
            {
                Toast.makeText(SignUpShop.this,"Please enter a valid email address", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(!(shoppass.length()>8))
                {
                    Toast.makeText(SignUpShop.this,"Please Enter Password greater than 8 character", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, signup_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (response.equalsIgnoreCase(String.valueOf(4))) {
                                    saveEmailShop(emailtext.getText().toString());
                                    savenameShop(shopkeepernametext.getText().toString());
                                    startActivity(new Intent(SignUpShop.this,RegistrationVerification.class));
                                    finish();
                                } else if (response.equalsIgnoreCase(String.valueOf(1))) {
                                    Toast.makeText(SignUpShop.this, "Sorry You have Already Registered Yet", Toast.LENGTH_SHORT).show();
                                } else if (response.equalsIgnoreCase(String.valueOf(2))) {
                                    Toast.makeText(SignUpShop.this, "Something missing", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(SignUpShop.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUpShop.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<>();
                            parameters.put("shopkeepername",shopkeepername);
                            parameters.put("shopname", shopname);
                            parameters.put("email", shopemail);
                            parameters.put("password", shoppass);
                            parameters.put("shopaddress", shopaddr);
                            parameters.put("phoneno", shopphone);
                            parameters.put("starttime", shopopen);
                            parameters.put("endtime", shopclose);
                            parameters.put("city", shopcity);
                            parameters.put("zipcode", shopzip);
                            return parameters;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        }
    }

    public void saveEmailShop(String mailid)
    {
        String FILENAME = "emailshop1.txt";

        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(mailid.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savenameShop(String name)
    {
        String FILENAME = "shopownername.txt";

        try {
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(name.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
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
}
