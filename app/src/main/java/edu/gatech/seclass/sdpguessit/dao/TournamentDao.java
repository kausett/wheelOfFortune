package edu.gatech.seclass.sdpguessit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.seclass.sdpguessit.entity.Tournament;

/**
 * Created by tofiques on 3/1/18.
 */

@Dao
public interface TournamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTournament(Tournament tournament);

    @Query("Select * from tournament where created_by!=:userName")
    List<Tournament> tournamentByOtherUser (String userName);

    @Query("Select * from tournament where created_by =:userName")
    List<Tournament> tournamentByUser (String userName);
}
