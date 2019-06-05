package com.example.laptop.khadamat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laptop.khadamat.R;
import com.example.laptop.khadamat.ServiceDetails;
import com.example.laptop.khadamat.entities.Service;
import com.example.laptop.khadamat.entities.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterServices extends RecyclerView.Adapter<RecyclerAdapterServices.ViewHolder> {

    private List<Service> services;
    private Context context;

    public RecyclerAdapterServices(List<Service> services, Context context) {
        this.services = services;
        this.context = context;
    }

    public void setFilter(List<Service> sl) {
        services = new ArrayList<>();
        services.addAll(sl);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView idService;
        public TextView idUser;
        public TextView fullName;
        public TextView serviceName;
        public TextView experience;
        public TextView rate;


        public ViewHolder(View itemView) {
            super(itemView);
            idService = (TextView)itemView.findViewById(R.id.idService);
            idUser = (TextView)itemView.findViewById(R.id.idUser);
            fullName = (TextView)itemView.findViewById(R.id.fullName);
            serviceName = (TextView)itemView.findViewById(R.id.serviceName);
            experience = (TextView)itemView.findViewById(R.id.experience);
            rate = (TextView)itemView.findViewById(R.id.rate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ServiceDetails.class);
                    intent.putExtra("service", services.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.service_card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.idService.setText(services.get(i).getIdService());
        viewHolder.idUser.setText(services.get(i).getUser().getIdUser());
        viewHolder.fullName.setText(services.get(i).getUser().getFullName());
        viewHolder.serviceName.setText(services.get(i).getServiceName());
        viewHolder.experience.setText("experience : "+services.get(i).getExperience());
        viewHolder.rate.setText("rate : "+services.get(i).getRate());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }


}
