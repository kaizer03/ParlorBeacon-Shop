package com.springboardtechsolutions.parlorbeaconshop;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_Employee extends AppCompatActivity {

    @Bind(R.id.add_emp_name)
    EditText nametext;
    @Bind(R.id.add_emp_email)
    EditText emailtext;
    @Bind(R.id.add_emp_designation)
    EditText designationtext;

    String add_url = "http://parlorbeacon.com/androidapp/employee/addemployee.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__employee);
        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.add_emp_button)
    public void onClick(View view)
    {
        String name = nametext.getText().toString();
        String email = emailtext.getText().toString();
        String desig = designationtext.getText().toString();

        if(!email.contains("@") && !email.contains(".com"))
        {
            Toast.makeText(Add_Employee.this,"Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            addemp(name,email,desig);
        }
    }
    
    public void addemp(final String name, final String email, final String desig)
    {
        if (NoInternetToast.isNetworkAvailable(Add_Employee.this)) {
            final ProgressDialog progressDialog = new ProgressDialog(Add_Employee.this);
            progressDialog.setMessage("Please Wait Logging You In.");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, add_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.hide();
                    try {
                        if (response.equalsIgnoreCase("success")) {
                            startActivity(new Intent(Add_Employee.this,MS_Shop.class));
                            finish();
                            Toast.makeText(Add_Employee.this, "Employee Add Successfully", Toast.LENGTH_SHORT).show();
                        } else if (response.equalsIgnoreCase("failed")) {
                            Toast.makeText(Add_Employee.this, "Employee Could Not be Added due to some problem. Please Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Add_Employee.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(Add_Employee.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("shopemail", loadData());
                    parameters.put("name", name);
                    parameters.put("email", email);
                    parameters.put("designation", desig);

                    return parameters;
                }
            };
            requestQueue.add(stringRequest);
            progressDialog.dismiss();

        } else {
            NoInternetToast.nointernettoast(Add_Employee.this);
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
}
