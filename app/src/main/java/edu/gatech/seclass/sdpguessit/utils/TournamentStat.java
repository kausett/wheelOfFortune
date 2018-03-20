package edu.gatech.seclass.sdpguessit.utils;

/**
 * Created by Yanqun on 2018/3/6.
 */

public class TournamentStat {
    String tournament_name;
    int num_player;
    int top_score;
    String username;

    public String getTournamentName() {
        return tournament_name;
    }

    public int getNumPlayer() {
        return num_player;
    }

    public int getTopScore() {
        return top_score;
    }

    public String getUsername() {
        return username;
    }

    public TournamentStat(String tournament_name, int num_player, int top_score, String username ) {
        this.tournament_name = tournament_name;
        this.num_player = num_player;
        this.top_score = top_score;
        this.username = username;
    }
}
