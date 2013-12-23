package com.example.HelloAndroid;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * User: lockersoft
 * Date: 12/18/13
 * Time: 12:57 PM
 */
public class Map extends BaseActivity {
  private WebView webView;
  LocationManager locationManager;
  Spinner buildingSpinner;
  String[] buildingArray;

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.map );
    locationManager = (LocationManager)this.getSystemService( Context.LOCATION_SERVICE );
    webView = (WebView)findViewById( R.id.webView );
    webView.setInitialScale( 1 );
//    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setSupportZoom( true );
    webView.getSettings().setLoadWithOverviewMode( true ); // Zoom farthest out

    webView.getSettings().setUseWideViewPort( true );
    webView.getSettings().setBuiltInZoomControls( true );

    initializeApp();
  }

  private void initializeApp() {
    // storing string resources into Array
    buildingArray = getResources().getStringArray( R.array.buildings );
    buildingSpinner = (Spinner)findViewById( R.id.spinner );
    buildingSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected( AdapterView<?> arg0, View arg1, int arg2, long arg3 ) {
        int index = arg0.getSelectedItemPosition();
        toastIt( "You have selected : " + buildingArray[index] );
        positionMap( index );
      }

      public void onNothingSelected( AdapterView<?> arg0 ) {
      }

    });

    webView.loadUrl( "file:///android_asset/sccmap.html" );

//    webView.setWebChromeClient(new WebChromeClient() {
//      public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
//        callback.invoke(origin, true, false);
//      }
//    });
  }

  // Original Picture width = 4415
  // Original Picture height = 3306
  private void positionMap( Integer index ) {
    Log.d( "MAP Y", "" + webView.getContentHeight() );
    Log.d( "MAP X", "" + webView.getWidth() );
    Log.d( "MAP wx", "" + webView.getScrollX());
    Log.d( "MAP wy", "" + webView.getScrollX());

    switch( index ) {
      case 0:  // Building 1
        zoomInTimes( 5 );
        webView.setScrollX( 700 );
        webView.setScrollY( 2500 );
        break;

      case 1:  // Building 50
        zoomInTimes( 5 );
        webView.scrollTo( 0, 0 );
        break;

      default:
        break;
    }
  }

  private void zoomInTimes( Integer zoomLevel ){
    for( int i=0;i<zoomLevel;i++)
      webView.zoomIn();
  }

  private void zoomOutTimes( Integer zoomLevel ){
    for( int i=0;i<zoomLevel;i++)
      webView.zoomOut();
  }
}
