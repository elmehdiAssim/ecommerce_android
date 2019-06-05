package com.example.laptop.khadamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.laptop.khadamat.Adapter.AdapterMsgSenders;
import com.example.laptop.khadamat.Adapter.RecyclerAdapterServices;
import com.example.laptop.khadamat.entities.Message;
import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.interfaces.VolleyCallback;
import com.example.laptop.khadamat.services.GetMessagesSenders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfSenders extends AppCompatActivity implements IPAddress {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterMsgSenders adapter;

    Button btnBack;

    List<Message> messages;
    Message message;
    User sender;
    User receiver;

    GetMessagesSenders s;
    String url = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/getMsgSenders.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_senders);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_msgSenders);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnBack = (Button) findViewById(R.id.btnBack);

        SharedPreferences sharedPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String idReceiver = sharedPrefs.getString("idUser", null);
        receiver = new User();
        receiver.setIdUser(idReceiver);

        s = new GetMessagesSenders(url, this);
        s.getMsgSenders(receiver.getIdUser(), this, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) throws JSONException {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result.toString());
                    JSONArray messagesArray = jsonObject.getJSONArray("messages");
                    messages = new ArrayList<Message>();

                    for (int i = 0; i < messagesArray.length(); i++) {
                        JSONObject messageJson = messagesArray.getJSONObject(i);
                        message = new Message();
                        sender = new User();
                        sender.setIdUser(messageJson.getString("idSender"));
                        sender.setFullName(messageJson.getString("fullName"));
                        message.setSender(sender);
                        //message.setReceiver(receiver);
                        //message.setMsgText(messageJson.getString("msgText"));
                        //message.setSendingDate(messageJson.getString("sendingDate"));
                        messages.add(message);
                    }

                    adapter = new AdapterMsgSenders(messages, ListOfSenders.this);
                    recyclerView.setAdapter(adapter);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListOfSenders.this, ToDo.class);
                startActivity(intent);
            }
        });

    }
}
