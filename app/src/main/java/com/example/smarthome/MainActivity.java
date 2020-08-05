package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SharedPreferences pref=getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
       if(pref.getBoolean("LoggedIn",false))
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
            startActivity(intent);
        }
    }
    public void login_main(View view)
    {
        Intent intent=new Intent(getApplicationContext(),loginActivity.class);
        startActivity(intent);
    }
    public void register_main(View view)
    {
        Intent intent=new Intent(getApplicationContext(),registerActivity.class);
        startActivity(intent);
    }
    int backButtonCount=0;
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}