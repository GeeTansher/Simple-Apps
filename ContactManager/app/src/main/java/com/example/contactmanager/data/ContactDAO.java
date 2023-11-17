package com.example.contactmanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactmanager.model.ContactRoom;

import java.util.List;

// DAO - Data Access Object (For Room)
@Dao
public interface ContactDAO {
    // CRUD operations

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ContactRoom contactRoom);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    LiveData<List<ContactRoom>> getAllContacts();

    @Query("SELECT * FROM contact_table WHERE contact_table.id == :id")
    LiveData<ContactRoom> get(int id);

    @Update
    void update(ContactRoom contactRoom);

    @Delete
    void delete(ContactRoom contactRoom);

}
