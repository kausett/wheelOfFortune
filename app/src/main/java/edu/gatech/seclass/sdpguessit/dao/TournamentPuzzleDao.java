package edu.gatech.seclass.sdpguessit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.seclass.sdpguessit.entity.TournamentPuzzle;

/**
 * Created by tofiques on 3/1/18.
 */

@Dao
public interface TournamentPuzzleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPuzzle(TournamentPuzzle tournamentPuzzle);

   @Query("Select * from tournament_puzzle where tournament_name =:tournamentName")
   List<TournamentPuzzle> getPuzzlesForTournament(String tournamentName);

}
