package edu.gatech.seclass.sdpguessit.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by tofiques on 3/1/18.
 */

@Entity(tableName = "tournament")
public class Tournament {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tournament_name")
    public String tournamentName;

    @ColumnInfo(name = "created_by")
    private  String createdBy;

    @Ignore
    List<Puzzle> puzzles;

    public Tournament(@NonNull String tournamentName, String createdBy) {
        this.tournamentName = tournamentName;
        this.createdBy = createdBy;
    }

    @NonNull
    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(@NonNull String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }
}
