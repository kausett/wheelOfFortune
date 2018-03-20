package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    private String getUsername() {
        return getIntent().getStringExtra("USER_NAME");
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ConfigureQuitBtnReturnBackToLogin();
        ConfigureCreatePuzzleButton();
        ConfigureCreateTournamentButton();
        ConfigurePlayTournamentButton();
        ConfigureContinueTournamentButton();
        ConfigureViewStatsButton();
        ConfigurePlayRandomPuzzleButton();

    }

    private void ConfigureCreatePuzzleButton() {
        Button btn = (Button) findViewById(R.id.btnCreatePuzzle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, CreatePuzzle.class);
                intent.putExtra("USER_NAME", getUsername());
                startActivity(intent);
            }
        });
    }

    private void ConfigureCreateTournamentButton() {
        Button btn = (Button) findViewById(R.id.btnCreateTournament);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainMenuActivity.this, CreateTournament.class);
                intent.putExtra("USER_NAME", getUsername());
                startActivity(intent);
            }
        });
    }

    private void ConfigurePlayTournamentButton() {
        Button btn = findViewById(R.id.btnPlayTournement);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainMenuActivity.this, JoinTournamentActivity.class);
                intent.putExtra("USER_NAME", getUsername());
                startActivity(intent);
            }
        });
    }

    private void ConfigurePlayRandomPuzzleButton(){
        Button btn = (Button) findViewById(R.id.btnPlayPuzzle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, PlayPuzzle.class);
                intent.putExtra("USER_NAME", getUsername());
                startActivity(intent);
            }
        });
    }

    private void ConfigureContinueTournamentButton() {
        Button btn = findViewById(R.id.btnContinueTournament);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, ContinueTournamentActivity.class));
            }
        });
    }

    private void ConfigureViewStatsButton() {
        Button btn = (Button) findViewById(R.id.btnStats);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ViewStatActivity.class);
                intent.putExtra("USER_NAME", getUsername());
                startActivity(intent);
            }
        });
    }

    private void ConfigureQuitBtnReturnBackToLogin() {
        Button btn = findViewById(R.id.btnQuit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainMenuActivity.this, MainActivity.class));
            }
        });
    }

}
