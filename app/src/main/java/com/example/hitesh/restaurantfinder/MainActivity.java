package com.example.hitesh.restaurantfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.setContentView(R.layout.activity_main);
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(MainActivity.this,
                            ListActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();

    }
}
