package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.Tournament;
import edu.gatech.seclass.sdpguessit.entity.TournamentPuzzle;
import edu.gatech.seclass.sdpguessit.entity.User;
import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;
import edu.gatech.seclass.sdpguessit.entity.UserTournament;


public class Register extends AppCompatActivity {
    EditText firstName,lastName,username,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ConfigureGoHomeFromRegisterButton();
        ConfigureRegisterButton();
    }

    private void ConfigureRegisterButton() {
        Button createUser = (Button) findViewById(R.id.btnRegister);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewUserAction();
            }
        });
    }


    private void CreateNewUserAction() {
        final AppDataBase database = AppDataBase.getAppDatabase(this);
        Thread t = new Thread() {
            public void run() {
                Boolean userNameTaken=false;
                firstName=(EditText) findViewById(R.id.txtFirstName);
                lastName=(EditText) findViewById(R.id.txtLastName);
                username=(EditText) findViewById(R.id.txtPlayPuzzleUsername);
                email=(EditText) findViewById(R.id.txtEmail);
               String emailpattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(emailpattern);
                Matcher matcher=pattern.matcher(email.getText().toString());
                if(!matcher.matches()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            email.setError("Wrong Email");

                        }
                    });
                }
                else if (username.getText().toString().trim().isEmpty()){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            username.setError("User Name can not be empty");

                        }
                    });
                }else {
                    final User user = new User(username.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
                    User[] users = database.userDao().loadAll();
                    for (User user1 : users) {
                        if (username.getText().toString().equalsIgnoreCase(user1.getUserName())) {
                            userNameTaken = true;
                        }
                    }
                    if (!userNameTaken) {
                        database.userDao().register(user);
                        ConfigureBtnGoToMainMenu();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                username.setError("User Name Taken");

                            }
                        });


                    }
                }
            }
        };
        t.start();

    }

    private void ConfigureGoHomeFromRegisterButton() {
        Button backButton = (Button) findViewById(R.id.btnBackHome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ConfigureBtnGoToMainMenu(){
        Button btn = (Button) findViewById(R.id.btnRegister);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this, MainMenuActivity.class);
                intent.putExtra("USER_NAME", username.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
