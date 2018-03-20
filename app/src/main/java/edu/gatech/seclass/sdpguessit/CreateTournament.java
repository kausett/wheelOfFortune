package edu.gatech.seclass.sdpguessit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.Tournament;
import edu.gatech.seclass.sdpguessit.entity.TournamentPuzzle;

import static java.lang.Math.toIntExact;

public class CreateTournament extends AppCompatActivity {

    private String getUsername() {

        return getIntent().getStringExtra("USER_NAME");
    }

    ListView lv;
    List<Puzzle> puzzles = new ArrayList<>();
    boolean[] checkedItems;
    ArrayList<Integer> mSelectedPuzzl = new ArrayList<>();
    ArrayList<String> selectedPuzzlesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        CondigurebtnCancelCreateTournament();
        CondigurebtnCreateTournament();
//        lv = (ListView) findViewById(R.id.lvTournament);

        final AppDataBase database = AppDataBase.getAppDatabase(this);
        ExecutorService service = Executors.newCachedThreadPool();
        Future<List<Puzzle>> future = service.submit(new Callable<List<Puzzle>>() {
            @Override
            public List<Puzzle> call() throws Exception {
                List<Puzzle> puzzles = database.puzzleDao().puzzleByUser(getUsername());
                return puzzles;
            }

        });
        service.shutdown();
        try {
            puzzles = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final Button mChooseTournament = (Button) findViewById(R.id.chooseTournamentList);

        final String[] mPuzzle = new String[puzzles.size()];
        for (int p = 0; p < puzzles.size(); p++) {
            mPuzzle[p] = "Id: " + Integer.toString(puzzles.get(p).getPuzzleId()) + " Phrase: " + puzzles.get(p).getPhrase();
        }

        checkedItems = new boolean[mPuzzle.length];
        mChooseTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CreateTournament.this);
                mbuilder.setTitle("Choose 5 puzzle for the Tournament");
                mbuilder.setMultiChoiceItems(mPuzzle, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mSelectedPuzzl.add(position);
                        } else {
                            mSelectedPuzzl.remove((Integer.valueOf(position)));
                        }
                    }
                });
//
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        ArrayList<String> puzzleIds = new ArrayList<>();
                        for (int i = 0; i < mSelectedPuzzl.size(); i++) {
                            item = item + mPuzzle[mSelectedPuzzl.get(i)];
                            if (i != mSelectedPuzzl.size() - 1) {
                                item = item + ",";
                            }

                        }



                        String[] result = item.split(",");

                        for (String srt : result){
                            String[] anser  = srt.split("\\s");
                            puzzleIds.add(anser[1]);
                        }

                        int puzzleIdSize = puzzleIds.size();
                        if (puzzleIdSize <= 5)
                            selectedPuzzlesDialog = puzzleIds;
                        else
                            Toast.makeText(getApplicationContext(), "You can only select upto 5 puzzles!", Toast.LENGTH_SHORT).show();

                    }

                });

                mbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mbuilder.setNeutralButton("Clear Selects", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mSelectedPuzzl.clear();
                        }
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });
    }


    private void CondigurebtnCancelCreateTournament() {
        Button btn = (Button) findViewById(R.id.btnCancelCreateTournament);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(CreateTournament.this, MainMenuActivity.class);
                intent.putExtra("USER_NAME", getUsername());
                startActivity(intent);
            }
        });
    }

    private void CondigurebtnCreateTournament() {
        Button btn = (Button) findViewById(R.id.button);


        final AppDataBase database = AppDataBase.getAppDatabase(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tournName = (EditText) findViewById(R.id.tournamentName);
                final String tournamentName = tournName.getText().toString().trim();

                ExecutorService service = Executors.newCachedThreadPool();
                if (tournamentName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Tournament Name ", Toast.LENGTH_SHORT).show();
                } else if (selectedPuzzlesDialog == null || selectedPuzzlesDialog.isEmpty() || selectedPuzzlesDialog.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please select a puzzle", Toast.LENGTH_SHORT).show();
                } else {

                    Future<Tournament> future = service.submit(new Callable<Tournament>() {
                        @Override
                        public Tournament call() throws Exception {
                            Tournament tournament = new Tournament(tournamentName, getUsername());
                            database.tournamentDao().createTournament(tournament);
                            return tournament;
                        }
                    });
                    service.shutdown();
                    try {
                        final Tournament tournament = future.get();
                        for (String s : selectedPuzzlesDialog) {
                            final Integer puzzId = Integer.valueOf(s);
                            Thread t = new Thread() {
                                public void run() {

                                    TournamentPuzzle tournamentPuzzle = new TournamentPuzzle(tournament.getTournamentName(), puzzId);
                                    database.tournamentPuzzleDao().addPuzzle(tournamentPuzzle);

                                }
                            };
                            t.start();



                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                    Intent intent = new Intent(CreateTournament.this, MainMenuActivity.class);
                    intent.putExtra("USER_NAME", getUsername());
                    startActivity(intent);
                }
            }
        });
    }
}
