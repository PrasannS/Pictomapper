package com.apps.android.prasannsinghal.pictomapper;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private Button playbutton;
    private Button shopbutton;
    private Button settingsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);

        playbutton = (Button)findViewById(R.id.playbutton);
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
            }
        });

        shopbutton = (Button)findViewById(R.id.shopbutton);
        shopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopActivity();
            }
        });

        settingsbutton = (Button)findViewById(R.id.settingsbutton);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingActivity();
            }
        });
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openShopActivity(){
        Intent intent1 = new Intent(this,ShopActivity.class);
        startActivity(intent1);
    }
    public void openSettingActivity(){
        Intent intent1 = new Intent(this,SettingActivity.class);
        startActivity(intent1);
    }



}
