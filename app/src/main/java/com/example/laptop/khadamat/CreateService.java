package com.example.laptop.khadamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laptop.khadamat.entities.Service;
import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.services.PostService;

import java.util.ArrayList;
import java.util.List;

public class CreateService extends AppCompatActivity implements IPAddress{

    String url = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/addService.php";

    EditText serviceName;
    EditText description;
    Spinner experience;
    EditText rate;
    Button btnAdd;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userID = sharedPrefs.getString("idUser", null);

        serviceName = (EditText) findViewById(R.id.serviceName);
        experience = (Spinner) findViewById(R.id.experience);
        rate = (EditText) findViewById(R.id.rate);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        List<String> list = new ArrayList<String>();
        list.add("select experience ...");
        list.add("0 to 2 years");
        list.add("2 to 10 years");
        list.add("10 to 20 years");
        list.add("more than 20 years");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CreateService.this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experience.setAdapter(dataAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setIdUser(userID);

                Service service = new Service();
                service.setServiceName(serviceName.getText().toString());
                service.setExperience(experience.getSelectedItem().toString());
                service.setRate(rate.getText().toString());
                service.setUser(user);

                PostService serviceService = new PostService(url, CreateService.this);
                serviceService.postService(service, CreateService.this);

                Toast.makeText(getApplicationContext(), "The Service is Created",
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
