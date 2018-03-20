package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.utils.PuzzleStat;
import edu.gatech.seclass.sdpguessit.utils.TournamentStat;

public class ViewAllTournamentStatActivity extends AppCompatActivity {
    String username;
    private List<TournamentStat> data ;
    ViewTournamentStatAdapter Adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tournament_stat);
        ConfiureBackToStatButton();
        //get username from main menu intent

        //TODO: get all results from db: and return it whole string: puzzle id + score. save it to data
        data = new ArrayList<TournamentStat>();
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");

        final AppDataBase database = AppDataBase.getAppDatabase(this);
        ExecutorService service = Executors.newCachedThreadPool();
        Future<List<TournamentStat>> future=service.submit(new Callable<List<TournamentStat>>() {
            @Override
            public List<TournamentStat> call() throws Exception {
                List<TournamentStat> tournamentStat = database.userTournamentDao().getCompleteTournamentStat();
                return tournamentStat;
            }
        });
        service.shutdown();
        try{
            data=future.get();
        }catch (Exception e){
            e.printStackTrace();
        }


        recyclerView = (RecyclerView)findViewById(R.id.userStat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter = new ViewTournamentStatAdapter(this, data);
        recyclerView.setAdapter(Adapter);
    }



    private void ConfiureBackToStatButton(){
        Button btn = (Button)findViewById(R.id.btnBkToStatshme);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
