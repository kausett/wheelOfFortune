package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Creates startup screen and redirects us to Main Activity after its done with splash screen
        final Intent I = new Intent(SplashActivity.this, MainActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(I);
                    finish();
                }
            }
        };
        timer.start();
    }
}
