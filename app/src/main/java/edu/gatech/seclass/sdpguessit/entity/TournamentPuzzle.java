package edu.gatech.seclass.sdpguessit.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tofiques on 3/1/18...
 */
@Entity(tableName = "tournament_puzzle",
        indices = {@Index("tournament_name"),@Index("puzzle_id") },
        foreignKeys = {
                @ForeignKey(entity = Tournament.class,
                        parentColumns = "tournament_name",
                        childColumns = "tournament_name"),
                @ForeignKey(entity = Puzzle.class,
                        parentColumns = "puzzleId",
                        childColumns = "puzzle_id")
        })
public class TournamentPuzzle {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tournament_name")
    public String tournamentName;

    @ColumnInfo(name = "puzzle_id")
    private int puzzleId;


    public TournamentPuzzle(String tournamentName, int puzzleId) {
        this.tournamentName = tournamentName;
        this.puzzleId = puzzleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }
}


