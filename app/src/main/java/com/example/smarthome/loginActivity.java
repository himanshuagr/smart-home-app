package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class loginActivity extends AppCompatActivity {

    EditText mobile,password;
    boolean isClickable=true;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

    }
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    public  void login(View view)
    {

        if(!isClickable)
            return;
         isClickable=false;
         progressBar.setVisibility(View.VISIBLE);
         mobile=(EditText)findViewById(R.id.mobile);
         password=(EditText)findViewById(R.id.password);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        AsyncHttpClient client=new AsyncHttpClient();
        String url="https://smarthome9875.herokuapp.com/userLogin";
        RequestParams params=new RequestParams();
        params.add("mobile",mobile.getText().toString());
        params.add("password",password.getText().toString());
        JsonHttpResponseHandler responseHandler=new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                isClickable=true;
                progressBar.setVisibility(View.GONE);
                if(statusCode==200)
                {
                    Toast.makeText(loginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref=getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
                    SharedPreferences.Editor edit=pref.edit();
                    edit.putBoolean("LoggedIn",true);
                    edit.putString("x-auth-token",headers[3].toString());
                    edit.commit();
                    Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(intent);
                }
                else
                Toast.makeText(loginActivity.this, "Unable to Login", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                isClickable=true;
                progressBar.setVisibility(View.GONE);
                Toast.makeText(loginActivity.this, "Unable to Login", Toast.LENGTH_SHORT).show();
            }
        };
        client.post(url,params,responseHandler);


    }
}