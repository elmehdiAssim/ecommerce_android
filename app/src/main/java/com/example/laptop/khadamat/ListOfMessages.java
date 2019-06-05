package com.example.laptop.khadamat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.laptop.khadamat.Adapter.AdapterMessages;
import com.example.laptop.khadamat.entities.Message;
import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.interfaces.VolleyCallback;
import com.example.laptop.khadamat.services.GetMessages;
import com.example.laptop.khadamat.services.PostMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfMessages extends AppCompatActivity implements IPAddress{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterMessages adapter;

    Button btnNewMessage;
    Button btnBack;

    GetMessages s;
    List<Message> listOfMsgs;
    Message msg;
    User from;
    User to;

    String idSender;
    String idReceiver;

    String url = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/getMessages.php";
    String urlSendMessage = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/sendMessage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_messages);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_messages);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnNewMessage = (Button) findViewById(R.id.btnNewMessage);
        btnBack = (Button) findViewById(R.id.btnBack);

        SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        idReceiver = sharedPrefs.getString("idUser", null);
        idSender = getIntent().getStringExtra("idSender");

        s = new GetMessages(url, this);
        s.getMessages(idReceiver, idSender, this, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) throws JSONException {
                System.out.println(result);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result.toString());
                    JSONArray messages = jsonObject.getJSONArray("messages");
                    listOfMsgs = new ArrayList<Message>();

                    for (int i = 0; i < messages.length(); i++) {

                        JSONObject messageJson = messages.getJSONObject(i);
                        msg = new Message();
                        from = new User();
                        from.setFullName(messageJson.getString("fullName"));
                        msg.setSendingDate(messageJson.getString("sendingDate"));
                        msg.setMsgText(messageJson.getString("msgText"));
                        msg.setSender(from);
                        listOfMsgs.add(msg);
                    }
                    adapter = new AdapterMessages(listOfMsgs);
                    recyclerView.setAdapter(adapter);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

        btnNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = new Message();
                from = new User();
                to = new User();
                from.setIdUser(idReceiver);
                to.setIdUser(idSender);
                msg.setSender(from);
                msg.setReceiver(to);
                showSendMessageDialog(msg);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListOfMessages.this, ListOfSenders.class);
                startActivity(intent);
            }
        });

    }

    public void showSendMessageDialog(final Message message) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.custom_send_message,null, false);

        final EditText messageEdit = (EditText) formElementsView.findViewById(R.id.messageEdit);

        new AlertDialog.Builder(this).setView(formElementsView)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        message.setMsgText(messageEdit.getText().toString());
                        PostMessage s = new PostMessage(urlSendMessage, ListOfMessages.this);
                        s.sendMessage(message, ListOfMessages.this);
                        Intent intent = new Intent(ListOfMessages.this, ListOfMessages.class);
                        intent.putExtra("idSender", idSender);
                        startActivity(intent);
                    }
                }).show();
    }

}
