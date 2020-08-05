package com.example.smarthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class access_frag extends Fragment {



    public static access_frag newInstance(String param1, String param2) {
        access_frag fragment = new access_frag();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final boolean[] isClickable = {true};

        View view = inflater.inflate(R.layout.fragment_access_frag, container, false);
        SharedPreferences pref=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        if(pref.getString("access_key",null)!=null)
            Toast.makeText(getActivity(), "you already have access", Toast.LENGTH_SHORT).show();
        final ProgressBar progressBar=(ProgressBar)view.findViewById(R.id.progressBar4);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);
        final EditText key = (EditText) view.findViewById(R.id.key);
        Button button=(Button)view.findViewById(R.id.access);
        if(isClickable[0])
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                isClickable[0] =false;
                progressBar.setVisibility(View.VISIBLE);
                AsyncHttpClient client=new AsyncHttpClient();
                String url="https://smarthome9875.herokuapp.com/checkAccessKey?access_key="+key.getText().toString();
                RequestParams params=new RequestParams();
                JsonHttpResponseHandler responseHandler=new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        isClickable[0] =true;
                        progressBar.setVisibility(View.GONE);
                        if(statusCode==200)
                        {
                            Toast.makeText(getActivity(), "you have access to app", Toast.LENGTH_SHORT).show();
                            SharedPreferences pref=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit=pref.edit();
                            edit.putString("access_key",key.getText().toString());
                            edit.commit();
                            key.setText("", TextView.BufferType.EDITABLE);

                        }
                        else
                            Toast.makeText(getActivity(), "Invalid access key", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        isClickable[0] =true;
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Unable to verify access key", Toast.LENGTH_SHORT).show();
                    }
                };
                client.get(url,responseHandler);


            }
        });

        return view;

    }
}