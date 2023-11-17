ackage com.example.unconvertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textview;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button2);
        textview = findViewById(R.id.textView2);
        editText = findViewById(R.id.editTextTextPersonName);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "YOUR OUTPUT", Toast.LENGTH_SHORT).show();
                String a = editText.getText().toString();
                double b = Double.parseDouble(a);
                double pound = (2.025 * b);
                textview.setText("The corresponding value in pounds is: " + pound);
            }
        });
    }
//    public void calculate(View view){
//        Toast.makeText(MainActivity.this, "YOUR OUTPUT", Toast.LENGTH_SHORT).show();
//        String a = editText.getText().toString();
//        double b = Double.parseDouble(a);
//        double pound = (2.025 * b);
//        textview.setText("The corresponding value in pounds is: " + pound);
//    }
//    we can also use this function instead of above setOnClickListener func
//    only you have to paste " android:onClick="calculate " in button section of xml file or go to attributes of button and
//    search onclick and write calculate line will be added.
}