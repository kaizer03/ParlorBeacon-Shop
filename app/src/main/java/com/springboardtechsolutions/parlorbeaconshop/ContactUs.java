package com.springboardtechsolutions.parlorbeaconshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ContactUs extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
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
        TextView textView = (TextView)headerView.findViewById(R.id.navcontactshoptext);
        textView.setText("Hello "+loadData2());
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
        getMenuInflater().inflate(R.menu.contact_us, menu);
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
            startActivity(new Intent(ContactUs.this,UpcomingFeatures.class));
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
            finish();
        }

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
