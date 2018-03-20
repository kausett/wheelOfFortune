package edu.gatech.seclass.sdpguessit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.seclass.sdpguessit.entity.Puzzle;


/**
 * Created by tofiques on 2/28/18.
 */

@Dao
public interface PuzzleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createPuzzle(Puzzle puzzle);

    @Query("Select * from puzzle where created_by!=:userName")
    List<Puzzle> puzzleByOtherUser(String userName);


    @Query("Select * from puzzle where created_by=:userName")
    List<Puzzle> puzzleByUser(String userName);
}

