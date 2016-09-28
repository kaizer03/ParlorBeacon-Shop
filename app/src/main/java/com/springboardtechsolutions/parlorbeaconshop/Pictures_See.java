package com.springboardtechsolutions.parlorbeaconshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Pictures_See extends AppCompatActivity {

    RequestQueue requestQueue;
    String picture_url = "http://parlorbeacon.com/androidapp/shopkeeper/getnophotos.php";
    String pic_upload_url = "http://parlorbeacon.com/androidapp/shopkeeper/uploads/pic_upload.php";

    GridView images;

    private static final int PICK_IMAGE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures__see);

        Button fab = (Button) findViewById(R.id.fab123);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(Pictures_See.this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });


        requestQueue = Volley.newRequestQueue(Pictures_See.this);
        if (isNetworkAvailable()) {
            seeapplic();
        }
        else {
            Toast.makeText(Pictures_See.this,"Internet not connected. Please connect to internet and try again.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap1 = ImagePicker.getImageFromResult(this, resultCode, data);
                uploadImage(bitmap1);
                break;
            default: super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadImage(final Bitmap bitmap){

        if(bitmap!=null) {
            //Showing the progress dialog
            final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST,pic_upload_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            loading.dismiss();
                            if(s.equalsIgnoreCase("success")) {
                                Toast.makeText(Pictures_See.this, "Pic Uploaded" , Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    loading.dismiss();
                    Toast.makeText(Pictures_See.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    String body;
                    //get response body and parse with appropriate encoding
                    if(volleyError.networkResponse.data!=null) {
                        try {
                            body = new String(volleyError.networkResponse.data,"UTF-8");
                            Toast.makeText(Pictures_See.this,body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Toast.makeText(Pictures_See.this,e.toString(), Toast.LENGTH_SHORT).show();
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

            requestQueue = Volley.newRequestQueue(this);

            //Adding request to the queue
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(Pictures_See.this,"Operation Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public void seeapplic()
    {

        images = (GridView)findViewById(R.id.gridViewimages);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, picture_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("Failed")  || response.equals("0")) {
                        Toast.makeText(Pictures_See.this, "Sorry No Pics Found", Toast.LENGTH_SHORT).show();
                    } else {

                        int number = Integer.parseInt(response);
                        String[] pic_urls = new String[number];
                        int i;
                        for(i=0;i<number;i++) {
                            pic_urls[i] = "http://parlorbeacon.com/androidapp/shopkeeper/uploads/" + loadData() + "/" + String.valueOf(i) + ".png";
                        }
                        SinglePicture adapter = new SinglePicture(Pictures_See.this,pic_urls);
                        images.setAdapter(adapter);

                    }
                } catch (Exception e) {
                    Toast.makeText(Pictures_See.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pictures_See.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("email", loadData());

                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)getApplication().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    protected String loadData() {
        String FILENAME = "emailshop.txt";
        String out = "";

        try {
            FileInputStream fis1 = Pictures_See.this.openFileInput(FILENAME);
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
