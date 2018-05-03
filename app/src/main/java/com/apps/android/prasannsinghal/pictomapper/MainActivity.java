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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.google.android.gms.maps.CameraUpdateFactory;
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
import android.support.v7.widget.Toolbar;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView mapclear;
    private ImageView hintbutton;
    //public HomeActivity home = new HomeActivity();
    //public HomeActivity.GetWikiURLsAsync urla = home.new GetWikiURLsAsync();
    //int turnnumbergen = (int)(Math.random()*urla.MonumentModels.size());
    //int turnumber = turnnumbergen;
    int score;



    MonumentModel[] ALL_MON_MODELS;
    int currentIndex = 0;
    int hintclicks = 0;
    public boolean hintactivate1 = false;
    public boolean hintactivate2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ALL_MON_MODELS = MonumentModel.fromCSV(MONUMENTS.ALL_MONUMENTS);
        onPlayBegin();

        SupportMapFragment mapFragment =

                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        com.apps.android.prasannsinghal.pictomapper.TouchImageView  imageView = (com.apps.android.prasannsinghal.pictomapper.TouchImageView)findViewById(R.id.imag);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(this)
                .load(ALL_MON_MODELS[currentIndex].imageURL)
                .into(imageView);



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
                        builder1.setMessage("Are you sure that this is your final guess?");
                        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(getlat(), getlat())).title("Hello Maps").icon(BitmapDescriptorFactory.fromResource(R.drawable.monu));
                                map.addMarker(marker);
                                Polyline line = map.addPolyline(new PolylineOptions()
                                        .add(new LatLng(getlat(), getlng()), new LatLng(point.latitude,point.longitude))
                                        .width(3)
                                        .color(Color.BLACK));

                                List<PatternItem> pattern = Arrays.<PatternItem>asList(
                                        new Gap(5), new Dash(10), new Gap(5));
                                line.setPattern(pattern);
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng((point.latitude+getlat())/2, (point.longitude+getlng())/2),2));
                                score +=(20010-Distance(new LatLng(getlat(),getlng()),point));
                                rellayout().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                        builder1.setCancelable(true);
                                        builder1.setTitle("Good Guess");
                                        builder1.setMessage("You were "+Distance(new LatLng(getlat(),getlng()),point)+" km away!\nHere's some info: \n"+ALL_MON_MODELS[currentIndex].detailedDescription);
                                        builder1.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                        builder1.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = getIntent();
                                                finish();
                                                startActivity(intent);
                                            }
                                        });
                                        builder1.show();
                                    }
                                });


                            }
                        });
                        builder1.show();

                    }
                });
                builder.show();

                map.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromResource(R.drawable.images)));
                float[] results = new float[1];
                Location.distanceBetween(point.latitude, point.longitude,
                        getlat(), getlng(), results);

            }
        });

        mapclear = (ImageView) findViewById(R.id.clearbutton);
        mapclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0),0));
            }
        });

        hintbutton = (ImageView) findViewById(R.id.button2);
        hintbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHint();
                if(hintactivate1==true){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getlat(), getlng()),5));
                }
                if(hintactivate2==true){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getlat(), getlng()),10));
                }
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

    public LinearLayout rellayout(){
        LinearLayout rel = findViewById(R.id.mainlay);
        return rel;
    }

    public void onPlayBegin(){
        Random r = new Random();
        currentIndex = r.nextInt(ALL_MON_MODELS.length);
    }

    public void onHint(){
        switch(hintclicks){
            case 0:{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setCancelable(true);
                builder1.setTitle("HINT "+(hintclicks+1));
                builder1.setMessage("The name of this place is "+ALL_MON_MODELS[currentIndex].name);
                builder1.setNegativeButton("BACK TO QUESTION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder1.show();
                break;
            }
            case 1:{
                hintactivate1 = true;
                break;
            }
            case 2:{
                hintactivate2 = true;
                break;
            }
            default:{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setCancelable(true);
                builder1.setTitle("SORRY ");
                builder1.setMessage("YOU have used up all your hints");
                builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder1.show();
                break;
            }
        }
        hintclicks++;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainactivitytoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                openSettingsActivity();
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
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
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
