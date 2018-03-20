package edu.gatech.seclass.sdpguessit.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "userpuzzlescore",
        indices = {@Index("username"),@Index("puzzle_id") },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "name",
                        childColumns = "username"),
                @ForeignKey(entity = Puzzle.class,
                        parentColumns = "puzzleId",
                        childColumns = "puzzle_id")
        }
)
public class UserPuzzleScore {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    public String userName;

    @ColumnInfo(name = "puzzle_id")
    private int puzzleId;

    private int score;

    public UserPuzzleScore(String userName, int puzzleId,int score) {
        this.userName = userName;
        this.puzzleId = puzzleId;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}


