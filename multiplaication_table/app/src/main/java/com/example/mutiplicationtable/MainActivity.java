package com.example.mutiplicationtable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText n1;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n1=findViewById(R.id.n1);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(n1.getText().toString());
                textView.setText(num + " x " + 0 + " = " + num * 0 + "\n"+
                num + " x " + 1 + " = " + num + "\n"+
                num + " x " + 2 + " = " + num * 2 + "\n"+
                num + " x " + 3 + " = " + num * 3 + "\n"+
                num + " x " + 4 + " = " + num * 4 + "\n"+
                num + " x " + 5 + " = " + num * 5 + "\n"+
                num + " x " + 6 + " = " + num * 6 + "\n"+
                num + " x " + 7 + " = " + num * 7 + "\n"+
                num + " x " + 8 + " = " + num * 8 + "\n"+
                num + " x " + 9 + " = " + num * 9 + "\n"+
                num + " x " + 10 + " = " + num * 10 + "\n");
            }
        });
    }
}