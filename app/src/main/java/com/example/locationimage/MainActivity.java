package com.example.locationimage;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String UserEmail;

    TextView Signout;
    android.widget.Button Type1,Type2,Type3;
    Intent I;
    String getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Signout=findViewById(R.id.Signout);

        Type1=findViewById(R.id.Type1);
        Type2=findViewById(R.id.Type2);
        Type3=findViewById(R.id.Type3);

        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UserEmail = sharedPreferences.getString("Email", "");

        Signout.setOnClickListener(this);
        Type1.setOnClickListener(this);
        Type2.setOnClickListener(this);
        Type3.setOnClickListener(this);

        permissionMethod();
    }

    private void permissionMethod() {
        Dexter.withContext(MainActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                /* ... */
                report.areAllPermissionsGranted();


            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                /* ... */

                token.continuePermissionRequest();

            }
        }).check();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.Signout:
                sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();

                editor.putInt("Key",0);
                editor.clear();
                editor.apply();
                finish();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                finish();
                Toast.makeText(getApplicationContext(),"Logout Success",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Type1:
                I =new Intent(MainActivity.this,FingerPrintAuthentication.class);
                I.putExtra("TextName","Personal Photo");
                startActivity(I);
                break;
            case R.id.Type2:
                I=new Intent(MainActivity.this,FingerPrintAuthentication.class);
                I.putExtra("TextName","Office Photo");
                startActivity(I);
                break;
            case R.id.Type3:
                I=new Intent(MainActivity.this,FingerPrintAuthentication.class);

                I.putExtra("TextName","Important Photo");
                startActivity(I);

                break;
        }
    }
}