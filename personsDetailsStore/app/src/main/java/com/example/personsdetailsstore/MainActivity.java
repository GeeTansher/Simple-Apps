package com.example.personsdetailsstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName,etCell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etCell=findViewById(R.id.etCell);

    }

    public void btnSubmit(View v)
    {
        String name = etName.getText().toString().trim();
        String cell = etCell.getText().toString().trim();

        try
        {
            ContactsDB db = new ContactsDB(this);
            db.open();
            if(!etName.getText().toString().trim().equals("") && !etCell.getText().toString().trim().equals(""))
            {
                db.createEntry(name,cell);
                Toast.makeText(this, "Succesfully saved", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etCell.setText("");
            }
            else
            {
                Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
            }
            db.close();

        }
        catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void btnShowData(View v)
    {
        startActivity(new Intent(this,Data.class));
    }

    public void btnEditData(View v)
    {
        try
        {
            ContactsDB db = new ContactsDB(this);
            db.open();
            // Suppose we have to change 1st one and detils as per us but you can take from user as well
            db.updateEntry("1","Geetansh Verma","723648711");
            db.close();
            Toast.makeText(this, "Succesfully saved...", Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void btnDeleteData(View v)
    {
        try
        {
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.deleteEntry("1");
            Toast.makeText(this, "Succesfully deleted!!!", Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}