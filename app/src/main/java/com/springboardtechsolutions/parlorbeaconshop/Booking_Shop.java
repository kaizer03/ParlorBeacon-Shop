package com.springboardtechsolutions.parlorbeaconshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class Booking_Shop extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueue;
    ListView listView;
    String booking_url = "http://parlorbeacon.com/androidapp/shopkeeper/getbooking.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView textView = (TextView)headerView.findViewById(R.id.navbookshoptext);
        textView.setText("Hello "+loadData2());

        listView = (ListView)findViewById(R.id.ListViewBookings);

        requestQueue = Volley.newRequestQueue(Booking_Shop.this);
        if (NoInternetToast.isNetworkAvailable(Booking_Shop.this)) {
            bookings();
        }
        else {
            NoInternetToast.nointernettoast(this);
        }
    }

    public void bookings()
    {
        JSONObject params = new JSONObject();
        try {
            params.put("email", loadData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, booking_url,params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray detail=response.getJSONArray("booking");

                    String[] custname,custemail,custservice,custservtime,custservdate,custservstatus;

                    if(detail.length()>0) {

                        custname = new String[detail.length()];
                        custemail = new String[detail.length()];
                        custservice = new String[detail.length()];
                        custservtime = new String[detail.length()];
                        custservdate = new String[detail.length()];
                        custservstatus = new String[detail.length()];

                        for (int i = 0; i < detail.length(); i++) {
                            JSONObject singledetail = detail.getJSONObject(i);
                            custname[i] = singledetail.getString("custname");
                            custemail[i] = singledetail.getString("custemail");
                            custservice[i] = singledetail.getString("service");
                            custservtime[i] = singledetail.getString("time");
                            custservdate[i] = singledetail.getString("date");
                            custservstatus[i] = singledetail.getString("status");

                        }
                    }
                    else {
                        custname = new String[1];
                        custemail = new String[1];
                        custservice = new String[1];
                        custservtime = new String[1];
                        custservdate = new String[1];
                        custservstatus = new String[1];

                        custname[0] = "No Bookings for Today";
                        custemail[0] = "";
                        custservice[0] = "";
                        custservtime[0] = "";
                        custservdate[0] = "";
                        custservstatus[0] = "";
                    }

                    Booking_List adapter = new Booking_List(Booking_Shop.this,custname,custemail,custservice,custservtime,custservstatus,custservdate);
                    listView.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    Toast.makeText(Booking_Shop.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Booking_Shop.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.booking__shop, menu);
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
            startActivity(new Intent(Booking_Shop.this,UpcomingFeatures.class));
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
