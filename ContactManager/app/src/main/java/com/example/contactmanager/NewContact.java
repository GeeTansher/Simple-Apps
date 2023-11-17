package com.example.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.contactmanager.model.ContactRoom;
import com.example.contactmanager.model.ContactViewModel;

public class NewContact extends AppCompatActivity {

    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation_reply";
    private EditText etName;
    private EditText etOccupation;
    private Button btnSave;
    private Button btnDelete;
    private Button btnUpdate;
    private int contactId = 0;
    private ContactViewModel contactViewModel;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        btnSave = findViewById(R.id.btnSave);
        etName = findViewById(R.id.etName);
        etOccupation = findViewById(R.id.etOccupation);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this
                .getApplication())
                .create(ContactViewModel.class);


        if (getIntent().hasExtra(MainActivity.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID, 0);

            contactViewModel.get(contactId).observe(this, contactRoom -> {
                if (contactRoom != null) {
                    etName.setText(contactRoom.getName());
                    etOccupation.setText(contactRoom.getOccupation());
                }
            });
            isEdit = true;
        }

        btnSave.setOnClickListener(v -> {

            Intent replyIntent = new Intent();
            if (!TextUtils.isEmpty(etName.getText()) &&
                    !TextUtils.isEmpty(etOccupation.getText())) {

                replyIntent.putExtra(NAME_REPLY, etName.getText().toString().trim());
                replyIntent.putExtra(OCCUPATION, etOccupation.getText().toString().trim());
                setResult(RESULT_OK, replyIntent);
            } else {
                setResult(RESULT_CANCELED, replyIntent);
//                Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show();
            }
            finish();
        });


        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> edit(false));

        /*
             Todo:We can also delete by only id as if user decides to delete in between some
              alterations in contact, so see to it if possible...
        */

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> edit(true));

        if (isEdit) {
            btnSave.setVisibility(View.GONE);
        } else {
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

    }

    private void edit(Boolean isDelete) {
        String name = etName.getText().toString().trim();
        String occupation = etOccupation.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)) {
            Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show();
        } else {
            ContactRoom contactRoom = new ContactRoom();
            contactRoom.setId(contactId);
            contactRoom.setName(name);
            contactRoom.setOccupation(occupation);

            if (isDelete) {
                ContactViewModel.delete(contactRoom);
            } else {
                ContactViewModel.update(contactRoom);
            }
            finish();
        }
    }
}