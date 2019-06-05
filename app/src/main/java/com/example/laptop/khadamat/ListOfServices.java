package com.example.laptop.khadamat;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laptop.khadamat.Adapter.RecyclerAdapterServices;
import com.example.laptop.khadamat.entities.Service;
import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.interfaces.VolleyCallback;
import com.example.laptop.khadamat.services.GetServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfServices extends AppCompatActivity implements IPAddress {

    Button btnGoogleMap;
    SearchView searchView;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterServices adapter;

    Button btnBack;

    List<Service> servicesList;
    Service service;
    User u;

    GetServices s;
    String url = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/searchServices.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_services);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnGoogleMap = (Button) findViewById(R.id.btnGoogleMap);
        btnBack = (Button) findViewById(R.id.btnBack);

        s = new GetServices(url, ListOfServices.this);
        s.searchServices(ListOfServices.this, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) throws JSONException {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result.toString());
                    JSONArray services = jsonObject.getJSONArray("services");
                    servicesList = new ArrayList<Service>();

                    for (int i = 0; i < services.length(); i++) {

                        JSONObject serviceJson = services.getJSONObject(i);
                        service = new Service();
                        u = new User();
                        service.setIdService(serviceJson.getString("idService"));
                        service.setServiceName(serviceJson.getString("serviceName"));
                        service.setExperience(serviceJson.getString("experience"));
                        service.setRate(serviceJson.getString("rate"));
                        u.setIdUser(serviceJson.getString("idUser"));
                        u.setFullName(serviceJson.getString("fullName"));
                        u.setEmail(serviceJson.getString("email"));
                        u.setPhoneNumber(serviceJson.getString("phoneNumber"));
                        u.setCity(serviceJson.getString("city"));
                        u.setScheduleDays(serviceJson.getString("scheduleDays"));
                        u.setScheduleHours(serviceJson.getString("scheduleHours"));
                        service.setUser(u);
                        servicesList.add(service);
                    }

                    adapter = new RecyclerAdapterServices(servicesList, ListOfServices.this);
                    recyclerView.setAdapter(adapter);

                }catch (JSONException e) {
                    System.out.println(e);
                }

            }
        });

        btnGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListOfServices.this, MapsActivity.class);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListOfServices.this, ToDo.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Service> filteredModeList = filter(servicesList, newText);
                adapter.setFilter(filteredModeList);
                return false;
            }
        });

        return true;
    }

    private List<Service> filter(List<Service> sl, String query) {

        query = query.toLowerCase();
        final List<Service> filteredModeList = new ArrayList<>();

        for(Service model:sl) {
            final String serviceName = model.getServiceName().toLowerCase();
            final String serviceProvider = model.getUser().getFullName().toLowerCase();
            if(serviceProvider.startsWith(query) || serviceName.startsWith(query)) {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }



}
