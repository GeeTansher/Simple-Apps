package com.example.contactmanager.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contactmanager.model.ContactRoom;
import com.example.contactmanager.util.ContactRoomDatabase;

import java.util.List;

public class ContactRepositoryRoom {

    private ContactDAO contactDAO;
    private LiveData<List<ContactRoom>> allContacts;

    public ContactRepositoryRoom(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDAO = db.contactDAO();

        allContacts = contactDAO.getAllContacts();
    }

    public LiveData<List<ContactRoom>> getAllData(){
        return allContacts;
    }

    public void insert(ContactRoom contactRoom){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDAO.insert(contactRoom));
    }

    public LiveData<ContactRoom> get(int id){
        return contactDAO.get(id);
    }

    public void update(ContactRoom contactRoom){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDAO.update(contactRoom));
    }

    public void delete(ContactRoom contactRoom){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDAO.delete(contactRoom));
    }
}
