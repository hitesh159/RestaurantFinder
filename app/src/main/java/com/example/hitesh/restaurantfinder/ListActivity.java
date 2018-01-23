package com.example.hitesh.restaurantfinder;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private ListAdapter adapter;
    private List<ListModel> list;
    ArrayList<String> resId;
    LocationManager locationManager;
    LocationListener locationListener;
    public void updateLocationInfo(Location location){
        GetContent content=new GetContent(ListActivity.this);
        content.execute("https://developers.zomato.com/api/v2.1/search?count=10&lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&radius=50&sort=real_distance");

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,50000,500,locationListener);
            }
        }
    }
    public class GetContent extends AsyncTask<String,Void,String>{

        private ProgressDialog dialog;
        public GetContent(ListActivity activity){
            dialog=new ProgressDialog(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            URL url=null;
            HttpURLConnection connection=null;
            String result="";
            try {
                url=new URL(strings[0]);

                connection=(HttpURLConnection)url.openConnection();
                //System.out.println("code:"+connection.getResponseCode());
                connection.setRequestProperty("user-key","efdf4320ab8775f2bd0092ee74bc2292");
                InputStream inputStream=connection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                while (data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                Log.i("info",result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            try {
                JSONObject results=new JSONObject(s);
                String restaurants=results.getString("restaurants");
                JSONArray array=new JSONArray(restaurants);
                list.clear();
                for (int i=0;i<array.length();i++)
                {
                    JSONObject restaurant=new JSONObject(array.getJSONObject(i).getString("restaurant"));
                    JSONObject location=new JSONObject(restaurant.getString("location"));
                    list.add(new ListModel(location.getString("address"),location.getString("locality"),restaurant.getString("name"),i));
                    resId.add(restaurant.getString("id"));
                    Log.i("name of restaurant",restaurant.getString("name"));
                }
                adapter.notifyDataSetChanged();
                Log.i("res",restaurants);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        resId=new ArrayList<>();
        listView=(ListView)findViewById(R.id.list);
        list=new ArrayList<>();
        adapter=new ListAdapter(getApplicationContext(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), Integer.toString(i),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),RestaurantDetails.class);
                intent.putExtra("resId",resId.get(i));
                startActivity(intent);
            }
        });
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("LocationInfo",location.toString());
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(getApplicationContext(),"hehe",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,50000,500,locationListener);
            Toast.makeText(getApplicationContext(),"hmm",Toast.LENGTH_SHORT).show();
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location!=null){
                updateLocationInfo(location);
            }
            else{
                Toast.makeText(getApplicationContext(),"ouu",Toast.LENGTH_SHORT).show();
            }
        }
//        GetContent content=new GetContent(ListActivity.this);
//        content.execute("https://developers.zomato.com/api/v2.1/search?count=10&lat=28.594858&lon=77.0171008&radius=50&sort=real_distance");

    }
}
