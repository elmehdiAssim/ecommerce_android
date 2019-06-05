package com.example.laptop.khadamat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telecom.Call;

import com.example.laptop.khadamat.entities.Service;
import com.example.laptop.khadamat.entities.User;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.interfaces.VolleyCallback;
import com.example.laptop.khadamat.services.GetServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.laptop.khadamat.interfaces.IPAddress.ipaddress;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, IPAddress {

    private GoogleMap mMap;
    LocationManager locationManager;

    GetServices s;
    Service service;
    User u;
    HashMap<String, Service> markerHashMap;
    String url = "http://" + ipaddress + "/projects/khadamat_rest_api/userServices/searchServices.php";

    float zoom = 12.f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getMyLocation();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                for(Map.Entry<String, Service> entry:markerHashMap.entrySet()){
                    String key = entry.getKey();
                    if(key.equals(marker.getId())) {
                        Service serviceToPass = entry.getValue();
                        if(serviceToPass != null) {
                            Intent i = new Intent(MapsActivity.this, ServiceDetails.class);
                            i.putExtra("service", serviceToPass);
                            startActivity(i);
                        }
                    }

                }
                return false;
            }
        });

        s = new GetServices(url, this);
        s.searchServices(this, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) throws JSONException {

                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(result.toString());
                    JSONArray services = jsonObject.getJSONArray("services");
                    markerHashMap = new HashMap<String, Service>();

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
                        u.setAddress(serviceJson.getString("address"));
                        u.setScheduleDays(serviceJson.getString("scheduleDays"));
                        u.setScheduleHours(serviceJson.getString("scheduleHours"));
                        service.setUser(u);

                        // set all addresses in the map
                        Marker marker = mMap.addMarker(new MarkerOptions().position(getLocationFromAddress(MapsActivity.this, u.getAddress())).title(service.getServiceName()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLocationFromAddress(MapsActivity.this, u.getAddress())));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocationFromAddress(MapsActivity.this, u.getAddress()), zoom));

                        markerHashMap.put(marker.getId(), service);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;
    }

    public void getMyLocation(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Me")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Me")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
    }




}
