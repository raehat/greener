package com.example.greener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<DataModel> dataholder_profile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile,container,false);
        recyclerView=view.findViewById(R.id.rec_view_profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder_profile=new ArrayList<>();

        DataModel ob1=new DataModel(R.drawable.ic_baseline_person_24,"Angular","Web Application");
        dataholder_profile.add(ob1);

        DataModel ob2=new DataModel(R.drawable.ic_baseline_person_24,"C Programming","Embed Programming");
        dataholder_profile.add(ob2);

        DataModel ob3=new DataModel(R.drawable.ic_baseline_person_24,"C++ Programming","Embed Programming");
        dataholder_profile.add(ob3);

        DataModel ob4=new DataModel(R.drawable.ic_baseline_person_24,".NET Programming","Desktop and Web Programming");
        dataholder_profile.add(ob4);

        DataModel ob5=new DataModel(R.drawable.ic_baseline_person_24,"Java Programming","Desktop and Web Programming");
        dataholder_profile.add(ob5);

        recyclerView.setAdapter(new myadapter_profile(dataholder_profile));
        return view;
    }
}