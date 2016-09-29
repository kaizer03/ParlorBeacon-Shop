package com.springboardtechsolutions.parlorbeaconshop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
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


public class Service extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueue;
    ListView listView;
    String serviceshow_url = "http://parlorbeacon.com/androidapp/shopkeeper/ShowService.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Service.this,AddServices.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listView = (ListView)findViewById(R.id.listViewServices);

        requestQueue = Volley.newRequestQueue(Service.this);
        if (isNetworkAvailable()) {
            bookings();
        }
        else {
            Toast.makeText(Service.this,"Internet not connected. Please connect to internet and try again.",Toast.LENGTH_SHORT).show();
        }
    }

    public void bookings()
    {
        final ProgressDialog progressDialog = ProgressDialog.show(Service.this,"Loading Services","Please Wait",true,true);
        JSONObject params = new JSONObject();
        try {
            params.put("email", loadData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serviceshow_url,params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try
                {
                    JSONArray detail=response.getJSONArray("info");

                    String[] servname,servdur;

                    if(detail.length()>0) {

                        servname = new String[detail.length()];
                        servdur = new String[detail.length()];

                        for (int i = 0; i < detail.length(); i++) {
                            JSONObject singledetail = detail.getJSONObject(i);
                            servname[i] = singledetail.getString("service");
                            servdur[i] = singledetail.getString("duration");

                        }
                    }
                    else {
                        servname = new String[1];
                        servdur = new String[1];

                        servname[0] = "No Service Added";
                        servdur[0] = "";
                    }

                    Single_Service adapter = new Single_Service(Service.this,servname,servdur);
                    listView.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    Toast.makeText(Service.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Service.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                progressDialog.dismiss();
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
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
        getMenuInflater().inflate(R.menu.service, menu);
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
            startActivity(new Intent(this,UpcomingFeatures.class));
            return true;
        }else if(id==R.id.logout){
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
            startActivity(new Intent(this,Booking_Shop.class));
        } else if (id == R.id.Services) {
            startActivity(new Intent(this,Service.class));
        } else if (id == R.id.myshop) {
            startActivity(new Intent(this,MyShop.class));
        } else if (id == R.id.myemployeeshop) {
            startActivity(new Intent(this,Employee_Shop.class));
        } else if (id == R.id.shopdetail) {
            startActivity(new Intent(this,Detail_Shop.class));
        } else if (id == R.id.aboutus) {
            startActivity(new Intent(this,About_Us.class));
        } else if (id == R.id.contactus) {
            startActivity(new Intent(this,ContactUs.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
