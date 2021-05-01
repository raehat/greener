package com.example.greener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class enterOTP extends AppCompatActivity {

    PinView editTextCode;
    String phoneTemp;
    String name;
    String username;
    String city;
    String pinn;
    FirebaseFirestore fstore;
    FirebaseAuth mAuth;
    private String userID;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterotp);

        getSupportActionBar().hide();
        fstore= FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        phoneTemp= getIntent().getExtras().getString("temp");
        name= getIntent().getExtras().getString("name");
        username= getIntent().getExtras().getString("email");
        city= getIntent().getExtras().getString("city");
        pinn= getIntent().getExtras().getString("pinn");

        editTextCode= findViewById(R.id.kk);

        sendVerificationCode();


        new AlertDialog.Builder(enterOTP.this)
                .setTitle("Enter the OTP")
                .setMessage("You will recieve OTP on the" + phoneTemp + " in a moment. Please enter that OTP here" +
                        "to confirm your phone number").
                setNeutralButton("OK", null).
                show();

        Button button2= (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });
    }

    private void verifySignInCode() {

        final String code = editTextCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Getting values to store
                            //here you can open new activity
                            Toast.makeText(getApplicationContext(),
                                    "Login Successful", Toast.LENGTH_LONG).show();

                            userID= mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference= fstore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            Map<String, Object> user= new HashMap<>();
                            user.put("fname", name);
                            user.put("City", city);
                            user.put("number", phoneTemp);
                            user.put("pin", pinn);
                            user.put("username", username);
                            user.put("points","0");
                            user.put("eventName","");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(enterOTP.this, "User profile created", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });


                            Toast.makeText(enterOTP.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(enterOTP.this, HomePage.class);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(enterOTP.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendVerificationCode() {


        String phone = phoneTemp;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(enterOTP.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
            Toast.makeText(enterOTP.this, "OTP on it's way!", Toast.LENGTH_SHORT).show();

        }
    };
}

