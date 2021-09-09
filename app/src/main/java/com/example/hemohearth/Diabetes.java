package com.example.hemohearth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class Diabetes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes);

        Bundle extras = getIntent().getExtras();

        if(extras != null){

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();

            if(extras.getString("fragment").equalsIgnoreCase("diabetes")){

                transaction.replace(R.id.fragmentContainerView2, Diabetes_Fragment.class, null);
                transaction.commit();

            }if(extras.getString("fragment").equalsIgnoreCase("diabetes")){
                


            }

        }

    }
}

