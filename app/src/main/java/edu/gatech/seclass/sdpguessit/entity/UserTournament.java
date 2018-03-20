package edu.gatech.seclass.sdpguessit.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(
        tableName = "usertournament",
        indices = {@Index("tournament_name"),@Index("username") },
//        primaryKeys = {"username", "tournament_name"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "name",
                        childColumns = "username"),
                @ForeignKey(entity = Tournament.class,
                        parentColumns = "tournament_name",
                        childColumns = "tournament_name")
        }
)

public class UserTournament {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NonNull
    @ColumnInfo(name = "username")
    public String userName;

    @NonNull
    @ColumnInfo(name = "tournament_name")
    private String tournamentName;



    public UserTournament(String userName, String tournamentName) {
        this.userName = userName;
        this.tournamentName = tournamentName;

    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

}


