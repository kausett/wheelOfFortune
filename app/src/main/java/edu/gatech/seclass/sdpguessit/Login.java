package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.User;

public class Login extends AppCompatActivity {

    private EditText login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ConfigureGoHomeFromLoginButton();
        ConfigureLoginToApplicationButton();
    }

    private void ConfigureLoginToApplicationButton() {
        Button loginButton = (Button) findViewById(R.id.btnLogin);
        login = (EditText) findViewById(R.id.userNameField);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add Function that checks user info and authenticate them
                AuthenticateUser();
            }
        });
    }

    //TODO: Add user Authentication
    private void AuthenticateUser() {
        final String loginName = login.getText().toString();
        final AppDataBase database = AppDataBase.getAppDatabase(this);
        ExecutorService service = Executors.newCachedThreadPool();
        Future<User> future = service.submit(new Callable<User>() {

            @Override
            public User call() throws Exception {

                User user = database.userDao().login(loginName);
                //database.userDao().register(user);

                return user;
            }

        });
        service.shutdown();
        try {
            User user = future.get();
            if (user == null) {
                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
            } else {
                ConfigureBtnToMainMenuAction();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void ConfigureGoHomeFromLoginButton() {
        Button goHomeBtn = (Button) findViewById(R.id.backHomeButton);
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ConfigureBtnToMainMenuAction() {
        Button btn = (Button) findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainMenuActivity.class);
                intent.putExtra("USER_NAME", login.getText().toString());
                startActivity(intent);
            }
        });

    }
}
