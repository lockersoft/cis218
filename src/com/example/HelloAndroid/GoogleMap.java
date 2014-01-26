package com.example.HelloAndroid;

import android.app.FragmentTransaction;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * User: lockersoft
 * Date: 1/25/14
 * Time: 11:25 AM
 */
public class GoogleMap extends BaseActivity {

  // Google Map
  private com.google.android.gms.maps.GoogleMap googleMap;
  private MapFragment mapFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    setContentView( R.layout.googlemaps );
    mapFragment = MapFragment.newInstance();
    FragmentTransaction fragmentTransaction =
        getFragmentManager().beginTransaction();
    fragmentTransaction.add(R.id.mapContainer, mapFragment);

    fragmentTransaction.commit();
    try {
      // Loading map
      initializeMap();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * function to load map. If map is not created it will create it for you
   * */
  private void initializeMap() {
    if (googleMap == null) {
      googleMap = mapFragment.getMap();

      // check if map is created successfully or not
      if (googleMap != null) {
        // latitude and longitude
        double latitude = 47.6730355;       // Spokane Community College
        double longitude = -117.3567463;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Spokane Community College");

        // adding marker
        googleMap.addMarker(marker);
        googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( new LatLng(latitude, longitude), 15 ));

      } else {
        toastIt( "Sorry! unable to create maps" );
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    initializeMap();
  }
}
