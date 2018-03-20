package edu.gatech.seclass.sdpguessit.utils;

/**
 * Created by ssanchez on 3/9/2018.
 */

public class PuzzleStat {
    int puzzleId;
    int numPlayers;
    int top_score;
    String username;

    public PuzzleStat( int puzzleId, int numPlayers, int top_score,String username ) {
        this.puzzleId = puzzleId;
        this.numPlayers = numPlayers;
        this.top_score = top_score;
        this.username = username;
    }

    public int getPuzzleID() {
        return puzzleId;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getTopScore() {
        return top_score;
    }

    public String getUsername() {
        return username;
    }
}
