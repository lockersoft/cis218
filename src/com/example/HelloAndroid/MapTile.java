package com.example.HelloAndroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerEventListener;

/**
 * Created by dljones on 1/15/14.
 */
public class MapTile extends BaseActivity {
  TileView tileView, tileView001;
  LinearLayout linearLayout;
  Spinner buildingSpinner;
  String[] buildingArray;

  @Override
  protected void onCreate( Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    linearLayout = new LinearLayout( this );
    linearLayout.setOrientation( LinearLayout.VERTICAL );
    setContentView( linearLayout );

    buildingSpinner = new Spinner( this );
    LinearLayout.LayoutParams spinnerViewLayout = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
    linearLayout.addView( buildingSpinner, spinnerViewLayout );

    // Create our TileView for overview map
    tileView = new TileView(MapTile.this);

    // Set the minimum parameters
    tileView.setSize(4415, 3306);
    tileView.addDetailLevel(1f, "tiles/1000_%col%_%row%.png", "downsamples/map.png");
    tileView.addDetailLevel(0.5f, "tiles/500_%col%_%row%.png", "downsamples/map.png");
    tileView.addDetailLevel(0.25f, "tiles/250_%col%_%row%.png", "downsamples/map.png");
    tileView.addDetailLevel(0.125f, "tiles/125_%col%_%row%.png", "downsamples/map.png");

    tileView.addTileViewEventListener(new TileView.TileViewEventListenerImplementation(){
      @Override
      public void onTap(int x, int y){
        Log.i( "MapTile", "x: " + x + " y: " + y);
      }
    });

    tileView.moveToAndCenter( 4415, 3306 );
    tileView.slideToAndCenter( 4415, 3306 );
    tileView.setScale( 0.25 );

    // Create the TileView for Building 1 details
    tileView001 = new TileView(MapTile.this);

    // Set the minimum parameters
    tileView001.setSize(3236, 2461);
    tileView001.addDetailLevel(1f, "tiles001/1000_%col%_%row%.gif", "downsamples001/map.gif");
    tileView001.addDetailLevel(0.5f, "tiles001/500_%col%_%row%.gif", "downsamples001/map.gif");
    tileView001.addDetailLevel(0.25f, "tiles001/250_%col%_%row%.gif", "downsamples001/map.gif");
    tileView001.addDetailLevel(0.125f, "tiles001/125_%col%_%row%.gif", "downsamples001/map.gif");

    tileView001.addTileViewEventListener(new TileView.TileViewEventListenerImplementation(){
      @Override
      public void onTap(int x, int y){
        Log.i( "MapTile", "x: " + x + " y: " + y);
      }
    });

    // Add the view to display it
    LinearLayout.LayoutParams tileViewLayout = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 1 );
    linearLayout.addView( tileView, tileViewLayout );

    initializeApp();
  }

  private void initializeApp(){

    buildingArray = getResources().getStringArray( R.array.buildings );
    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
        android.R.layout.simple_spinner_item, new Building[] {
        new Building( "Building 1", 1393, 2769 ),
        new Building( "Building 15", 852, 1448 ),
        new Building( "Building 50", 600, 400 )
    } );

    buildingSpinner.setAdapter(spinnerArrayAdapter);

    buildingSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected( AdapterView<?> arg0, View arg1, int arg2, long arg3 ) {
        Building b = (Building)arg0.getSelectedItem();
        toastIt( "You have selected : " + b.toString() );
        positionMap( b );
      }

      public void onNothingSelected( AdapterView<?> arg0 ) {
      }

    });

    ImageView markerA = new ImageView(this);
    markerA.setImageResource(R.drawable.marker_blue);
    markerA.setTag("Building 1");

    ImageView markerB = new ImageView(this);
    markerB.setImageResource(R.drawable.marker_blue);
    markerB.setTag("Building 15");

    ImageView markerC = new ImageView(this);
    markerC.setImageResource(R.drawable.marker_blue);
    markerC.setTag("Building 50");

    tileView.addMarker(markerA, 1393, 2769, -0.5f, -1.0f);
    tileView.addMarker(markerB, 852, 1448, -0.5f, -1.0f);
    tileView.addMarker(markerC, 600, 400, -0.5f, -1.0f);

    tileView.addMarkerEventListener(new MarkerEventListener(){
      @Override
      public void onMarkerTap( View view, int x, int y ){
        Log.d("Marker Event", "marker tag = " + view.getTag());//
        linearLayout.removeView( tileView );
        LinearLayout.LayoutParams tileViewLayout = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 1 );
        linearLayout.addView( tileView001, tileViewLayout );
//        tileView001.moveToAndCenter( 3236, 2461 );
//        tileView001.slideToAndCenter( 3236, 2461 );
        tileView001.setScale( 0.50 );
      }
    });
  }

  private void positionMap( Building b ){
      tileView.slideToAndCenter(b.x, b.y);
      tileView.setScale(1f);
  }

//  @Override
//  public void onPause(){
//    tileView.clear();
//    tileView001.clear();
//  }
//
//  @Override
//  public void onResume(){
//    tileView.resume();
//    tileView001.resume();
//  }
}
