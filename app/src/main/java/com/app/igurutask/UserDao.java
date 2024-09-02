package com.app.igurutask;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAllUsers();

    @Insert
    void insert(UserEntity user);
}
