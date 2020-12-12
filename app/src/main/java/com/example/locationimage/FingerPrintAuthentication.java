package com.example.locationimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class FingerPrintAuthentication extends AppCompatActivity {

    TextView Status;
    Button Authenticate;

    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    String TypeName;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_authentication);

        Status=findViewById(R.id.Status);

        Authenticate=findViewById(R.id.Authenticate);

        TypeName=getIntent().getStringExtra("TextName");
        builder = new AlertDialog.Builder(this);

        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(FingerPrintAuthentication.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                Status.setText("Auth Error"+errString+errorCode);

                if(errorCode==BiometricPrompt.ERROR_HW_UNAVAILABLE)
                {
                    builder.setMessage("This Device Doesn't Support FingerPrint, You cant acccess the application")
                            .setTitle("No FingerPrint For Device")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    Toast.makeText(getApplicationContext(),"",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("No FingerPrint For Device");
                    alert.show();
                }
                else if(errorCode==BiometricPrompt.ERROR_NO_BIOMETRICS)
                {
                    builder.setMessage("You Dont have FringerPrint, if you want to access this Application need to add FingerPrint in your device")
                            .setTitle("Add Finger Print")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    Toast.makeText(getApplicationContext(),"",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Add Finger Print");
                    alert.show();
                }

                //Toast.makeText(getApplicationContext(),"AutheError",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Status.setText("Success");

                Intent I=new Intent(FingerPrintAuthentication.this,HomeActivity.class);
                I.putExtra("MyValues",TypeName);
                startActivity(I);
                finish();
                Toast.makeText(getApplicationContext(),"Auth Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Status.setText("Auth Failed");

                // Toast.makeText(getApplicationContext(),"Authe Failed",Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("BioMertic Auth").setSubtitle("Finger Print Auth").setNegativeButtonText("Use App Password").build();

        Authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);

            }
        });

    }
}