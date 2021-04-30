package com.example.greener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.greener.HomePage.usern;

public class ProfileFragment extends Fragment {

    ImageView imageView3;
    EditText eventName, location, description, latitude, longitude;
    Button submitArticle;
    FirebaseFirestore firestore;
    private Uri imageUri;
    private boolean flag=false;
    private StorageReference storageReference;
    private FirebaseStorage storage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile,container,false);

        firestore= FirebaseFirestore.getInstance();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        imageView3= view.findViewById(R.id.imageView3);
        eventName= view.findViewById(R.id.event_name);
        location= view.findViewById(R.id.location);
        description= view.findViewById(R.id.description);
        latitude= view.findViewById(R.id.latitude);
        longitude= view.findViewById(R.id.longitude);
        submitArticle= view.findViewById(R.id.submit_article);

        submitArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> object= new HashMap<>();
                Map<String, Object> object1= new HashMap<>();
                firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getString("eventName")==null){
                            object1.put("eventName", eventName.getText().toString());
                        }
                        else {
                            object1.put("eventName", documentSnapshot.getString("eventName") + "," + eventName.getText().toString());
                        }
                        firestore.collection("users").document( FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .update(object1);
                    }
                });

                object.put("eventName", eventName.getText().toString());
                object.put("location", location.getText().toString());
                object.put("description", description.getText().toString());
                object.put("latitude", latitude.getText().toString());
                object.put("longitude", longitude.getText().toString());

                if (flag){
                    uploadPicture(eventName.getText().toString());
                    object.put("imageCode", eventName.getText().toString());
                }
                firestore.collection("events").document(eventName.getText().toString())
                        .set(object);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imageView3.setImageURI(imageUri);
                flag = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


            public void uploadPicture(String str) {
        final ProgressDialog pd= new ProgressDialog(getContext());
        pd.setTitle("Image is being uploaded!");
        pd.show();
        StorageReference riversRef = storageReference.child("images/" + str);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "Upload Succesful", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "" + "Please make sure that the " +
                                "image is less than 1024 KB/ 1 MB", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent= (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    }
                });
    }

    private void choosePicture() {
        CropImage.activity()
                .setAspectRatio(414, 736)
                .start(getContext(), this);
    }
}
