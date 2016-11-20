package com.example.warning.zomato;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
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

import org.json.JSONException;
import org.json.JSONObject;


import com.example.warning.zomato.Adater.ListAdapter;
import com.example.warning.zomato.Controller.Controller;
import com.example.warning.zomato.Model.GetData;
import com.example.warning.zomato.Utils.LatLong;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{


    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static int MY_PERMISSION_ACCESS_COURSE_LOCATION;
    protected LocationManager locationManager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    LatLong latLong=new LatLong();


    ArrayList<GetData> data = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ListAdapter listAdapter;
    ListView list_item;
        String lat="28.5280300",lon="77.2569200";
    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
//        if (mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
//            mGoogleApiClient.disconnect();
//        }


    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderDisabled(String provider) {
    }
    @Override
    public void onConnected(Bundle bundle) {
        //int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MainActivity.MY_PERMISSION_ACCESS_COURSE_LOCATION);


        }


        if (MY_PERMISSION_ACCESS_COURSE_LOCATION == PackageManager.PERMISSION_GRANTED) {

            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);


                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("Enable your GPS services on phone");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            turnGPSOn();
                            //Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();

                }


            } else {
                //If everything went fine lets get latitude and longitude
//                lat.setText(String.valueOf(location.getLatitude()));
//                longi.setText(String.valueOf(location.getLongitude()));
                latLong.setLat(String.valueOf(location.getLatitude()));
//                lat=String.valueOf(location.getLatitude());
//                lon=String.valueOf(location.getLongitude());
                latLong.setLongi(String.valueOf(location.getLongitude()));

                Toast.makeText(this,latLong.getLat()+"  "+latLong.getLongi(),Toast.LENGTH_LONG).show();
//                Appcontroller.setMobileLat(String.valueOf(location.getLatitude()));
//                Appcontroller.setMobileLong(String.valueOf(location.getLongitude()));
                Log.d("Success else", "Location");
//                Toast.makeText(this, "Location sent successfully", Toast.LENGTH_LONG).show();
//                Toast.makeText(this, "Latitude :" + Appcontroller.getMobileLat() + "Longitude : " + Appcontroller.getMobileLong() + "", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("Error", "Location");
//            Toast.makeText(this, "Some Issues ", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
    /**
     * If locationChanges change lat and long
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

//        lat.setText(String.valueOf(location.getLatitude()));
//        longi.setText(String.valueOf(location.getLongitude()));
        latLong.setLat(String.valueOf(location.getLatitude()));
        latLong.setLongi(String.valueOf(location.getLongitude()));


    }


    private void turnGPSOn() {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);


        list_item = (ListView) findViewById(R.id.list_item);
        listAdapter = new ListAdapter(this,data);
        list_item.setAdapter(listAdapter);

        // Showing progress dialog before making http request



        getZomatodata();

          }

    public void getZomatodata()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        lat=latLong.getLat();
        lon=latLong.getLongi();
//        Log.d("latitute",latLong.getLat()+"  "+latLong.getLongi() );
//        System.out.println(latLong.getLat()+"   "+ latLong.getLongi());




        final String baseUrl = "https://developers.zomato.com/api/v2.1/search?lat="+lat+"&lon="+lon;


        Log.d("latitute",lat+"  "+lon );

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, baseUrl, null, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject jsonObject) {

                Log.v("Job Status 1111", String.valueOf(jsonObject));
                Log.d("Response", jsonObject.toString());

                progressDialog.dismiss();
                try {
                    Log.d("latitute in the funtion",latLong.getLat()+"  "+latLong.getLongi() );
                    for (int i = 0; i < jsonObject.getJSONArray("restaurants").length(); i++) {
                        JSONObject job = jsonObject.getJSONArray("restaurants").getJSONObject(i);
                        GetData getData = new GetData();
                        JSONObject object = job.getJSONObject("restaurant");
                        getData.setName(object.getString("name"));
                        getData.setImage(object.getString("featured_image"));
                        getData.setCuisines(object.getString("cuisines"));
                        JSONObject object1 = object.getJSONObject("user_rating");
                        getData.setRating(object1.getString("aggregate_rating"));
//                        getData.setUrl(object.getString("url"));
                        data.add(getData);


                    }
//                    Log.d("values are:", data.get(1).getRating());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("something not found", "");
                }

                listAdapter.notifyDataSetChanged();
            }

        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.v("campaign error", "");

            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", "08ab197217bfe4d876a3e988d69a6d34");
                params.put("Accept", "application/json");

                return params;
            }
        };


// invoke the server to retrieve data
        Controller.getInstance().addToRequestQueue(request);









    }




    }



