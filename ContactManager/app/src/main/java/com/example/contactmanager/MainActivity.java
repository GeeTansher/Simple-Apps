package com.example.contactmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactmanager.adapter.RecyclerViewAdapter;
import com.example.contactmanager.model.ContactRoom;
import com.example.contactmanager.model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickListener {
    public static final String CONTACT_ID = "contact_id";
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;

    //TODO: replace startActivityForResults() with new one...
    //TODO: error of arrayOutOfBound when item is clicked on onContactClicked function.

    private ContactViewModel contactViewModel;
    //    private ArrayAdapter<String> arrayAdapter;
//    private ArrayList<String> arrayList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
         In this i have use Room related classes for data managing
         no the SQLite one so it's there only for code but no use in this app
        */

//        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.rvContactsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(MainActivity.this,
                contactRooms -> {

                    // set Recycler Adapter
                    recyclerViewAdapter = new RecyclerViewAdapter(contactRooms, MainActivity.this,
                            this);
                    recyclerView.setAdapter(recyclerViewAdapter);

/*
                    for (ContactRoom contactRoom : contactRooms) {
                        arrayList.clear();
                        arrayList.add(contactRoom.getName());
                    }
//                     creating arrayAdapter
                    arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            arrayList);

//                     add to listview
                    lvContactList = setAdapter(arrayAdapter);
*/

                });


        FloatingActionButton btnFloatingAddContact = findViewById(R.id.btnFloatingAddContact);
        btnFloatingAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewContact.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            assert data != null;
            ContactRoom contactRoom = new ContactRoom(data.getStringExtra(NewContact.NAME_REPLY),
                    data.getStringExtra(NewContact.OCCUPATION));
            ContactViewModel.insert(contactRoom);
        }
    }

    @Override
    public void onContactClick(int position) {

        ContactRoom contactRoom = Objects.requireNonNull(contactViewModel.allContacts
                .getValue()).get(position);
        Intent intent = new Intent(MainActivity.this, NewContact.class);
        intent.putExtra(CONTACT_ID, contactRoom.getId());
        startActivity(intent);
    }
}