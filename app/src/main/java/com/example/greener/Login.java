package com.example.greener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {
    Button button;
    EditText editText2;
    CountryCodePicker ccp;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button= (Button) findViewById(R.id.button);
        editText2= (EditText) findViewById(R.id.editText);
        ccp= (CountryCodePicker) findViewById(R.id.countryCodePicker);

        getSupportActionBar().hide();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number= "+" + ccp.getFullNumber() + editText2.getText().toString();
                Intent intent= new Intent(getApplicationContext(), enterOTPforLogin.class);
                intent.putExtra("number", number);
                startActivity(intent);

            }
        });
    }
}