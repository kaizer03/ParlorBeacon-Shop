package com.springboardtechsolutions.parlorbeaconshop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Detail_Shop extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueue;
    String show_url = "http://parlorbeacon.com/androidapp/shopkeeper/show.php";
    String pic_url = "http://parlorbeacon.com/androidapp/shopkeeper/uploads/";

    private static final int PICK_IMAGE_ID1 = 0;
    private static final int PICK_IMAGE_ID2 = 1;
    @Bind(R.id.profile_shop)
    ImageView profilepic;
    @Bind(R.id.front_shop)
    ImageView frontpic;

    @Bind(R.id.detail_shop_shopkeepername)
    TextView shopkeepernametext;
    @Bind(R.id.detail_shop_shopname)
    TextView shopnametext;
    @Bind(R.id.detail_shop_email)
    TextView emailtext;
    @Bind(R.id.detail_shop_shopphone)
    TextView shopphonetext;
    @Bind(R.id.detail_shop_shopaddress)
    TextView shopaddrtext;
    @Bind(R.id.detail_shop_shopcity)
    TextView shopcitytext;
    @Bind(R.id.detail_shop_shopzip)
    TextView shopziptext;
    @Bind(R.id.detail_shop_shopopen)
    TextView shopopentext;
    @Bind(R.id.detail_shop_shopclose)
    TextView shopclosetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //noinspection deprecation
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView textView = (TextView)headerView.findViewById(R.id.navdetailshoptext);
        textView.setText("Hello "+loadData2());

        requestQueue = Volley.newRequestQueue(this);

        ButterKnife.bind(this);
        if (isNetworkAvailable()) {
            setDetail();
        }
        else {
            Toast.makeText(Detail_Shop.this,"Internet not connected. Please connect to internet and try again.",Toast.LENGTH_SHORT).show();
        }
    }

    public void setDetail()
    {
        final String email = loadData();
        emailtext.setText(email);

        JSONObject params = new JSONObject();
        try {
            params.put("email", loadData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, show_url,params, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray detail=response.getJSONArray("info");
                    for(int i=0;i<detail.length();i++) {
                        JSONObject singledetail = detail.getJSONObject(i);
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
                catch (JSONException e)
                {
                    Toast.makeText(Detail_Shop.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_Shop.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        Picasso.with(Detail_Shop.this).load(pic_url+loadData()+"/profilepic.png").skipMemoryCache().error(R.drawable.npi).into(profilepic);
        Picasso.with(Detail_Shop.this).load(pic_url+loadData()+"/frontpic.png").skipMemoryCache().error(R.drawable.nfi).into(frontpic);

    }

    @OnClick(R.id.edit_shopdetail_button)
    public void onClick(View view) {
        Intent intent = new Intent(Detail_Shop.this,Edit_Detail_Shop.class);
        intent.putExtra("Email",loadData());
        startActivity(intent);
        finish();
    }

    public void onClick4(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID1);
    }

    public void onClick5(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_ID1:
                Bitmap bitmap1 = ImagePicker.getImageFromResult(this, resultCode, data);
                profilepic.setImageBitmap(bitmap1);
                uploadImage(bitmap1,0);
                break;
            case PICK_IMAGE_ID2:
                Bitmap bitmap2 = ImagePicker.getImageFromResult(this, resultCode, data);
                frontpic.setImageBitmap(bitmap2);
                uploadImage(bitmap2,1);
                break;
            default: super.onActivityResult(requestCode, resultCode, data);
                break;
        }
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
        getMenuInflater().inflate(R.menu.detail__shop, menu);
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
            startActivity(new Intent(Detail_Shop.this,UpcomingFeatures.class));
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

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadImage(final Bitmap bitmap, int val){

        if(bitmap!=null) {
            //Showing the progress dialog
            final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
            String UPLOAD_URL;
            if(val==0) {
                UPLOAD_URL = "http://parlorbeacon.com/androidapp/shopkeeper/uploads/uploadprofilepic.php";
            }
            else {
                UPLOAD_URL = "http://parlorbeacon.com/androidapp/shopkeeper/uploads/uploadfrontpic.php";
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Disimissing the progress dialog
                            loading.dismiss();
                            //Showing toast message of the response
                            if(s.equalsIgnoreCase("success")) {
                                Toast.makeText(Detail_Shop.this, "Pic Uploaded" , Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //Dismissing the progress dialog
                    loading.dismiss();

                    //Showing toast
                    Toast.makeText(Detail_Shop.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    String body;
                    //get response body and parse with appropriate encoding
                    if(volleyError.networkResponse.data!=null) {
                        try {
                            body = new String(volleyError.networkResponse.data,"UTF-8");
                            Toast.makeText(Detail_Shop.this,body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Toast.makeText(Detail_Shop.this,e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Converting Bitmap to String
                     String image = getStringImage(bitmap);

                        //Creating parameters
                        Map<String,String> params = new Hashtable<>();

                        //Adding parameters
                        params.put("image", image);
                        params.put("email", loadData());

                        //returning parameters
                        return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //Adding request to the queue
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(Detail_Shop.this,"Operation Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try
        {
            trimCache(getApplicationContext());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        assert dir != null;
        return dir.delete();
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
