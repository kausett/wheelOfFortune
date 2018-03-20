package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.*;
import edu.gatech.seclass.sdpguessit.entity.Tournament;

public class ContinueTournamentActivity extends AppCompatActivity {
    private ArrayList<String> tournamentNames ;
    private String username;
    ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_tournament);
        ListView lv = findViewById(R.id.lvAvailableTournaments);

        //get username from main menu intent
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
      //  Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        // TODO: find the tournament name from db
        tournamentNames = new ArrayList<String>();
        final AppDataBase database = AppDataBase.getAppDatabase(this);
        Thread t = new Thread() {
            public void run() {
                tournaments = new ArrayList<>(database.userTournamentDao().continueTournamentForUser(username));
                for (edu.gatech.seclass.sdpguessit.entity.Tournament tournament :tournaments){
                    tournamentNames.add(tournament.getTournamentName());
                }
            }
        };
        t.start();

        tournamentNames.add("t1");
        tournamentNames.add("t2");
        tournamentNames.add("t3");
        tournamentNames.add("t4");
        tournamentNames.add("t5");
        tournamentNames.add("t6");
        tournamentNames.add("t7");

        ArrayAdapter<String> tournamentAdapter = new ArrayAdapter<String>(ContinueTournamentActivity.this,android.R.layout.simple_list_item_1,tournamentNames  );
        lv.setAdapter(tournamentAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Toast.makeText(getApplicationContext(), "you choose " + (tournamentNames.get(position)), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ContinueTournamentActivity.this, PlayPuzzle.class);
                intent.putExtra("tournamentName", tournamentNames.get(position));
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

    }
}
