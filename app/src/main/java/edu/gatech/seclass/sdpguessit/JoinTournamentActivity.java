package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.Tournament;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;

public class JoinTournamentActivity extends AppCompatActivity {
    private ArrayList<String> tournamentNames ;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tournament);
        ListView lv = findViewById(R.id.lvAvailableTournaments);

        //get username from main menu intent
        Intent intent = getIntent();
        username = getUsername();

        // TODO: find the tournament name from db
        tournamentNames = new ArrayList<String>();

        final AppDataBase database = AppDataBase.getAppDatabase(this);
        Thread t = new Thread() {
            public void run() {
                List<Tournament> tournaments = database.userTournamentDao().joinNewTournamentForUser(username);
                for (Tournament tournament :tournaments){
                    tournamentNames.add(tournament.getTournamentName());
                }
            }
        };
        t.start();

//        tournamentNames.add("t1");
//        tournamentNames.add("t2");
//        tournamentNames.add("t3");
//        tournamentNames.add("t4");
//        tournamentNames.add("t5");
//        tournamentNames.add("t6");
//        tournamentNames.add("t7");
        ArrayAdapter<String> tournamentAdapter = new ArrayAdapter<String>(JoinTournamentActivity.this,android.R.layout.simple_list_item_1,tournamentNames  );
        lv.setAdapter(tournamentAdapter);

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(JoinTournamentActivity.this, PlayPuzzle.class);
                intent.putExtra("tournamentName", tournamentNames.get(position));
                intent.putExtra("USER_NAME", username);
                startActivity(intent);
                finish();
            }
        });



    }

    private String getUsername() {
        return getIntent().getStringExtra("USER_NAME");
    }
}
