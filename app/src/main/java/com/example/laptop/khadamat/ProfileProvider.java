package com.example.laptop.khadamat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.services.UpdateAvailability;
import com.example.laptop.khadamat.services.UpdateUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProfileProvider extends AppCompatActivity implements IPAddress {

    TextView fullNameText;
    TextView emailText;
    TextView phoneNumber;
    TextView cityText;
    TextView avaDaysText;
    TextView avaScheduleText;

    CardView cardAvailability;

    Button btnUpdate;
    Button btnBack;

    String urlUpdateUser = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/updateUser.php";
    String urlUpdateAvailability = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/updateAvailability.php";

    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_provider);

        sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        final User user = new User();
        user.setIdUser(sharedPrefs.getString("idUser", null));
        user.setFullName(sharedPrefs.getString("fullName", null));
        user.setEmail(sharedPrefs.getString("email", null));
        user.setPhoneNumber(sharedPrefs.getString("phoneNumber", null));
        user.setCity(sharedPrefs.getString("city", null));
        user.setScheduleDays(sharedPrefs.getString("scheduleDays", null));
        user.setScheduleHours(sharedPrefs.getString("scheduleHours", null));
        user.setUsername(sharedPrefs.getString("username", null));
        user.setPassword(sharedPrefs.getString("password", null));

        cardAvailability = (CardView) findViewById(R.id.cardAvailability);

        fullNameText = (TextView) findViewById(R.id.fullNameText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneNumber = (TextView) findViewById(R.id.phoneNumberText);
        cityText = (TextView) findViewById(R.id.cityText);
        avaDaysText = (TextView) findViewById(R.id.avaDaysText);
        avaScheduleText = (TextView) findViewById(R.id.avaScheduleText);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnBack = (Button) findViewById(R.id.btnBack);

        fullNameText.setText(user.getFullName());
        emailText.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());
        cityText.setText(user.getCity());
        avaDaysText.setText(user.getScheduleDays());
        avaScheduleText.setText(user.getScheduleHours());

        cardAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAvailabilityDialog(user);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileDialog(user);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileProvider.this, ToDo.class);
                startActivity(intent);
            }
        });

    }

    public void showProfileDialog(final User u) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.layout_custom_dialog,null, false);

        final EditText fullNameEdit = (EditText) formElementsView.findViewById(R.id.fullNameEdit);
        final EditText emailEdit = (EditText) formElementsView.findViewById(R.id.emailEdit);
        final EditText phoneNumberEdit = (EditText) formElementsView.findViewById(R.id.phoneNumberEdit);
        final EditText cityEdit = (EditText) formElementsView.findViewById(R.id.cityEdit);
        final EditText usernameEdit = (EditText) formElementsView.findViewById(R.id.usernameEdit);
        final EditText passwordEdit = (EditText) formElementsView.findViewById(R.id.passwordEdit);

        fullNameEdit.setText(u.getFullName());
        emailEdit.setText(u.getEmail());
        phoneNumberEdit.setText(u.getPhoneNumber());
        cityEdit.setText(u.getCity());
        usernameEdit.setText(u.getUsername());
        passwordEdit.setText(u.getPassword());

        new AlertDialog.Builder(ProfileProvider.this).setView(formElementsView)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        u.setIdUser(u.getIdUser());
                        u.setFullName(fullNameEdit.getText().toString());
                        u.setEmail(emailEdit.getText().toString());
                        u.setPhoneNumber(phoneNumberEdit.getText().toString());
                        u.setCity(cityEdit.getText().toString());
                        u.setUsername(usernameEdit.getText().toString());
                        u.setPassword(passwordEdit.getText().toString());

                        UpdateUser service = new UpdateUser(urlUpdateUser, ProfileProvider.this);
                        service.updateUser(u, ProfileProvider.this);

                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("idUser", u.getIdUser());
                        editor.putString("fullName", u.getFullName());
                        editor.putString("email",u.getEmail());
                        editor.putString("username", u.getUsername());
                        editor.putString("password", u.getPassword());
                        editor.putString("phoneNumber", u.getPhoneNumber());
                        editor.putString("city", u.getCity());
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Your Profile Has Been Updated",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ProfileProvider.this, ProfileProvider.class);
                        startActivity(intent);
                    }
                })
                .show();

    }

    public void showAvailabilityDialog(final User u) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.layout_custom_availability,null, false);

        final CheckBox avaMonday = (CheckBox) formElementsView.findViewById(R.id.avaMonday);
        final CheckBox avaTuesday = (CheckBox) formElementsView.findViewById(R.id.avaTuesday);
        final CheckBox avaWednesday = (CheckBox) formElementsView.findViewById(R.id.avaWednesday);
        final CheckBox avaThursday = (CheckBox) formElementsView.findViewById(R.id.avaThursday);
        final CheckBox avaFriday = (CheckBox) formElementsView.findViewById(R.id.avaFriday);
        final CheckBox avaSaturday = (CheckBox) formElementsView.findViewById(R.id.avaSaturday);
        final CheckBox avaSunday = (CheckBox) formElementsView.findViewById(R.id.avaSunday);

        final EditText fromEdit = (EditText) formElementsView.findViewById(R.id.fromEdit);
        final EditText toEdit = (EditText) formElementsView.findViewById(R.id.toEdit);

        if(u.getScheduleDays().equals("Not Specified") == false) {

            List<String> days = new ArrayList<String>(Arrays.asList(u.getScheduleDays().split(", ")));
            for (int i = 0; i < days.size(); i++) {
                if(days.get(i).equals("Monday") == true ){
                    avaMonday.setChecked(true);
                }
                if(days.get(i).equals("Tuesday") == true ){
                    avaTuesday.setChecked(true);
                }
                if(days.get(i).equals("Wednesday") == true ){
                    avaWednesday.setChecked(true);
                }
                if(days.get(i).equals("Thursday") == true ){
                    avaThursday.setChecked(true);
                }
                if(days.get(i).equals("Friday") == true ){
                    avaFriday.setChecked(true);
                }
                if(days.get(i).equals("Saturday") == true ){
                    avaSaturday.setChecked(true);
                }
                if(days.get(i).equals("Sunday") == true ){
                    avaSunday.setChecked(true);
                }
            }
        }else {

        }

        if(u.getScheduleHours().equals("Not Specified") == false) {

            List<String> hours = new ArrayList<String>(Arrays.asList(u.getScheduleHours().split("-")));
            fromEdit.setText(hours.get(0));
            toEdit.setText(hours.get(1));
        }else {

        }

        new AlertDialog.Builder(ProfileProvider.this).setView(formElementsView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String scheduleDays = "";
                        String scheduleHours = "";
                        if(avaMonday.isChecked()) {
                            scheduleDays += "Monday, ";
                        }
                        if(avaTuesday.isChecked()) {
                            scheduleDays += "Tuesday, ";
                        }
                        if(avaWednesday.isChecked()) {
                            scheduleDays += "Wednesday, ";
                        }
                        if(avaThursday.isChecked()) {
                            scheduleDays += "Thursday, ";
                        }
                        if(avaFriday.isChecked()) {
                            scheduleDays += "Friday, ";
                        }
                        if(avaSaturday.isChecked()) {
                            scheduleDays += "Saturday, ";
                        }
                        if(avaSunday.isChecked()) {
                            scheduleDays += "Sunday, ";
                        }

                        if(scheduleDays.equals("")) {
                            scheduleDays = "Not Specified";
                        }else {
                            /** delete ',' which is the last character from schedule **/
                            scheduleDays = scheduleDays.substring(0, scheduleDays.length() - 2);
                        }
                        scheduleHours = fromEdit.getText()+"-"+toEdit.getText();

                        u.setScheduleDays(scheduleDays);
                        u.setScheduleHours(scheduleHours);

                        UpdateAvailability s = new UpdateAvailability(urlUpdateAvailability, ProfileProvider.this);
                        s.updateSchedule(u, ProfileProvider.this);

                        SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("scheduleDays", u.getScheduleDays());
                        editor.putString("scheduleHours", u.getScheduleHours());
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Availability Has Been Updated",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ProfileProvider.this, ProfileProvider.class);
                        startActivity(intent);

                    }
                })
                .show();
    }

}
