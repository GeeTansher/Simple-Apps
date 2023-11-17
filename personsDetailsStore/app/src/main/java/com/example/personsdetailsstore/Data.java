package com.example.personsdetailsstore;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Data extends AppCompatActivity {

    TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        tvData=findViewById(R.id.tvData);
        try
        {
            ContactsDB db = new ContactsDB(this);
            db.open();
            tvData.setText("\tDATA...\n" + db.getData());
            db.close();
        }
        catch (SQLException e)
        {
            Toast.makeText(Data.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}