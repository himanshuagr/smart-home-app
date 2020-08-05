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
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class registerActivity extends AppCompatActivity {
    EditText name,mobile,password,confirmPassword;
    boolean isClickable=true;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);


    }
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    public void register(View view)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        if(!isClickable)
            return;
        isClickable=false;
        name=(EditText)findViewById(R.id.name);
        mobile=(EditText)findViewById(R.id.mobile);
        password=(EditText)findViewById(R.id.password);
        confirmPassword=(EditText)findViewById(R.id.confirm_password);


        if(password.getText().toString().isEmpty()||mobile.getText().toString().isEmpty()||name.getText().toString().isEmpty()||confirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            Toast.makeText(this, "password not matching", Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncHttpClient client=new AsyncHttpClient();
        String url="https://smarthome9875.herokuapp.com/userRegister";
        RequestParams params=new RequestParams();
        params.add("name",name.getText().toString());
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
                    Toast.makeText(registerActivity.this, "user registered successfully", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref=getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
                    SharedPreferences.Editor edit=pref.edit();
                    edit.putBoolean("LoggedIn",true);
                    edit.putString("x-auth-token",headers[3].toString());
                    edit.commit();
                    Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(intent);
                }
                else

                Toast.makeText(registerActivity.this, "unable to register", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(registerActivity.this, "unable to register", Toast.LENGTH_SHORT).show();
            }
        };
        client.post(url,params,responseHandler);
    }




}