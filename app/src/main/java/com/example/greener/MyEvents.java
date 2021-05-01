package com.example.greener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.security.AccessController.getContext;

public class MyEvents extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataModel> dataholder;
    FirebaseFirestore firestore;
    private List<String> list;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Events");
        recyclerView= findViewById(R.id.rec_view_profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyEvents.this));
        dataholder=new ArrayList<>();

        firestore= FirebaseFirestore.getInstance();
        progressDialog= new ProgressDialog(MyEvents.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();

        DocumentReference documentReference= firestore.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String eventName= documentSnapshot.getString("eventName");
                if (eventName==null)
                {
                    Toast.makeText(MyEvents.this, "No events planned", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                list= Arrays.asList(eventName.split(",", 0));
                for (String s: list)
                {
                    firestore.collection("events").document(s)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            DataModel dataModel= new DataModel(s, documentSnapshot.getString("description"), documentSnapshot.getString("location"));
                            dataholder.add((dataModel));
                            if (list.size()==dataholder.size()){
                                //Toast.makeText(MyEvents.this, "" + dataholder.size(), Toast.LENGTH_SHORT).show();
                                recyclerView.setAdapter(new myadapter(getApplicationContext(),dataholder));
                            }
                        }
                    });
                }
                progressDialog.dismiss();
            }
        });

        /*DataModel ob1=new DataModel(R.drawable.ic_baseline_person_24,"Angular","Web Application");
        dataholder_profile.add(ob1);



        recyclerView.setAdapter(new myadapter_profile(dataholder_profile));*/
    }
}