package edu.gatech.seclass.sdpguessit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.seclass.sdpguessit.utils.TournamentStat;
import edu.gatech.seclass.sdpguessit.utils.UserTournamentStat;
import edu.gatech.seclass.sdpguessit.entity.Puzzle;
import edu.gatech.seclass.sdpguessit.entity.Tournament;
import edu.gatech.seclass.sdpguessit.entity.UserTournament;


@Dao
public interface UserTournamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUserTournament(UserTournament userTournament);

    @Query("Select distinct a.tournament_name,a.username, sum(ups.score) as tournament_score  " +
            "from (select distinct ut.*  from usertournament ut " +
            " where ut.username =:username and not exists (select distinct * from tournament_puzzle tp1 " +
            "where tp1.tournament_name = ut.tournament_name and tp1.puzzle_id not in (select distinct puzzle_id " +
            "from userpuzzlescore ups2 " +
            " where ups2.username =:username))" +
            "  ) as a inner join tournament_puzzle tp on a.tournament_name = tp.tournament_name" +
            " inner join userpuzzlescore ups on tp.puzzle_id = ups.puzzle_id and ups.username = :username " +
            " group by a.tournament_name order by tournament_score DESC")
    List<UserTournamentStat> getCompleteTournamentStatForUser(String username);

    @Query("select distinct h.tournament_name, h.num_player, h.top_score, f.username " +
            "from (select tournament_name,count(*) as num_player, max(tn_score) as top_score " +
            "from (select distinct a.tournament_name, a.username,sum(ups3.score) as tn_score " +
            "from (Select ut.* from usertournament ut   " +
            "where not exists (select distinct * from tournament_puzzle tp1 " +
            "where tp1.tournament_name = ut.tournament_name and tp1.puzzle_id not in (select puzzle_id " +
            "from userpuzzlescore ups2 " +
            " where ups2.username = ut.username))" +
            ") as a " +
            "inner join tournament_puzzle as tp2 on a.tournament_name = tp2.tournament_name " +
            "inner join userpuzzlescore ups3 on a.username = ups3.username and ups3.puzzle_id = tp2.puzzle_id" +
            " group by a.tournament_name, a.username ) " +
            "group by tournament_name) as h " +
            " inner join (select distinct a.tournament_name, a.username,sum(ups3.score) as tn_score " +
            "from (Select ut.* from usertournament ut   " +
            "where not exists (select distinct * from tournament_puzzle tp1 " +
            "where tp1.tournament_name = ut.tournament_name and tp1.puzzle_id not in (select puzzle_id " +
            "from userpuzzlescore ups2 " +
            "where ups2.username = ut.username))" +
            ") as a inner join tournament_puzzle as tp2 on a.tournament_name = tp2.tournament_name " +
            "inner join userpuzzlescore ups3 on a.username = ups3.username and ups3.puzzle_id = tp2.puzzle_id" +
            " group by a.tournament_name, a.username ) as f" +
            " on h.tournament_name = f.tournament_name  and h.top_score = f.tn_score order by h.num_player DESC ")
    List<TournamentStat> getCompleteTournamentStat();


    @Query("Select distinct t.* from tournament t" +
            " where not exists (select * from tournament_puzzle tp inner join puzzle on puzzle.puzzleId = tp.puzzle_id" +
                                 " where tp.tournament_name = t.tournament_name and puzzle.created_by = :username)" +
             "and not exists (select * from usertournament ut where ut.tournament_name = t.tournament_name and ut.username = :username)" +
            "and exists (select * from tournament_puzzle tp1 " +
                          "where tp1.tournament_name = t.tournament_name and tp1.puzzle_id not in (select puzzle_id " +
                           "from userpuzzlescore ups2 where ups2.username =:username))")
    List<Tournament> joinNewTournamentForUser(String username);

    @Query("Select distinct t.* from tournament t inner join usertournament ut ON ut.tournament_name = t.tournament_name" +
            " where ut.username =:username and exists (select distinct * from tournament_puzzle tp1 " +
            "where tp1.tournament_name = t.tournament_name and tp1.puzzle_id not in (select puzzle_id " +
            "from userpuzzlescore ups2 " +
            "where ups2.username =:username))")
    List<Tournament> continueTournamentForUser(String username);

    @Query("Select distinct p.* from puzzle p inner join tournament_puzzle tp on p.puzzleId = tp.puzzle_id where tp.tournament_name = :tournamentname" +
            " and not exists (select * from userpuzzlescore ups where ups.puzzle_id = p.puzzleId and ups.username = :username)")
    List<Puzzle> getUnfinishedPuzzlesWithinTournamentForUser(String tournamentname, String username);

    @Query("Select distinct p.* from puzzle p inner join tournament_puzzle tp on p.puzzleId = tp.puzzle_id where tp.tournament_name = :tournamentname" +
            " and exists (select * from userpuzzlescore ups where ups.puzzle_id = p.puzzleId and ups.username = :username)")
    List<Puzzle> getfinishedPuzzlesWithinTournamentForUser(String tournamentname, String username);

}