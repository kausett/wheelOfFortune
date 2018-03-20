package edu.gatech.seclass.sdpguessit.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tofiques on 2/28/18.
 */

@Entity(tableName = "puzzle")
public class Puzzle {

    public Puzzle(String phrase, int guessCount, String createdBy) {
        this.phrase = phrase;
        this.guessCount = guessCount;
        this.createdBy = createdBy;
    }

    @PrimaryKey(autoGenerate = true)
    private int puzzleId;

    @ColumnInfo(name = "phrase")
    private  String phrase;

    @ColumnInfo(name = "guess_count")
    private  int guessCount;

    @ColumnInfo(name = "created_by")
    private  String createdBy;

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getPhrase() {
        return phrase.toLowerCase();
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public void setGuessCount(int guessCount) {
        this.guessCount = guessCount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public String toString() {
        return phrase;
    }
}
