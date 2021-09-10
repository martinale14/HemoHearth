package com.example.hemohearth;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> fireData = new HashMap<>();
        fireData.put("first", "martin");
        fireData.put("last", "martin");

        db.collection("pacientes").document("id").set(fireData);*/

    }

    public void openDiabetes(View view){
        Intent intent = new Intent(this, Diabetes.class);
        intent.putExtra("fragment", "diabetes");
        startActivity(intent);
    }

    public void openAnemia(View view){
        Intent intent = new Intent(this, Diabetes.class);
        intent.putExtra("fragment", "anemia");
        startActivity(intent);
    }
}