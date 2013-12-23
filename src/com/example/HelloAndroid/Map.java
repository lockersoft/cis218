package com.example.HelloAndroid;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

/**
 * User: lockersoft
 * Date: 12/18/13
 * Time: 12:57 PM
 */
public class Map extends BaseActivity {
  private WebView webView;
  LocationManager locationManager;

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.map );
    locationManager = (LocationManager)this.getSystemService( Context.LOCATION_SERVICE );
    webView = (WebView)findViewById( R.id.webView );
    webView.setInitialScale( 70 );
//    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setSupportZoom( true );
    webView.getSettings().setLoadWithOverviewMode( true );

    webView.getSettings().setUseWideViewPort(true);
    webView.getSettings().setBuiltInZoomControls( true );

    initializeApp();
  }

  private void initializeApp() {
    webView.loadUrl( "file:///android_asset/sccmap.html" );

//    webView.setWebChromeClient(new WebChromeClient() {
//      public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
//        callback.invoke(origin, true, false);
//      }
//    });
  }
}
