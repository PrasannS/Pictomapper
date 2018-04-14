package com.apps.android.prasannsinghal.pictomapper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {



    private Button button;
    private Button quitbutton;
    private Button settingsbutton;
    private Button storebutton1;
    private Button storebutton2;
    private Button mapclear;
    //public HomeActivity home = new HomeActivity();
    //public HomeActivity.GetWikiURLsAsync urla = home.new GetWikiURLsAsync();
    //int turnnumbergen = (int)(Math.random()*urla.MonumentModels.size());
    //int turnumber = turnnumbergen;
    int score;


    MonumentModel[] ALL_MON_MODELS;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ALL_MON_MODELS = MonumentModel.fromCSV(MONUMENTS.ALL_MONUMENTS);
        onPlayBegin();

        SupportMapFragment mapFragment =

                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        ImageView imageView = (ImageView)findViewById(R.id.imag);
        Picasso.with(this)
                .load(ALL_MON_MODELS[currentIndex].imageURL)
                .into(imageView);

        button = (Button)findViewById(R.id.homebuttonmain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
            }
        });


        storebutton1 = (Button)findViewById(R.id.scorebutton1);
        storebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStoreActivity();
            }
        });

        storebutton2 = (Button)findViewById(R.id.storebutton2);
        storebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStoreActivity();
            }
        });

        settingsbutton = (Button)findViewById(R.id.settingsbutton);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsActivity();
            }
        });

        quitbutton = (Button)findViewById(R.id.quitbutton);
        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Are you sure?");
                builder.setMessage("If you quit then your progress will be recorded and you will start fresh next time");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openHomeActivity();

                    }
                });
                builder.show();
            }
        });



    }

    /**

     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(final GoogleMap map) {


        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng point) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Is this your guess?");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        map.clear();
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setCancelable(true);
                        builder1.setTitle("What do you want to do?");
                        builder1.setMessage("You can make another guess or finish, but beware, making an additional guess will average the results of your other guesses");
                        builder1.setNegativeButton("Make another guess", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder1.setPositiveButton("Finish question", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                map.addMarker(new MarkerOptions().position(new LatLng(getlat(), getlng())).title("Marker"));
                                Polyline line = map.addPolyline(new PolylineOptions()
                                        .add(new LatLng(getlat(), getlng()), new LatLng(point.latitude,point.longitude))
                                        .width(3)
                                        .color(Color.BLACK));

                                List<PatternItem> pattern = Arrays.<PatternItem>asList(
                                        new Gap(5), new Dash(10), new Gap(5));
                                line.setPattern(pattern);
                                score +=(20010-Distance(new LatLng(getlat(),getlng()),point));
                                storebutton1.setText("SCORE: "+score);
                            }
                        });
                        builder1.show();

                    }
                });
                builder.show();

                map.addMarker(new MarkerOptions().position(point));
                float[] results = new float[1];
                Location.distanceBetween(point.latitude, point.longitude,
                        getlat(), getlng(), results);

            }
        });

        mapclear = (Button)findViewById(R.id.clearbutton);
        mapclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
            }
        });

    }

    public void openHomeActivity(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void openSettingsActivity(){
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }
    public void openStoreActivity(){
        Intent intent = new Intent(this,ShopActivity.class);
        startActivity(intent);
    }
    /*public double getlat(){
        return urla.MonumentModels.get(turnumber).lat;

    }
    public double getlng(){
        return urla.MonumentModels.get(turnumber).lng;
    }*/

    public double getlat(){
        return ALL_MON_MODELS[currentIndex].lat;

    }
    public double getlng(){

        return ALL_MON_MODELS[currentIndex].lng;
    }

    public void onPlayBegin(){
        Random r = new Random();
        currentIndex = r.nextInt(ALL_MON_MODELS.length);
    }
    public double Distance(LatLng StartP, LatLng EndP) {
        int Radius=6371;//radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
    }

}
