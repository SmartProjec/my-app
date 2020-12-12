package com.example.locationimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DisplayDataActivity extends AppCompatActivity {

    ImageView DisplayImage;
    TextView DisplayAdd,Document;
    String img,Add,MyDoc,TypeName;
    Button Delete;

    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        DisplayImage=findViewById(R.id.DisplayImage);
        DisplayAdd=findViewById(R.id.DisplayAddress);
        Document=findViewById(R.id.Doc);
        Delete=findViewById(R.id.Delete);

        img=getIntent().getStringExtra("Image");
        Add=getIntent().getStringExtra("Address");
        MyDoc=getIntent().getStringExtra("Document");
        TypeName=getIntent().getStringExtra("Value11");

        DisplayAdd.setText(""+Add);
        Document.setText(""+MyDoc);

        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);

        Glide.with(DisplayDataActivity.this).load(""+img).centerCrop().placeholder(R.drawable.ic_account_circle_black_24dp).into(DisplayImage);


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteMethod();

            }
        });

    }

    private void deleteMethod() {

        progressDialog.show();
        progressDialog.setMessage("Deleting File....");
        firebaseFirestore.collection("UserUploadData").document(""+MyDoc).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Successfully Deleted",Toast.LENGTH_SHORT).show();
                finish();
               /* Intent i=new Intent(DisplayDataActivity.this,HomeActivity.class);
                i.putExtra("MyValues",TypeName);

                startActivity(i);*/

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}