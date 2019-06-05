package com.example.laptop.khadamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.interfaces.VolleyCallback;
import com.example.laptop.khadamat.services.SearchUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements IPAddress {

    String urlSearchUser = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/searchUser.php";

    EditText usernameEdit;
    EditText passwordEdit;
    Button btnLogin;
    Button btnJoinUs;
    SearchUser searchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnJoinUs = (Button) findViewById(R.id.btnJoinUs);

        btnJoinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CreateUser.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                searchUser = new SearchUser(urlSearchUser, LoginActivity.this);
                searchUser.search(username, password, LoginActivity.this, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) throws JSONException {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            JSONArray userJsonArray = jsonObject.getJSONArray("user");
                            JSONObject userJsonObject = userJsonArray.getJSONObject(0);

                            /** get user type **/
                            String userType = userJsonObject.getString("userType");

                            /** shared Preferencies **/
                            SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putString("idUser", userJsonObject.getString("idUser"));
                            editor.putString("fullName", userJsonObject.getString("fullName"));
                            editor.putString("email", userJsonObject.getString("email"));
                            editor.putString("username", userJsonObject.getString("username"));
                            editor.putString("password", userJsonObject.getString("password"));
                            editor.putString("userType", userJsonObject.getString("userType"));
                            editor.putString("phoneNumber", userJsonObject.getString("phoneNumber"));
                            editor.putString("city", userJsonObject.getString("city"));
                            editor.putString("scheduleDays", userJsonObject.getString("scheduleDays"));
                            editor.putString("scheduleHours", userJsonObject.getString("scheduleHours"));
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, ToDo.class);
                            startActivity(intent);


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Username or Password is Not Correct",
                                    Toast.LENGTH_LONG).show();
                        }



                    }
                });



            }

        });

    }
}
