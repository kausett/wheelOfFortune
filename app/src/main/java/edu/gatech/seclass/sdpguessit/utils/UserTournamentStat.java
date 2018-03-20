package edu.gatech.seclass.sdpguessit.utils;

/**
 * Created by Yanqun on 2018/3/6.
 */

public class UserTournamentStat {
    String username;
    String tournament_name;
    int tournament_score;

    public UserTournamentStat( String tournament_name, String username, int tournament_score) {
        this.username = username;
        this.tournament_name = tournament_name;
        this.tournament_score = tournament_score;
    }

    public String getUsername() {
        return username;
    }


    public String getTournamentName() {
        return tournament_name;
    }

    public int getScore() {
        return tournament_score;
    }


}
