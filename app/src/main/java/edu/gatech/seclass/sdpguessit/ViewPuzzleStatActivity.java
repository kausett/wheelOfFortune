package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;
import edu.gatech.seclass.sdpguessit.utils.PuzzleStat;

public class ViewPuzzleStatActivity extends AppCompatActivity {

    String username;
    private List<UserPuzzleScore> data = new ArrayList<>();
    ViewPuzzleStatAdapter Adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_puzzle_stat);

        ConfigureBackToPuzzle();
        //get username from main menu intent
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        final AppDataBase database = AppDataBase.getAppDatabase(this);
        ExecutorService service = Executors.newCachedThreadPool();
        Future<List<UserPuzzleScore>> future=service.submit(new Callable<List<UserPuzzleScore>>() {
            @Override
            public List<UserPuzzleScore> call() throws Exception {
                List<UserPuzzleScore> puzzleStats = database.userPuzzleScoreDao().getPuzzleStatByUser(username);
                return puzzleStats;
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
        Adapter = new ViewPuzzleStatAdapter(this, data);
        recyclerView.setAdapter(Adapter);
    }

    private void ConfigureBackToPuzzle(){
        Button btn = (Button)findViewById(R.id.btnBackToStatshome);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
