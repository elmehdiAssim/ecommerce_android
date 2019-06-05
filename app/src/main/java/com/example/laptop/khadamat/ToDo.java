package com.example.laptop.khadamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laptop.khadamat.entities.User;

public class ToDo extends AppCompatActivity {

    Button btnProfile;
    Button btnServices;
    Button btnMessages;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnServices = (Button) findViewById(R.id.btnServices);
        btnMessages = (Button) findViewById(R.id.btnMessages);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        final User user = (User) getIntent().getSerializableExtra("user");

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDo.this, ProfileProvider.class);
                startActivity(intent);
            }
        });

        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                String userType = sharedPrefs.getString("userType", null);
                if(userType.equals("Service Provider")) {
                    Intent intent = new Intent(ToDo.this, CreateService.class);
                    startActivity(intent);
                }else if(userType.equals("Normal User")) {
                    Intent intent = new Intent(ToDo.this, ListOfServices.class);
                    startActivity(intent);
                }

            }
        });

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ToDo.this, ListOfSenders.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(ToDo.this, LoginActivity.class);
                startActivity(i);
            }
        });


    }
}
