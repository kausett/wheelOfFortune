package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.User;
import edu.gatech.seclass.sdpguessit.utils.DatabaseInitializer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set Up Sign in button
        ConfigureSignInButton();

        //Setup SignUpButton();
        ConfigureSignUpButton();
    }

    //Programmitically Key up the SignInButton to the Sign in controller
    //TODO: Test UI
    //@Test: This class should redirect to Login View.
    private void ConfigureSignInButton(){
        Button signInButton = (Button) findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }


    //Programmitically Key up the SignInButton to the Sign in controller
    //TODO: Test UI
    //@Test: This class should redirect to SignUp View.
    private void ConfigureSignUpButton(){
        Button signInButton = (Button) findViewById(R.id.SignUpButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Create a sign up class and move from there.
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

      final AppDataBase database = AppDataBase.getAppDatabase(this);
        Thread t = new Thread() {
            public void run() {
                DatabaseInitializer.with(database).generateuser();
                User user[]=database.userDao().loadAll();
            }
        };
        t.start();
    }
}
