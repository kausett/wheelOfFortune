package edu.gatech.seclass.sdpguessit;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.User;
import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;

import static java.lang.Character.isLetter;
import static java.lang.Character.toUpperCase;

public class PlayPuzzle extends AppCompatActivity {
    Dialog solveDialog, keyBoardDialog, buyVowelDialog, messageDialog, exitGameDialog;
    TextView txtUsername;
    TextView txtTotalPrize;
    Button a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20;

    Puzzle puzzle;
    private int remainingNumberOfWrongGuessCounter;
    private int totalPrize;
    private String currentPhrase;
    private String phrase;
    private ArrayList<String> correctGuessedLetters = new ArrayList<>();
    private ArrayList<String> allUniqueLetters;
    private Boolean hasMoney;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_puzzle);

        solveDialog = new Dialog(this);
        keyBoardDialog = new Dialog(this);
        buyVowelDialog = new Dialog(this);
        messageDialog = new Dialog(this);
        exitGameDialog = new Dialog(this);

        buyVowelDialog.setContentView(R.layout.buy_a_vowel);
        keyBoardDialog.setContentView(R.layout.keyboard_layout);
        solveDialog.setContentView(R.layout.solve_popup);
        messageDialog.setContentView(R.layout.message_popup);
        exitGameDialog.setContentView(R.layout.exit_game_popup);

        txtUsername = findViewById(R.id.txtPuzzleUsername);
        txtTotalPrize = findViewById(R.id.txtTotalPrize);

        loadRandomPuzzle();
        configureRandomSpinner();
        configureSolvePuzzleButton();
        configureBuyAVowelButton();
        ConfigureButtonQuitConfig();
        ConfigureButtonContinueGame();
        ConfigureButtonAcceptedQuit();
    }

    private void ConfigureButtonQuitConfig(){
        Button btn = (Button)findViewById(R.id.txtExitGame);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitGameDialog.show();
            }
        });
    }

    private void ConfigureButtonAcceptedQuit(){
        Button btn = (Button)exitGameDialog.findViewById(R.id.btnQUitGame);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitGameDialog.dismiss();
                totalPrize = 0;
                saveData();
                finish();
            }
        });
    }

    private void ConfigureButtonContinueGame(){
        Button btn = (Button)exitGameDialog.findViewById(R.id.btnContinuePZGame);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitGameDialog.dismiss();
            }
        });
    }

    //Final done
    // Load Initial Values
    private void loadRandomPuzzle() {
        final AppDataBase database = AppDataBase.getAppDatabase(this);
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Puzzle> future = service.submit(new Callable<Puzzle>() {

            @Override
            public Puzzle call() throws Exception {
                String currentUser = getUsername();
                //I think we are selecting something elze
                List<Puzzle> puzzles = database.puzzleDao().puzzleByOtherUser(currentUser);
                int num = getRandomNumberBetweenXandY(0, puzzles.size());
                return puzzles.get(num);
            }
        });
        service.shutdown();
        try {
            puzzle = future.get();
            if (puzzle == null ) {
                Toast.makeText(getApplicationContext(), "There is not puzzle", Toast.LENGTH_SHORT).show();
            } else {

                a1 = findViewById(R.id.a1);
                a2 = findViewById(R.id.a2);
                a3 = findViewById(R.id.a3);
                a4 = findViewById(R.id.a4);
                a5 = findViewById(R.id.a5);
                a6 = findViewById(R.id.a6);
                a7 = findViewById(R.id.a7);
                a8 = findViewById(R.id.a8);
                a9 = findViewById(R.id.a9);
                a10 = findViewById(R.id.a10);
                a11 = findViewById(R.id.a11);
                a12 = findViewById(R.id.a12);
                a13 = findViewById(R.id.a13);
                a14 = findViewById(R.id.a14);
                a15 = findViewById(R.id.a15);
                a16 = findViewById(R.id.a16);
                a17 = findViewById(R.id.a17);
                a18 = findViewById(R.id.a18);
                a19 = findViewById(R.id.a19);
                a20 = findViewById(R.id.a20);

                // Initial Values
                remainingNumberOfWrongGuessCounter = puzzle.getGuessCount();
                totalPrize = 0;
                hasMoney = Boolean.FALSE;
                currentPhrase = puzzle.getPhrase();
                phrase = puzzle.getPhrase();
                allUniqueLetters = getUniqueCharacters(phrase);

                // Refresh Data
                txtUsername.setText(getUsername());
                currentPhrase = getInitialPhrase();
                displayPhrase();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String getInitialPhrase() {
        StringBuilder current_Phrase = new StringBuilder(phrase);
        for (int i = 0; i < phrase.length(); i++){
            if (isLetter(phrase.charAt(i))){
                current_Phrase.setCharAt(i, '?');
            }
        }
        return current_Phrase.toString();
    }

    // Actions
    private void configureBuyAVowelButton() {
        Button btnVowelA = buyVowelDialog.findViewById(R.id.vowelA);
        Button btnVowelE = buyVowelDialog.findViewById(R.id.vowelE);
        Button btnVowelI = buyVowelDialog.findViewById(R.id.vowelI);
        Button btnVowelO = buyVowelDialog.findViewById(R.id.vowelO);
        Button btnVowelU = buyVowelDialog.findViewById(R.id.vowelU);

        ImageButton vButton = findViewById(R.id.btnBuyVoewl);
        vButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyVowelDialog.show();
            }
        });

        btnVowelA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyVowel("a");
                displayPhrase();
                buyVowelDialog.dismiss();
            }
        });
        btnVowelE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyVowel("e");
                displayPhrase();
                buyVowelDialog.dismiss();
            }
        });
        btnVowelI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyVowel("i");
                displayPhrase();
                buyVowelDialog.dismiss();
            }
        });
        btnVowelO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyVowel("o");
                displayPhrase();
                buyVowelDialog.dismiss();
            }
        });
        btnVowelU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyVowel("u");
                displayPhrase();
                buyVowelDialog.dismiss();
            }
        });
    }

    private void configureRandomSpinner() {
        ImageButton imBtn = findViewById(R.id.tapToSpin);
        Button btnQ = keyBoardDialog.findViewById(R.id.Q);
        Button btnW = keyBoardDialog.findViewById(R.id.W);
        Button btnR = keyBoardDialog.findViewById(R.id.R);
        Button btnT = keyBoardDialog.findViewById(R.id.T);
        Button btnY = keyBoardDialog.findViewById(R.id.Y);
        Button btnP = keyBoardDialog.findViewById(R.id.P);
        Button btnS = keyBoardDialog.findViewById(R.id.S);
        Button btnD = keyBoardDialog.findViewById(R.id.D);
        Button btnF = keyBoardDialog.findViewById(R.id.F);
        Button btnG = keyBoardDialog.findViewById(R.id.G);
        Button btnH = keyBoardDialog.findViewById(R.id.H);
        Button btnJ = keyBoardDialog.findViewById(R.id.J);
        Button btnK = keyBoardDialog.findViewById(R.id.K);
        Button btnL = keyBoardDialog.findViewById(R.id.L);
        Button btnZ = keyBoardDialog.findViewById(R.id.Z);
        Button btnX = keyBoardDialog.findViewById(R.id.X);
        Button btnC = keyBoardDialog.findViewById(R.id.C);
        Button btnV = keyBoardDialog.findViewById(R.id.V);
        Button btnB = keyBoardDialog.findViewById(R.id.B);
        Button btnN = keyBoardDialog.findViewById(R.id.N);
        Button btnM = keyBoardDialog.findViewById(R.id.M);

        imBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txView = findViewById(R.id.randomSpinner);
                TextView txtAmt = keyBoardDialog.findViewById(R.id.txtAmountForKeybLayout);

                StringBuilder stringBuilder = new StringBuilder("$");
                int randNumber = getRandomNumberBetween1and100IncrementOf100();
                stringBuilder.append(randNumber);
                txView.setText(stringBuilder);
                RotateAnimation anim = new RotateAnimation(txView.getRotation(), 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(100);
                anim.setRepeatCount(1);
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                txView.startAnimation(anim);
                keyBoardDialog.show();

                StringBuilder str = new StringBuilder("$");
                str.append(randNumber);
                txtAmt.setText(str);
            }
        });

        btnQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("q");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("w");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("r");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("t");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("y");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("p");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("s");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("d");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("f");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("g");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("h");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("j");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("k");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("l");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });

        btnZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("z");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("x");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("c");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("v");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("b");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("n");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessConsonant("m");
                displayPhrase();
                keyBoardDialog.dismiss();
            }
        });
    }

    private void configureSolvePuzzleButton() {
        ImageButton btn = findViewById(R.id.tapToSolve);

        Button btnXClose = solveDialog.findViewById(R.id.Xsolve);
        Button btnCancel = solveDialog.findViewById(R.id.btnCancleSolve);
        Button btnSolvePuzzle = solveDialog.findViewById(R.id.btnSolvePuzzle);
        final EditText answerText = solveDialog.findViewById(R.id.solvedPuzzleAnswer);

        Button btnMainMenu = messageDialog.findViewById(R.id.btnMessageGoToMainMenu);
        final TextView txtMessage = messageDialog.findViewById(R.id.tvMessage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solveDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerText.setText("");
                solveDialog.dismiss();
            }
        });
        btnXClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerText.setText("");
                solveDialog.dismiss();
            }
        });
        btnSolvePuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = solvePuzzle(answerText.getText().toString());
                displayPhrase();
                messageDialog.show();
                txtMessage.setText(msg);
                saveData();
                solveDialog.dismiss();
            }
        });
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayPuzzle.this, MainMenuActivity.class);
                intent.putExtra("USER_NAME", getUsername());
                startActivity(intent);
                messageDialog.dismiss();
            }
        });
    }

    // Utilities
    public void displayPhrase() {
        for (int i = 0; i < currentPhrase.length(); i++){
            if (i == 0)
                a1.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 1)
                a2.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 2)
                a3.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 3)
                a4.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 4)
                a5.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 5)
                a6.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 6)
                a7.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 7)
                a8.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 8)
                a9.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 9)
                a10.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 10)
                a11.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 11)
                a12.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 12)
                a13.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 13)
                a14.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 14)
                a15.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 15)
                a16.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 16)
                a17.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 17)
                a18.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 18)
                a19.setText(Character.toString(currentPhrase.charAt(i)));
            if (i == 19)
                a20.setText(Character.toString(currentPhrase.charAt(i)));
        }
        txtTotalPrize.setText(String.valueOf(totalPrize));
    }

    private String getUsername() {
        return getIntent().getStringExtra("USER_NAME");
    }

    private boolean isGameFinished(String buttonText) {
        ArrayList<String> tempAllGuessedLetters = new ArrayList<>(correctGuessedLetters);
        tempAllGuessedLetters.add(buttonText);
        boolean result;
        if (equalLists(tempAllGuessedLetters, allUniqueLetters)){
            message = "Congratulations! You pass the game!";
            result = true;
        }else{
            message = "Great guess!";
            result = false;
        }
        return result;
    }

    private boolean equalLists(ArrayList one, ArrayList two){
        if (one == null && two == null){
            return true;
        }

        if(one == null || two == null || one.size() != two.size()){
            return false;
        }

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
    }

    static ArrayList<String> getUniqueCharacters(String input) {
        ArrayList<String> result = new ArrayList<>();
        String buffer = "";
        for (int i = 0; i < input.length(); i++) {
            if (!buffer.contains(String.valueOf(input.charAt(i)))) {
                buffer += input.charAt(i);
                result.add(Character.toString(input.charAt(i)));
            }
        }
        return result;
    }

    public void buyVowel(String buttonText) {
        StringBuilder result = new StringBuilder(currentPhrase);
        boolean isContain = phrase.toLowerCase().contains(buttonText.toLowerCase());
        char ch = buttonText.charAt(0);
        if (totalPrize >= 300) {
            totalPrize -= 300;
            if (isContain) {
                for (int i = 0; i < phrase.length(); i++) {
                    if (phrase.charAt(i) == ch) {
                        result.setCharAt(i, toUpperCase(ch));//updated shown phrase
                    }
                }
                currentPhrase = result.toString();
                correctGuessedLetters.add(buttonText);
                isGameFinished(buttonText);
            } else {
                remainingNumberOfWrongGuessCounter -= 1;
                message = "Try again!";
                if (remainingNumberOfWrongGuessCounter < 0) {
                    totalPrize = 0;
                    message = "You failed the game!";
                }
            }
        }
    }

    public void guessConsonant(String buttonText) {
        Random random = new Random();
        int randomNumber = random.nextInt(10 ) + 1;

        StringBuilder result = new StringBuilder(currentPhrase);
        boolean isCorrectGuess = phrase.toLowerCase().contains(buttonText.toLowerCase());
        char ch = buttonText.charAt(0);
        //check whether current guess is right or not, if right, make updates with some fields and also check whether the game is finished or not
        if (isCorrectGuess){
            for (int i = 0; i < phrase.length(); i++){
                if (phrase.charAt(i) == ch){
                    result.setCharAt(i, toUpperCase(ch));//updated shown phrase
                    totalPrize += randomNumber * 100;//update prize value
                }
            }
            currentPhrase = result.toString();
            correctGuessedLetters.add(buttonText);
            //check is more guess is needed, if needed, do nothing,show "Good guess",  otherwise endgame, show "user passed the game"
            isGameFinished(buttonText);

        }else{
            remainingNumberOfWrongGuessCounter -= 1;
            this.message = "Try again";
            if (remainingNumberOfWrongGuessCounter < 0){
                totalPrize = 0;
                message = "You failed the game!";
            }
        }

    }

    public String solvePuzzle(String answer) {
        ArrayList<String> selections;
        selections = getUniqueCharacters(answer);
        if (equalLists(selections, allUniqueLetters)){
            currentPhrase = phrase;//display all letters
            int count = 0;
            for(String string : selections){
                for (int i = 0; i < phrase.length(); i++) {
                    if (Objects.equals(Character.toString(phrase.charAt(i)), string)) {
                        count++;
                    }
                }
            }
            totalPrize = count * 1000;
            message = "You win the game!";
        }else{
            totalPrize = 0;
            message = "You failed the game!";
        }
        return message;
    }

    private Integer getRandomNumberBetween1and100IncrementOf100() {
        Integer max = 1000;
        Integer min = 100;
        Random rnd = new Random();
        return (rnd.nextInt(9) + 1) * min;
    }

    private Integer getRandomNumberBetweenXandY(int min, int max) {

        Random rnd = new Random();
        return rnd.nextInt((max - min) + 1) + min;
    }

    public void getValueOfClickedButton(View view) {
        Button btn = (Button) view;
        String Text = (String) btn.getText();
        keyBoardDialog.dismiss();
    }

    private void saveData() {
        final AppDataBase database = AppDataBase.getAppDatabase(this);
        Thread t = new Thread() {
            public void run() {
                final UserPuzzleScore userPuzzleScore = new UserPuzzleScore(getUsername(), puzzle.getPuzzleId(), totalPrize);
                database.userPuzzleScoreDao().addUserPuzzleScore(userPuzzleScore);
            }
        };
        t.start();

    }
}
