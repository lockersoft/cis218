package com.example.HelloAndroid;

import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by dljones on 1/27/14.
 */
public class GoogleMap extends BaseActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

  private com.google.android.gms.maps.GoogleMap googleMap = null;
  private MapFragment mapFragment;
  String apiKey = "AIzaSyCQrb8oTqsMCmypy7jKNr5mMDDeLdSai5I";
  Location myLocation;
  LocationClient locationClient;


  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.googlemap );
    mapFragment = MapFragment.newInstance();
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.add( R.id.mapContainer, mapFragment );
    fragmentTransaction.commit();

    String placesURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=47.6730355,-117.3567463&radius=10000&sensor=true&types=doctor%7chospital&key=" + apiKey;
    locationClient = new LocationClient( this, this, this );

    try {
      initializeMap();
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      AsyncHttpClient client = new AsyncHttpClient();
      client.get( placesURL, new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess( String response ) {
          Log.i( "PLACES", response );
        }
      } );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initializeMap() {
    if (googleMap == null) {
      googleMap = mapFragment.getMap();

      if (googleMap != null) {
        double latitude = 47.6730355;
        double longitude = -117.3567463;

        // Create a marker
        MarkerOptions marker = new MarkerOptions().position( new LatLng( latitude, longitude ) ).title( "Spokane Community College" );
        googleMap.addMarker( marker );
        googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( new LatLng( latitude, longitude ), 15 ) );
      } else {
        toastIt( "Sorry! Unable to create maps" );
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    initializeMap();
  }

  @Override
  public void onConnected( Bundle arg0 ) {
    toastIt( "Connected" );

    Location loc = locationClient.getLastLocation();
    Log.d( "XXX", "location=" + loc.toString() );
  }

  @Override
  public void onConnectionFailed( ConnectionResult arg0 ) {
    toastIt( "Connection Failed" );
  }

  @Override
  public void onDisconnected() {
    toastIt( "Disconnected" );
  }
}
