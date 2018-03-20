package edu.gatech.seclass.sdpguessit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import edu.gatech.seclass.sdpguessit.entity.User;

/**
 * Created by tofiques on 2/28/18.
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void register(User users);

    @Query("Select * from user where name=:userName")
    User login(String userName);

    @Query("Select * FROM user")
    User[] loadAll();
}