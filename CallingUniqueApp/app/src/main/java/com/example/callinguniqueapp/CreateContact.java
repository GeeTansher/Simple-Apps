package com.example.callinguniqueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateContact extends AppCompatActivity implements View.OnClickListener{

    EditText etname,etweb,etphone,etloaction;
    ImageView ivhappy,ivok,ivsad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        etname=findViewById(R.id.etname);
        etweb=findViewById(R.id.etweb);
        etloaction=findViewById(R.id.etlocation);
        etphone=findViewById(R.id.etphone);
        ivhappy=findViewById(R.id.ivhappy);
        ivok=findViewById(R.id.ivok);
        ivsad=findViewById(R.id.ivsad);

        ivsad.setOnClickListener(this);
        ivhappy.setOnClickListener(this);
        ivok.setOnClickListener(this);

    }
    // instead of writing three different functions of on click listener write only one function as
    // all three have to do same thing that is return to main activity so why not implement func once
    // so write below function and implements onclicklistener class above....
    @Override
    public void onClick(View view) {

        if(etname.getText().toString().isEmpty() || etphone.getText().toString().isEmpty() ||
                etloaction.getText().toString().isEmpty() || etweb.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent();
            intent.putExtra("name",etname.getText().toString().trim());
            intent.putExtra("number",etphone.getText().toString().trim());
            intent.putExtra("web",etweb.getText().toString().trim());
            intent.putExtra("location",etloaction.getText().toString().trim());

            if(view.getId()==R.id.ivhappy)
            {
                intent.putExtra("mood","happy");
            }
            else if(view.getId()==R.id.ivok)
            {
                intent.putExtra("mood","ok");
            }
            else
            {
                intent.putExtra("mood","sad");
            }

            setResult(RESULT_OK,intent);
            CreateContact.this.finish();

        }
    }
}