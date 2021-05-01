package com.example.greener;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,container,false);

        firestore= FirebaseFirestore.getInstance();

        TextView points= view.findViewById(R.id.points);
        redeem= view.findViewById(R.id.redeem);

        firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                points.setText(documentSnapshot.getString("points"));
            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user= new HashMap<>();
                user.put("points", "0");
                firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .update(user);
                points.setText("0");
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
