package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewStatActivity extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stat);

        //get username from main menu intent
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
//
        onViewPuzzleStat();
        onViewTournamentStat();
        onViewAllPuzzleStat();
        onViewAllTournamentStat();
        onViewBackToHomeMenu();

    }

    public void onViewPuzzleStat(){
        Button saveButton = (Button) findViewById(R.id.btnViewPuzzlesCompleted);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewStatActivity.this, ViewPuzzleStatActivity.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);

            }
        });
    }

    public void onViewTournamentStat(){
        Button saveButton = (Button) findViewById(R.id.btnViewTournamentsCompleted);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewStatActivity.this, ViewTournamentStatActivity.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);

            }
        });
    }

    public void onViewAllPuzzleStat(){
        Button saveButton = (Button) findViewById(R.id.btnViewListOfPuzzles);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewStatActivity.this, ViewListOfPuzzleStatActivity.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);

            }
        });
    }

    public void onViewAllTournamentStat() {
        Button saveButton = (Button) findViewById(R.id.btnViewListOfTournaments);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewStatActivity.this, ViewAllTournamentStatActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }


    public void onViewBackToHomeMenu(){
        Button saveButton = (Button) findViewById(R.id.btnBck2hmbtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
