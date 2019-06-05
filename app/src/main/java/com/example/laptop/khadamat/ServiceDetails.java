package com.example.laptop.khadamat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.khadamat.entities.Message;
import com.example.laptop.khadamat.entities.Review;
import com.example.laptop.khadamat.entities.Service;
import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.services.PostMessage;
import com.example.laptop.khadamat.services.PostReview;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceDetails extends AppCompatActivity implements IPAddress {

    TextView fullName;
    TextView serviceName;
    TextView experience;
    TextView rate;


    TextView avaDaysText;
    TextView avaScheduleText;
    TextView emailText;
    TextView phoneNumberText;
    TextView cityText;

    Button btnReview;
    Button btnMessage;
    Button btnComment;

    String urlSendMessage = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/sendMessage.php";
    String urlAddReview = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/addReview.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        fullName = (TextView) findViewById(R.id.fullName);
        serviceName = (TextView) findViewById(R.id.serviceName);
        experience = (TextView) findViewById(R.id.experience);
        rate = (TextView) findViewById(R.id.rate);


        avaDaysText = (TextView) findViewById(R.id.avaDaysText);
        avaScheduleText = (TextView) findViewById(R.id.avaScheduleText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneNumberText = (TextView) findViewById(R.id.phoneNumberText);
        cityText = (TextView) findViewById(R.id.cityText);

        btnReview = (Button) findViewById(R.id.btnReview);
        btnMessage = (Button) findViewById(R.id.btnMessage);
        btnComment = (Button) findViewById(R.id.btnComment);


        final Service service = (Service) getIntent().getSerializableExtra("service");

        SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        User userMsgSender = new User();
        userMsgSender.setIdUser(sharedPrefs.getString("idUser", null));

        final Message message = new Message();
        message.setSender(userMsgSender);
        message.setReceiver(service.getUser());

        final Review review = new Review();
        review.setIdUser(sharedPrefs.getString("idUser", null));
        review.setIdService(service.getIdService());


        fullName.setText(service.getUser().getFullName());
        serviceName.setText(service.getServiceName());
        experience.setText("exprience : "+service.getExperience());
        rate.setText("rate : "+service.getRate());
        avaDaysText.setText(service.getUser().getScheduleDays());
        avaScheduleText.setText(service.getUser().getScheduleHours());
        emailText.setText(service.getUser().getEmail());
        phoneNumberText.setText(service.getUser().getPhoneNumber());
        cityText.setText(service.getUser().getCity());

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSendMessageDialog(message);
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReviewDialog(review);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServiceDetails.this, ListOfReviews.class);
                i.putExtra("idService", service.getIdService());
                startActivity(i);
            }
        });

    }

    public void showSendMessageDialog(final Message message) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.custom_send_message,null, false);

        final EditText messageEdit = (EditText) formElementsView.findViewById(R.id.messageEdit);

        new AlertDialog.Builder(ServiceDetails.this).setView(formElementsView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        message.setMsgText(messageEdit.getText().toString());
                        PostMessage s = new PostMessage(urlSendMessage, ServiceDetails.this);
                        s.sendMessage(message, ServiceDetails.this);
                        Toast.makeText(getApplicationContext(), "Your Message is Sent",
                                Toast.LENGTH_LONG).show();
                    }
                }).show();
    }

    public void showReviewDialog(final Review r) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.custom_rating,null, false);

        final EditText reviewEdit = (EditText) formElementsView.findViewById(R.id.reviewEdit);
        final RatingBar ratingBar = (RatingBar) formElementsView.findViewById(R.id.ratingBar);

        new AlertDialog.Builder(ServiceDetails.this).setView(formElementsView)
                .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String publicationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        r.setComment(reviewEdit.getText().toString());
                        r.setStarsNumber((int) ratingBar.getRating());
                        r.setPublicationDate(publicationDate);

                        PostReview s = new PostReview(urlAddReview, ServiceDetails.this);
                        s.addReview(r, ServiceDetails.this);
                        Toast.makeText(getApplicationContext(), "Thank You For Your Comment",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

}
