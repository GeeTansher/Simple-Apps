package com.example.callinguniqueapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView ivmood,ivphone,ivweb,ivlocation;
    Button btncreate;
    TextView tv1;
    final int CREATE_CONTACT =1;
    String name="",number="",web="",location="",mood="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivmood=findViewById(R.id.ivmood);
        ivphone=findViewById(R.id.ivphone);
        ivweb=findViewById(R.id.ivweb);
        ivlocation=findViewById(R.id.ivlocation);
        btncreate=findViewById(R.id.btncreate);
        tv1=findViewById(R.id.tv1);

        ivmood.setVisibility(View.GONE);
        ivphone.setVisibility(View.GONE);
        ivweb.setVisibility(View.GONE);
        ivlocation.setVisibility(View.GONE);
        tv1.setVisibility(View.GONE);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,
                        com.example.callinguniqueapp.CreateContact.class);
                startActivityForResult(intent,CREATE_CONTACT);

//                registerForActivityResult(,CREATE_CONTACT);
            }
        });

        ivphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        ivweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + web));
                startActivity(intent);
            }
        });

        ivlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + location));
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CREATE_CONTACT)
        {
            if(resultCode==RESULT_OK)
            {
                ivlocation.setVisibility(View.VISIBLE);
                ivphone.setVisibility(View.VISIBLE);
                ivweb.setVisibility(View.VISIBLE);
                ivmood.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);

                name=data.getStringExtra("name");
                number= data.getStringExtra("number");
                web=data.getStringExtra("web");
                location=data.getStringExtra("location");
                mood=data.getStringExtra("mood");

                tv1.setText("Name:" + name);

                if(mood.equals("happy"))
                {
                    ivmood.setImageResource(R.drawable.happy);
                }
                else if(mood.equals("sad"))
                {
                    ivmood.setImageResource(R.drawable.sad);
                }
                else
                {
                    ivmood.setImageResource(R.drawable.ok);
                }
            }
            else
            {
                Toast.makeText(this, "No Data passed through...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}