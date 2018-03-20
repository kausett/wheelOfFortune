package edu.gatech.seclass.sdpguessit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.UserPuzzleScore;
import edu.gatech.seclass.sdpguessit.utils.PuzzleStat;


@Dao
public interface UserPuzzleScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUserPuzzleScore(UserPuzzleScore userPuzzleScore);

    @Query("Select distinct * from userpuzzlescore where username =:username")
    List<UserPuzzleScore> getPuzzleStatByUser(String username);

    @Query("Select distinct * from puzzle where created_by =:username or " +
            " puzzleId in (select puzzle_id from userpuzzlescore where username =:username) ")
    List<Puzzle> getPuzzleForCreateTournamentByUser(String username);

    @Query("Select distinct a.puzzleId as puzzleId, a.numPlayers, a.top_score, b.username from (select p.puzzleId, count(*) as numPlayers, max(score) as top_score " +
            "from puzzle p inner join userpuzzlescore us on p.puzzleId = us.puzzle_id group by p.puzzleId) as a inner join userpuzzlescore as b " +
            " on a.puzzleId = b.puzzle_id and a.top_score = b.score order by a.numPlayers desc")
    List<PuzzleStat> getPuzzleStat();


}