package com.example.hasib.healthly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView output;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle info=getIntent().getExtras();

        String name=info.getString("Name");
        int age=info.getInt("Age");
        double bmi=info.getDouble("Bmi");
        int maxHeartRate=info.getInt("maxHeartRate");
        String status=info.getString("status");

        String message="Hello "+name+", your age is "+age+", your Body Mass Index is "+bmi+", Max. Heart rate is "+maxHeartRate+", "+status;
        output=(TextView)findViewById(R.id.output);
        output.setText(message);

        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v->{
            Intent intent=new Intent(ResultActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
