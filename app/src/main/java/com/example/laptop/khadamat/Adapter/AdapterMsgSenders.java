package com.example.laptop.khadamat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laptop.khadamat.ListOfMessages;
import com.example.laptop.khadamat.R;
import com.example.laptop.khadamat.entities.Message;
import com.example.laptop.khadamat.entities.User;

import java.io.Serializable;
import java.util.List;

public class AdapterMsgSenders extends RecyclerView.Adapter<AdapterMsgSenders.ViewHolder> {

    private List<Message> messages;
    private Context context;

    public AdapterMsgSenders(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public AdapterMsgSenders.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_of_senders, parent, false);
        AdapterMsgSenders.ViewHolder viewHolder = new AdapterMsgSenders.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterMsgSenders.ViewHolder holder, int position) {
        holder.fullNameSender.setText(this.messages.get(position).getSender().getFullName());
        holder.idSenderText.setText(this.messages.get(position).getSender().getIdUser());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        public TextView fullNameSender;
        public TextView idSenderText;

        public ViewHolder(View itemView) {
            super(itemView);
            fullNameSender = (TextView) itemView.findViewById(R.id.fullNameSender);
            idSenderText = (TextView) itemView.findViewById(R.id.idSenderText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ListOfMessages.class);
                    intent.putExtra("idSender", idSenderText.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
