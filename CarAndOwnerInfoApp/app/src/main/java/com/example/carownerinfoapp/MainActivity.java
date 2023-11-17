package com.example.carownerinfoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements carAdapter.itemClicked{

    Button btncarInfo = findViewById(R.id.btncarInfo),btnownerInfo = findViewById(R.id.btnownerInfo);
    ImageView ivmake;
    TextView tvmodel,tvname,tvtel;
    FragmentManager fragmentManager;
    Fragment buttonFrag,listFrag,carInfoFrag,ownerInfoFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            tvmodel = findViewById(R.id.tvmodel);
            tvname = findViewById(R.id.tvname);
            tvtel = findViewById(R.id.tvtel);
            ivmake = findViewById(R.id.ivmake);
            fragmentManager = getSupportFragmentManager();
            listFrag = fragmentManager.findFragmentById(R.id.listfrag);
            buttonFrag = fragmentManager.findFragmentById(R.id.buttonFrag);
            carInfoFrag = fragmentManager.findFragmentById(R.id.carInfoFrag);
            ownerInfoFrag = fragmentManager.findFragmentById(R.id.ownerInfoFrag);

            fragmentManager.beginTransaction()
                    .show(buttonFrag)
                    .show(listFrag)
                    .show(carInfoFrag)
                    .hide(ownerInfoFrag)
                    .commit();


//            btncarInfo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    fragmentManager.beginTransaction()
//                            .show(carInfoFrag)
//                            .hide(ownerInfoFrag)
//                            .commit();
//
//                }
//            });

//            btnownerInfo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    fragmentManager.beginTransaction()
//                            .hide(carInfoFrag)
//                            .show(ownerInfoFrag)
//                            .commit();
//                }
//            });
            onItemClicked(0);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    // if removing this function then change setting of onclick in fragment button xml
    public void setBtncarInfo(View v)
    {
        fragmentManager.beginTransaction()
                .show(carInfoFrag)
                .hide(ownerInfoFrag)
                .commit();

    }

    @Override
    public void onItemClicked(int index) {
        tvname.setText(carApplication.cars.get(index).getOwnername());
        tvtel.setText(carApplication.cars.get(index).getOwnertel());
        tvmodel.setText(carApplication.cars.get(index).getModel());
        if(carApplication.cars.get(index).getMake().equals("Volkswagon"))
        {
            ivmake.setImageResource(R.drawable.volkswagen);
        }
        else if(carApplication.cars.get(index).getMake().equals("Nissan"))
        {
            ivmake.setImageResource(R.drawable.nissan);
        }
        else if(carApplication.cars.get(index).getMake().equals("Mercedes"))
        {
            ivmake.setImageResource(R.drawable.mercedes);
        }
    }
}