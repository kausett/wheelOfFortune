package edu.gatech.seclass.sdpguessit.utils;

import edu.gatech.seclass.sdpguessit.database.AppDataBase;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.Tournament;
import edu.gatech.seclass.sdpguessit.entity.User;
import edu.gatech.seclass.sdpguessit.entity.TournamentPuzzle;
import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;

/**
 * Created by tofiques on 2/28/18.
 */

public class DatabaseInitializer {


    private static DatabaseInitializer instance;
    private static AppDataBase dataBase;

    public static DatabaseInitializer with(AppDataBase appDataBase) {

        if (dataBase == null)
            dataBase = appDataBase;

        if (instance == null)
            instance = new DatabaseInitializer();

        return instance;
    }

    public void generateuser(){
        User user= new User("abc");
       dataBase.userDao().register(user);
        Puzzle puzzle =new Puzzle("Testing",10,"abc");
        dataBase.puzzleDao().createPuzzle(puzzle);
        for (int i = 0; i <=5; i++){
            String phrase = "Testing" + i;
            Puzzle puzzle1 =new Puzzle(phrase,10,"xu");
            dataBase.puzzleDao().createPuzzle(puzzle1);
        }

//        UserPuzzleScore userPuzzleScore =new UserPuzzleScore("aa", 4, 100);
//        dataBase.userPuzzleScoreDao().addUserPuzzleScore(userPuzzleScore);

//        UserPuzzleScore userPuzzleScore2 =new UserPuzzleScore("aa", 4, 100);
//        dataBase.userPuzzleScoreDao().addUserPuzzleScore(userPuzzleScore2);


        Tournament tournament =new Tournament("t1", "xu");
        dataBase.tournamentDao().createTournament(tournament);

        Tournament tournament1 =new Tournament("t2", "xu");
        dataBase.tournamentDao().createTournament(tournament1);

        TournamentPuzzle tournamentPuzzle =new TournamentPuzzle("t1", 1);
        dataBase.tournamentPuzzleDao().addPuzzle(tournamentPuzzle);
        TournamentPuzzle tournamentPuzzle1 =new TournamentPuzzle("t1", 2);
        dataBase.tournamentPuzzleDao().addPuzzle(tournamentPuzzle1);

        TournamentPuzzle tournamentPuzzle3 =new TournamentPuzzle("t2", 1);
        dataBase.tournamentPuzzleDao().addPuzzle(tournamentPuzzle3);
        TournamentPuzzle tournamentPuzzle4 =new TournamentPuzzle("t2", 3);
        dataBase.tournamentPuzzleDao().addPuzzle(tournamentPuzzle4);
        TournamentPuzzle tournamentPuzzle5 =new TournamentPuzzle("t2", 4);
        dataBase.tournamentPuzzleDao().addPuzzle(tournamentPuzzle5);

    }
}
