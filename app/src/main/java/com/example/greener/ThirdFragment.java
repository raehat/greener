package com.example.greener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ThirdFragment extends Fragment {

    FirebaseFirestore firestore;

    Button myEvents;
    Button redeem;
    Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,container,false);

        firestore= FirebaseFirestore.getInstance();

        TextView points= view.findViewById(R.id.points);
        redeem= view.findViewById(R.id.redeem);
        logout= view.findViewById(R.id.logout);

        firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                points.setText(documentSnapshot.getString("points"));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Do you want to log out?")
                        .setMessage("Do you really want to logout from this account, you'll need to login again!")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                        }).setNegativeButton("no", null).show();
            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Redeem all points??")
                        .setMessage("When you make the decision to redeem all points, we make a donation to an orphanage." +
                                "We'll also mail you when we do make a donation and if possible, show you pictures of donation being made")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Map<String, Object> user= new HashMap<>();
                                user.put("points", "0");
                                firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .update(user);
                                points.setText("0");
                                Toast.makeText(getContext(), "All points redeemed!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO", null).show();
            }

        });

        myEvents= view.findViewById(R.id.my_events);

        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyEvents.class));
            }
        });

        return view;
    }
}
