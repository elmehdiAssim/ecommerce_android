package com.example.laptop.khadamat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.services.PostUser;

public class CreateUser extends AppCompatActivity implements IPAddress {

    String urlPostUser = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/addUser.php";
    EditText fullName;
    EditText email;
    EditText username;
    EditText password;
    Button btnRegister ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        fullName = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister );

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertUserType();
            }
        });

    }

    public void alertUserType() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.user_type,null, false);
        final RadioGroup userTypeRadioGroup = (RadioGroup) formElementsView.findViewById(R.id.userTypeRadioGroup);

        new AlertDialog.Builder(CreateUser.this).setView(formElementsView)
                .setTitle("You are a :")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int selectedId = userTypeRadioGroup.getCheckedRadioButtonId();
                        RadioButton selectedRadioButton = (RadioButton) formElementsView.findViewById(selectedId);
                        String userType = selectedRadioButton.getText().toString();

                        User user = new User();
                        user.setFullName(fullName.getText().toString());
                        user.setEmail(email.getText().toString());
                        user.setUsername(username.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setUserType(userType);

                        PostUser userService = new PostUser(urlPostUser, CreateUser.this);
                        userService.postUser(user, CreateUser.this);

                        Toast.makeText(getApplicationContext(), "Account Has Been Created",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(CreateUser.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }).show();
    }



}
