package edu.gatech.seclass.sdpguessit.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import edu.gatech.seclass.sdpguessit.dao.PuzzleDao;
import edu.gatech.seclass.sdpguessit.dao.TournamentDao;
import edu.gatech.seclass.sdpguessit.dao.TournamentPuzzleDao;
import edu.gatech.seclass.sdpguessit.dao.UserDao;
import edu.gatech.seclass.sdpguessit.dao.UserPuzzleScoreDao;
import edu.gatech.seclass.sdpguessit.dao.UserTournamentDao;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.Tournament;
import edu.gatech.seclass.sdpguessit.entity.TournamentPuzzle;
import edu.gatech.seclass.sdpguessit.entity.User;
import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;
import edu.gatech.seclass.sdpguessit.entity.UserTournament;

/**
 * Created by tofiques on 2/28/18.
 */
//@Database(entities = {User.class, Puzzle.class, TournamentPuzzle.class, Tournament.class},version=6,exportSchema = false)
@Database(entities = {User.class, Puzzle.class, TournamentPuzzle.class, Tournament.class, UserPuzzleScore.class, UserTournament.class},version=10,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;
    public abstract UserDao userDao();
    public abstract PuzzleDao puzzleDao();
    public abstract TournamentDao tournamentDao();
    public abstract TournamentPuzzleDao tournamentPuzzleDao();
    public abstract UserPuzzleScoreDao userPuzzleScoreDao();
    public abstract UserTournamentDao userTournamentDao();

    public static AppDataBase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class,
                    "game-db")
                    .fallbackToDestructiveMigration()
                   // .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
