package com.apps.android.prasannsinghal.pictomapper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.apps.android.prasannsinghal.pictomapper.Models.Helper;
import com.apps.android.prasannsinghal.pictomapper.Models.Monument;
import com.apps.android.prasannsinghal.pictomapper.Models.Play;
import com.apps.android.prasannsinghal.pictomapper.Models.Score;
import com.apps.android.prasannsinghal.pictomapper.Models.User;
import com.apps.android.prasannsinghal.pictomapper.Utilities.Scoring;
import com.apps.android.prasannsinghal.pictomapper.persistence.PictomapperDAO;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.relex.photodraweeview.PhotoDraweeView;

import static com.apps.android.prasannsinghal.pictomapper.MONUMENTS.ALL_MONUMENTS;
import static com.apps.android.prasannsinghal.pictomapper.Models.Helper.ALL_MONUMENTS_DB;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    public int score = 0;
    private ImageView mapclear;
    private ImageView hintbutton;
    private ImageView next;
    private ImageView left;
    private ImageView before;
    private TextView instruct;
    private boolean hintactivate1;
    private boolean hintactivate2;
    private int hintclicks = 0;
    private PictomapperDAO datasource=null;
    private Play p;
    private boolean guessed = false;
    private Menu mainMenu;

    //private com.apps.android.prasannsinghal.pictomapper.TouchImageView  monumentImage;
    //private SupportMapFragment mapFrag;

    public void saveInDatabase(){
        Monument[] monuments = Monument.fromCSV(ALL_MONUMENTS);
        for(Monument m:monuments){
            try{
                this.datasource.addMonument(m);
            }
            catch (Exception ex){
                Log.e("saveInDatabase",ex.getMessage());
            }

        }
    }

    public ArrayList<Monument> readAllMonuments(){
       return datasource.getAllMonuments();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.datasource.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Fresco.initialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /*monumentImage = (com.apps.android.prasannsinghal.pictomapper.TouchImageView)findViewById(R.id.imag);
        monumentImage.setScaleType(ImageView.ScaleType.FIT_XY);*/

        instruct = (TextView) findViewById(R.id.instruct);
        instruct.setText("Guess the location..");

        datasource = new PictomapperDAO(this.getApplicationContext());

        datasource.open();

        //this.saveInDatabase();

        /*for(Monument m: this.readAllMonuments()){
            Log.d("MonumentsDebugID",getColumnSize("MONUMENTS_TABLE_NAME")+"");
        }*/
        ALL_MONUMENTS_DB = this.readAllMonuments();
        /*datasource.addPlay(new Play(new Monument()));
        datasource.addScore(new Score());*/


        onNewPlay();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        score = datasource.getScore();


    }

    public void onNewPlay(){
        /*if(datasource.getCurrentPlay()!=null) {
            p = new Play(ALL_MONUMENTS_DB.get(new Random().nextInt(ALL_MONUMENTS_DB.size() - 1)), datasource.getColCount("ALL_MONUMENTS_NAME"));
            direction = true;
        }
        else   {
            p = datasource.getCurrentPlay();
            direction = false;
        }*/

        try {
            p = new Play(ALL_MONUMENTS_DB.get(getMonInd()));


        // Load image
        /*Picasso.with(this)
                .load(p.getMonumentImageURL())
                .into(monumentImage);*/
            PhotoDraweeView draweeView = (PhotoDraweeView) findViewById(R.id.image);
        draweeView.setPhotoUri(Uri.parse(p.getMonumentImageURL()));
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guessed){
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });


        // Clear map
        mapclear = (ImageView) findViewById(R.id.clearbutton);
        mapclear.performClick();
        }
        catch (Exception ex){
            Toast.makeText(this.getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG);
        }

    }

    /**

     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(final GoogleMap map) {

        //if(direction){


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

                                p.setGuess(point);
                                datasource.addPlay(p);

                                guessed = true;

                                //String msg = "Here's some info: \n"+ALL_MON_MODELS[currentIndex].detailedDescription;
                                Marker monmark = map.addMarker(new MarkerOptions().position(new LatLng(getlat(), getlng())).title(p.getMonumentName()).snippet(p.getMonumentDesc()));
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
                                int padding = 45; // offset from edges of the map in pixels
                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                map.animateCamera(cu);


                                //Toast.makeText(getApplicationContext(),msg,5*Toast.LENGTH_LONG).show();

                                instruct.setText(""+(int)Scoring.getDistanceKm(p.getUserGuessLatLng(),p.getMonumentLatLng())+" Kms");
                                score += new Score(p.ID,getUser().ID,p.ID,(int)Scoring.getDistanceKm(p.getUserGuessLatLng(),p.getMonumentLatLng())).score;
                                MenuItem pinMenuItem = mainMenu.findItem(R.id.action_score);
                                try{
                                    pinMenuItem.setTitle(score+"");
                                }
                                catch (Exception ex){
                                    Log.d("hello",ex.getMessage());

                                }
                                datasource.addScore(new Score(p.ID,getUser().ID,p.ID,(int)Scoring.getDistanceKm(p.getUserGuessLatLng(),p.getMonumentLatLng())));


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
        /*}
        else{

            LatLng point = new LatLng(p.userGuessLatLng.latitude,p.userGuessLatLng.longitude);
            Marker monmark = map.addMarker(new MarkerOptions().position(new LatLng(getlat(), getlng())).title(p.getMonumentName()).snippet(p.getMonumentDesc()));
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
            int padding = 45; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map.animateCamera(cu);


            //Toast.makeText(getApplicationContext(),msg,5*Toast.LENGTH_LONG).show();

            instruct.setText(""+(int)Scoring.getDistanceKm(p.getUserGuessLatLng(),p.getMonumentLatLng())+" Kms");



        }*/

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
        return p.getMonumentLatLng().latitude;

    }
    public double getlng(){

        return p.getMonumentLatLng().longitude;
    }

    public LinearLayout rellayout(){
        LinearLayout rel = findViewById(R.id.mainlay);
        return rel;
    }


    public void onHint(){
        switch(hintclicks){
            case 0:{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setCancelable(true);
                builder1.setTitle("HINT "+(hintclicks+1));
                builder1.setMessage("The name of this place is "+p.getMonumentName());
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

    public User getUser(){
        return new User("10","Prasann Singhal",5,14);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainactivitytoolbar, menu);
        mainMenu = menu;
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

            case R.id.action_hint:
                hintbutton.performClick();

            case R.id.action_reset:
                mapclear.performClick();


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public int getMonInd(){
        return new Random().nextInt(ALL_MONUMENTS_DB.size());
    }

    public void updatescore(){

    }


}
