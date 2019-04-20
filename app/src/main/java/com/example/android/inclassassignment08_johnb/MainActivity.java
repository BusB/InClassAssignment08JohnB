package com.example.android.inclassassignment08_johnb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button writeToFB;
    private Button readFromFB;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText keyField;
    private EditText valueField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyField = findViewById(R.id.key_field);
        valueField = findViewById(R.id.value_field);

        FirebaseApp.initializeApp(MainActivity.this);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");


        writeToFB = findViewById(R.id.write_to_cloud);
        writeToFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.child(keyField.getText().toString()).setValue(valueField.getText().toString());
            }
        });

        readFromFB = findViewById(R.id.read_from_cloud);
        readFromFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatabaseReference childRef = myRef.child(keyField.getText().toString());
                childRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String valueInCloud = dataSnapshot.getValue(String.class);
                            valueField.setText(valueInCloud);


                        } else {
                            valueField.setText("");
                            Toast.makeText(MainActivity.this, "Key not found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
                    }

                });

            }

        });


    }


}