package com.springboardtechsolutions.parlorbeaconshop;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddServices extends AppCompatActivity {

    RequestQueue requestQueue;
    String service_url = "http://parlorbeacon.com/androidapp/shopkeeper/AddService.php";

    @Bind(R.id.ServiceNameAdd) EditText ServiceNameText;
    @Bind(R.id.ServiceDurAdd) EditText ServiceDurText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(AddServices.this);
    }
    
    public void AddServiceOnClick(View view)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(AddServices.this,"Updating","Please Wait",true,true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, service_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    if (response.equalsIgnoreCase("Success")) {
                        Toast.makeText(AddServices.this,"Successfully Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddServices.this,Detail_Shop.class));
                        finish();
                    } else if (response.equalsIgnoreCase("Failed")) {
                        Toast.makeText(AddServices.this, "Sorry Details cannot be updated ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddServices.this,"Error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddServices.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AddServices.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("email", loadData() );
                parameters.put("service", ServiceNameText.getText().toString());
                parameters.put("duration", ServiceDurText.getText().toString());
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
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

}
