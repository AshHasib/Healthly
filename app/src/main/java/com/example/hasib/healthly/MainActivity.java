package com.example.hasib.healthly;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText txtName,txtHFeet,txtHInches,txtWeight;
    private String name,heightI,heightF,weight,dob;
    private Button btnDob,btnCalc;
    DatePickerDialog.OnDateSetListener dListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createObjects();

        btnCalc.setOnClickListener(v->{
            Log.d("Inside btnCalc______________","Positive");
            if(allOk()){
                Log.d("Logic Passed_______________","Positive");
                try {
                    name=txtName.getText().toString();
                    int maxHeartRate=maxHeartRate();
                    int age=getAge();
                    double bmi=calculateBmi();
                    String status=getHealthStatus();


                    Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                    intent.putExtra("Name",name);
                    intent.putExtra("Age",age);
                    intent.putExtra("Bmi",bmi);
                    intent.putExtra("maxHeartRate",maxHeartRate);
                    intent.putExtra("status",status);

                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Please give all the information",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void createObjects(){

        txtHFeet=(EditText)findViewById(R.id.txtHFeet);
        txtHInches=(EditText)findViewById(R.id.txtHInches);
        txtName=(EditText)findViewById(R.id.txtName);
        txtWeight=(EditText)findViewById(R.id.txtWeight);
        btnCalc=(Button)findViewById(R.id.btnCalculate);
        btnDob=(Button)findViewById(R.id.btnDob);
        btnDob.setOnClickListener(v->{
            Calendar cal= Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog=new DatePickerDialog(
                    MainActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dListener,
                    year,month,dayOfMonth
            );
            dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
            dialog.show();
        });


        dListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnDob.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
    }

    public String getHealthStatus(){
        double bmi=calculateBmi();
        if(bmi<=18.5){
            return "You are underweight";
        }
        else if(bmi>18.5 && bmi <=24.9){
            return "Your health status is normal";
        }
        else if(bmi>24.9 && bmi<29.9){
            return "You are overweight";
        }
        else{
            return "You are obese. Please maintain your health";
        }
    }

    public double calculateBmi(){
        heightF=txtHFeet.getText().toString();
        heightI=txtHInches.getText().toString();
        weight=txtWeight.getText().toString();

        double feet=Double.parseDouble(heightF);
        double inches=Double.parseDouble(heightI);
        double w=Double.parseDouble(weight);
        double height=((feet*12)+inches)*0.0254;

        Log.d("Weight:______________",""+w);
        Log.d("Height:_______________",""+height);
        return (w/(height*height));
    }

    public int maxHeartRate() throws ParseException{
        int age=getAge();
        return (220-age);
    }
    public int getAge()  throws ParseException {
        Date d=new Date();
        dob=btnDob.getText().toString();
        DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        d=format.parse(dob);

        int age=(int)((System.currentTimeMillis()-d.getTime())/86400000)/365;
        return age;
    }

    public boolean allOk(){
        boolean flag=true;
        Log.d("Inside allOk()","Positive");
        Log.d("Name______________","value:"+txtName.getText().toString());
        if(txtName.getText().toString().equals("")) flag=false;
        Log.d("Pass1","flag"+flag);
        if(txtWeight.getText().toString().equals(""))flag=false;
        Log.d("Pass2","flag"+flag);
        if(txtHFeet.getText().toString().equals(""))flag=false;
        Log.d("Pass3","flag"+flag);
        if(txtHInches.getText().toString().equals(""))flag=false;
        Log.d("Pass4","flag"+flag);
        if(btnDob.getText().toString().equals("Tap here!!!")) flag=false;
        Log.d("Pass5","flag"+flag);



        return flag;
    }
}
