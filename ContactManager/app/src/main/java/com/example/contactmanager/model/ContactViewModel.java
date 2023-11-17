package com.example.contactmanager.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactmanager.data.ContactRepositoryRoom;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    public static ContactRepositoryRoom repositoryRoom;
    public final LiveData<List<ContactRoom>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repositoryRoom = new ContactRepositoryRoom(application);
        allContacts = repositoryRoom.getAllData();
    }

    public LiveData<List<ContactRoom>> getAllContacts(){
        return allContacts;
    }

    public static void insert(ContactRoom contactRoom){
        repositoryRoom.insert(contactRoom);
    }

    public LiveData<ContactRoom> get(int id){
        return repositoryRoom.get(id);
    }

    public static void update(ContactRoom contactRoom){
        repositoryRoom.update(contactRoom);
    }

    public static void delete(ContactRoom contactRoom){
        repositoryRoom.delete(contactRoom);
    }
}
