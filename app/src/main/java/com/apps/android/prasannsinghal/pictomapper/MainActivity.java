package com.apps.android.prasannsinghal.pictomapper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
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
import android.support.v4.app.FragmentManager;


import com.apps.android.prasannsinghal.pictomapper.Models.Helper;
import com.apps.android.prasannsinghal.pictomapper.Models.Play;
import com.apps.android.prasannsinghal.pictomapper.Models.Session;
import com.apps.android.prasannsinghal.pictomapper.Utilities.Scoring;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    private ImageView mapclear;
    private ImageView hintbutton;
    private ImageView next;
    private ImageView left;
    private ImageView before;
    private TextView instruct;
    private boolean hintactivate1;
    private boolean hintactivate2;
    private int hintclicks = 0;

    private com.apps.android.prasannsinghal.pictomapper.TouchImageView  monumentImage;
    private SupportMapFragment mapFrag;

    private Session userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        monumentImage = (com.apps.android.prasannsinghal.pictomapper.TouchImageView)findViewById(R.id.imag);
        monumentImage.setScaleType(ImageView.ScaleType.FIT_XY);



        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        userSession = new Session();

        instruct = (TextView) findViewById(R.id.instruct);
        instruct.setText("Guess the location..");

        next = (ImageView) findViewById(R.id.right);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSession.nextPlay();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        left = (ImageView) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSession.previousPlay();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });



        onNewPlay();
    }

    public void onNewPlay(){

        Play p = this.userSession.getCurrentPlay();

        // Load image
        Picasso.with(this)
                .load(p.getMonumentImageURL())
                .into(monumentImage);

        // Clear map
        mapclear = (ImageView) findViewById(R.id.clearbutton);
        mapclear.performClick();


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

                                userSession.getCurrentPlay().setGuess(point);

                                //String msg = "Here's some info: \n"+ALL_MON_MODELS[currentIndex].detailedDescription;
                                Marker monmark = map.addMarker(new MarkerOptions().position(new LatLng(getlat(), getlng())).title(userSession.getCurrentPlay().getMonumentName()).snippet(userSession.getCurrentPlay().getMonumentDesc()));
                                Marker guessmark = map.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                Polyline line = map.addPolyline(new PolylineOptions()
                                        .add(new LatLng(getlat(), getlng()), new LatLng(point.latitude,point.longitude))
                                        .width(3)
                                        .color(Color.BLACK));

                                List<PatternItem> pattern = Arrays.<PatternItem>asList(
                                        new Gap(5), new Dash(10), new Gap(5));
                                line.setPattern(pattern);
                                //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng((point.latitude+getlat())/2, (point.longitude+getlng())/2),1));
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                builder.include(monmark.getPosition());
                                builder.include(guessmark.getPosition());
                                LatLngBounds bounds = builder.build();
                                int padding = 25; // offset from edges of the map in pixels
                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                map.animateCamera(cu);


                                //Toast.makeText(getApplicationContext(),msg,5*Toast.LENGTH_LONG).show();

                                instruct.setText(""+(int)Scoring.getDistanceKm(userSession.getCurrentPlay().getUserGuessLatLng(),userSession.getCurrentPlay().getMonumentLatLng())+" Kms");


                            }
                        });
                        builder1.show();

                    }
                });
                builder.show();


            }
        });



        mapclear = (ImageView) findViewById(R.id.clearbutton);
        mapclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0),0));
                //map.clear();
            }
        });

        hintbutton = (ImageView) findViewById(R.id.button2);
        hintbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHint();
                if(hintactivate1){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(getlat(), getlng()),5));
                }
                if(hintactivate2){
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

    public double getlat(){
        return userSession.getCurrentPlay().getMonumentLatLng().latitude;

    }
    public double getlng(){

        return userSession.getCurrentPlay().getMonumentLatLng().longitude;
    }

    public LinearLayout rellayout(){
        LinearLayout rel = findViewById(R.id.mainlay);
        return rel;
    }

    public int distancetozoom(int d){
        int val = 0;
        if(d>6000)
            val=1;
        else{
            if (d>5000)
                val = 2;
            else{
                if(d>4000)
                    val = 3;
                else{
                    if(d>3000)
                        val = 5;
                    else{
                        if(d>2000)
                            val = 6;
                        else{
                            if(d>550)
                                val = 7;
                            else{
                                if(d>250)
                                    val = 8;
                                else{
                                    if(d>100)
                                        val = 9;
                                    else{
                                        if(d>0)
                                            val = 10;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return val;
    }

    public void onHint(){
        switch(hintclicks){
            case 0:{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setCancelable(true);
                builder1.setTitle("HINT "+(hintclicks+1));
                builder1.setMessage("The name of this place is "+userSession.getCurrentPlay().getMonumentName());
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
