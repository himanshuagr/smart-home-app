package com.example.smarthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link switchFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class switchFrag extends Fragment {



    public switchFrag() {
        // Required empty public constructor
    }


    public static switchFrag newInstance(String param1, String param2) {
        switchFrag fragment = new switchFrag();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_switch, container, false);
        SharedPreferences pref=getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        if(pref.getString("access_key",null)==null)
            Toast.makeText(getActivity(), "you can not switch until access key is provided", Toast.LENGTH_SHORT).show();
        return view;
    }
}