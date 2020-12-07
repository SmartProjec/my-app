package com.example.locationimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String UserEmail;

    TextView Signout;
    android.widget.Button Type1,Type2,Type3;
    Intent I;

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
                // startActivity(new Intent(HomeActivity.this,LoginActivity.class));

                Toast.makeText(getApplicationContext(),"Logout Success",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Type1:
                I =new Intent(MainActivity.this,HomeActivity.class);
                I.putExtra("Cat","Caterogry1");
                I.putExtra("TextName","Personal Photo");
                startActivity(I);
                break;
            case R.id.Type2:
                I=new Intent(MainActivity.this,HomeActivity.class);
                I.putExtra("Cat","Caterogry2");
                I.putExtra("TextName","Office Photo");

                break;
            case R.id.Type3:
                I=new Intent(MainActivity.this,HomeActivity.class);
                I.putExtra("Cat","Caterogry3");
                I.putExtra("TextName","Important Photo");

                break;
        }
    }
}