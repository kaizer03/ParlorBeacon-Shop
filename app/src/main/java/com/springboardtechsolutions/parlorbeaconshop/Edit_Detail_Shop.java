package com.springboardtechsolutions.parlorbeaconshop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Edit_Detail_Shop extends AppCompatActivity {

    RequestQueue requestQueue;
    String show_url = "http://parlorbeacon.com/androidapp/shopkeeper/show.php";
    String update_url = "http://parlorbeacon.com/androidapp/shopkeeper/update.php";
    
    @Bind(R.id.edit_detail_shop_shopkeepername)
    EditText shopkeepernametext;
    @Bind(R.id.edit_detail_shop_shopname)
    EditText shopnametext;
    @Bind(R.id.edit_detail_shop_email)
    EditText emailtext;
    @Bind(R.id.edit_detail_shop_shopphone)
    EditText shopphonetext;
    @Bind(R.id.edit_detail_shop_shopaddress)
    EditText shopaddrtext;
    @Bind(R.id.edit_detail_shop_shopcity)
    EditText shopcitytext;
    @Bind(R.id.edit_detail_shop_shopzip)
    EditText shopziptext;
    @Bind(R.id.edit_detail_shop_shopopen)
    EditText shopopentext;
    @Bind(R.id.edit_detail_shop_shopclose)
    EditText shopclosetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_shop);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(Edit_Detail_Shop.this);

        Intent intent = getIntent();
        emailtext.setText(intent.getStringExtra("Email"));

        putdetail();
    }

    public void putdetail()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, show_url, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray detail=response.getJSONArray("info");
                    for(int i=0;i<detail.length();i++) {
                        JSONObject singledetail = detail.getJSONObject(i);
                        if(singledetail.getString("email").equalsIgnoreCase(emailtext.getText().toString())) {
                            shopkeepernametext.setText(singledetail.getString("shopkeepername"));
                            shopnametext.setText(singledetail.getString("shopname"));
                            shopphonetext.setText(singledetail.getString("phoneno"));
                            shopaddrtext.setText(singledetail.getString("shopaddress"));
                            shopcitytext.setText(singledetail.getString("city"));
                            shopziptext.setText(singledetail.getString("zipcode"));
                            shopopentext.setText(singledetail.getString("starttime"));
                            shopclosetext.setText(singledetail.getString("endtime"));
                        }
                    }
                }
                catch (JSONException e)
                {
                    Toast.makeText(Edit_Detail_Shop.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_Detail_Shop.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @OnClick(R.id.edit_detail_shop_shopopen)
    public void onClick1(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Edit_Detail_Shop.this, new TimePickerDialog.OnTimeSetListener() {
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

    @OnClick(R.id.edit_detail_shop_shopclose)
    public void onClick2(View view)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Edit_Detail_Shop.this, new TimePickerDialog.OnTimeSetListener() {
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

    public void onClick6(View view) {

        final ProgressDialog progressDialog = ProgressDialog.show(Edit_Detail_Shop.this,"Updating","Please Wait",true,true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            if (response.equalsIgnoreCase("success")) {
                                Toast.makeText(Edit_Detail_Shop.this,"Successfully Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Edit_Detail_Shop.this,Detail_Shop.class));
                                finish();
                            } else if (response.equalsIgnoreCase("failed")) {
                                Toast.makeText(Edit_Detail_Shop.this, "Sorry Details cannot be updated ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Edit_Detail_Shop.this,"Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Detail_Shop.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Edit_Detail_Shop.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("shopkeepername", shopkeepernametext.getText().toString());
                        parameters.put("shopname", shopnametext.getText().toString());
                        parameters.put("email", emailtext.getText().toString());
                        parameters.put("phoneno", shopphonetext.getText().toString());
                        parameters.put("shopaddress", shopaddrtext.getText().toString());
                        parameters.put("city", shopcitytext.getText().toString());
                        parameters.put("zipcode", shopziptext.getText().toString());
                        parameters.put("starttime", shopopentext.getText().toString());
                        parameters.put("endtime", shopclosetext.getText().toString());
                        return parameters;
                    }
                };
                requestQueue.add(stringRequest);
    }

}
