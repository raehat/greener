package com.example.greener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    ArrayList<DataModel> dataholder;
    Context context;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String imageURL;

    public myadapter(Context context, ArrayList<DataModel> dataholder) {
        this.dataholder = dataholder;
        this.context= context;
    }

    public myadapter(List<DataModel> addList) {
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {
        holder.name.setText(dataholder.get(position).getName());
        holder.des.setText(dataholder.get(position).getDes());
        holder.loc.setText(dataholder.get(position).getLoc());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("images/" + dataholder.get(position).getName());

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageURL = uri.toString();
                Glide.with(context).load(imageURL).into(holder.img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name, des, loc;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.t);
            des=itemView.findViewById(R.id.t1);
            loc= itemView.findViewById(R.id.t3);
        }
    }
}