package com.example.locationimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView PhotoType,TakePhoto;

    String TypeName,UserEmail;

    FirebaseFirestore firebaseFirestore;
    GridView gridView;
    List<DataModel> AllList=new ArrayList<>();
    ImageGridAdapter imageGridAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    DataModel dataModel;



    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PhotoType=findViewById(R.id.PhotoType);
        TakePhoto=findViewById(R.id.Camera);
        gridView=findViewById(R.id.ImageList);
        imageGridAdapter=new ImageGridAdapter(this,AllList);
        gridView.setAdapter(imageGridAdapter);

        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UserEmail = sharedPreferences.getString("Email", "");

        firebaseFirestore = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        TypeName=getIntent().getStringExtra("MyValues");
        PhotoType.setText(""+TypeName);

        TakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I=new Intent(HomeActivity.this,AddPhotoActivity.class);
                I.putExtra("Value1",TypeName);
                startActivity(I);
                finish();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel1=AllList.get(position);
                Intent i=new Intent(HomeActivity.this,DisplayDataActivity.class);
                i.putExtra("Address",dataModel1.getAddress());
                i.putExtra("Image",dataModel1.getImage());
                i.putExtra("Document",dataModel1.getDocument());
                i.putExtra("Value11",TypeName);
                startActivity(i);
            }
        });


    }

    private void loadData() {
        progressDialog.show();
        progressDialog.setMessage("Loading......");

        dataModel=new DataModel("defgh","fghjg",12.2345,45.12345,"asdfgh");
        AllList.add(dataModel);
        AllList.removeAll(AllList);
        firebaseFirestore.collection("UserUploadData").whereEqualTo("UserEmail",UserEmail).whereEqualTo("Category",""+PhotoType.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();

                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot ab:task.getResult())
                    {
                        String address=ab.getString("Address");
                        String image=ab.getString("UserImage");
                        Double lat=ab.getDouble("Lat");
                        Double lng=ab.getDouble("Lng");
                        String doc=ab.getId();

                        dataModel=new DataModel(image,address,lat,lng,doc);
                        AllList.add(dataModel);
                    }
                    imageGridAdapter.notifyDataSetChanged();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();


            }
        });
    }
}