package edu.gatech.seclass.sdpguessit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;

public class CreatePuzzle extends AppCompatActivity {
    //get the id of the text box
    private EditText phrase;
    private EditText maxNum;


    private String getUsername() {
        return getIntent().getStringExtra("USER_NAME");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_puzzle);
        CondigurebtnCancelCreatePuzzle();
        CondigurebtnSaveCreatePuzzle();
    }

    private void CondigurebtnCancelCreatePuzzle() {
        Button btn = (Button) findViewById(R.id.btnCancelCreatePuzzle);
        phrase = findViewById(R.id.txtCreatePuzzle);
        maxNum = findViewById(R.id.txtMaxGuessNumber);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(CreatePuzzle.this, MainMenuActivity.class));
            }
        });
    }

    private void CondigurebtnSaveCreatePuzzle() {
        Button btn = (Button) findViewById(R.id.btnCreatePuzzlePhrase);
        final AppDataBase database = AppDataBase.getAppDatabase(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maxNum.getText().toString().isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            maxNum.setError("Can not be empty");

                        }
                    });
                }else {
                    Integer max = Integer.parseInt(maxNum.getText().toString());
                    if (max > 9 || max < 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                maxNum.setError("Enter number between 0-9");

                            }
                        });
                    }
                }
                if (phrase.getText().toString().trim().isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            phrase.setError("Phrase can not be blank");

                        }
                    });
                } else {

                    final Puzzle puzzle = new Puzzle(phrase.getText().toString(), Integer.parseInt(maxNum.getText().toString()), getUsername());
                    Thread t = new Thread() {
                        public void run() {

                            database.puzzleDao().createPuzzle(puzzle);
                        }
                    };
                    t.start();
                    finish();
                    Intent intent = new Intent(CreatePuzzle.this, MainMenuActivity.class);
                    intent.putExtra("USER_NAME", getUsername());
                    finish();
                }

            }
        });
    }

    private boolean checkIfMaxNumberIsInRangeOf1to10() {

        return false;
    }


}
