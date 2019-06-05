package com.example.laptop.khadamat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laptop.khadamat.R;
import com.example.laptop.khadamat.entities.Message;

import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.ViewHolder> {

    private List<Message> listOfMsgs;

    public AdapterMessages(List<Message> listOfMsgs) {
        this.listOfMsgs = listOfMsgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_of_messages, parent, false);
        AdapterMessages.ViewHolder viewHolder = new AdapterMessages.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.senderText.setText(listOfMsgs.get(position).getSender().getFullName());
        holder.sendingDateText.setText(listOfMsgs.get(position).getSendingDate());
        holder.msgText.setText(listOfMsgs.get(position).getMsgText());
    }

    @Override
    public int getItemCount() {
        return listOfMsgs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        public TextView senderText;
        public TextView sendingDateText;
        public TextView msgText;

        public ViewHolder(View itemView) {
            super(itemView);
            senderText = (TextView) itemView.findViewById(R.id.senderText);
            sendingDateText = (TextView) itemView.findViewById(R.id.sendingDateText);
            msgText = (TextView) itemView.findViewById(R.id.msgText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                }
            });

        }
    }
}
