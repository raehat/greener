package com.example.greener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

public class Register extends AppCompatActivity {

    EditText editTextPhone;
    CountryCodePicker ccp;
    String number;
    EditText name, email, city, pinn;
    public String codeSent;
    TextView codesent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        Button button3 = findViewById(R.id.button3);
        name= findViewById(R.id.name);
        email= findViewById(R.id.email);
        city= findViewById(R.id.city);
        pinn= findViewById(R.id.pinn);
        editTextPhone = findViewById(R.id.no);
        ccp = findViewById(R.id.ccp);
        codesent= findViewById(R.id.codesent);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtp();
            }
        });
    }
    private void getOtp() {
        number = "+" + ccp.getFullNumber() + editTextPhone.getText().toString();

        Intent intent = new Intent(getApplicationContext(), enterOTP.class);
        intent.putExtra("temp", number);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("pinn", pinn.getText().toString());
        intent.putExtra("city", city.getText().toString());
        startActivity(intent);


    }
}