package com.springboardtechsolutions.parlorbeaconshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Employee_Shop extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.listView_employee) ListView employeelist;

    RequestQueue requestQueue;
    String get_url = "http://parlorbeacon.com/androidapp/employee/getemployee.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Button fab = (Button) findViewById(R.id.fab234);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employee_Shop.this,Add_Employee.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView textView = (TextView)headerView.findViewById(R.id.navempshoptext);
        textView.setText("Hello "+loadData2());


        requestQueue = Volley.newRequestQueue(Employee_Shop.this);
        getEmployee();
        
    }

    public void getEmployee() {

        final ProgressDialog progressDialog = ProgressDialog.show(this,"Fetching...","Please wait...",false,false);

        JSONObject params = new JSONObject();
        try {
            params.put("shopemail", loadData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, get_url,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray Employee = response.getJSONArray("empdetail");
                    String name[] = new String[Employee.length()];
                    String email[] = new String[Employee.length()];
                    String desig[] = new String[Employee.length()];
                    for(int i=0;i<Employee.length();i++) {
                        JSONObject singledetail = Employee.getJSONObject(i);
                        name[i] = singledetail.getString("name");
                        email[i] = singledetail.getString("email");
                        desig[i] = singledetail.getString("designation");
                    }

                    EmployeeDetailSet adapter = new EmployeeDetailSet(Employee_Shop.this,name,email,desig);
                    employeelist.setAdapter(adapter);

                }
                catch (JSONException e) {
                    Toast.makeText(Employee_Shop.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Employee_Shop.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.employee__shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.uf) {
            startActivity(new Intent(Employee_Shop.this, UpcomingFeatures.class));
            return true;
        }
        else if(id==R.id.logout){
            File dir = getFilesDir();
            File file = new File(dir, "shopownername.txt");
            file.delete();
            File file1 = new File(dir, "emailshop.txt");
            file1.delete();
            Intent p=new Intent(this,LoginShop.class);
            startActivity(p);
        }

        finish();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mybookshop) {
            startActivity(new Intent(Employee_Shop.this,Booking_Shop.class));
        } else if (id == R.id.myshop) {
            startActivity(new Intent(Employee_Shop.this,MyShop.class));
        } else if (id == R.id.myemployeeshop) {
            startActivity(new Intent(Employee_Shop.this, Employee_Shop.class));
        }else if(id == R.id.shopdetail) {
            startActivity(new Intent(Employee_Shop.this, Detail_Shop.class));
        } else if (id == R.id.aboutus) {
            startActivity(new Intent(Employee_Shop.this,About_Us.class));
        } else if (id == R.id.contactus) {
            startActivity(new Intent(Employee_Shop.this,ContactUs.class));
        }

        finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

}
