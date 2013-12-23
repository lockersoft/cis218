package com.example.HelloAndroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: dljones
 * Date: 11/6/13
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends Activity {

  public String FILENAME = "";
  public static final String EXT_FOLDERNAME = "/HealthApp/";
  public static final String PREFS_NAME = "HealthAppPrefs";
  public static final String DEF_DOCTOR = "dave@lockersoft.com";
  public String DOCTOR_EMAIL = "";

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );

    // Restore preferences
    SharedPreferences settings = getSharedPreferences( PREFS_NAME, 0 );
    // Can also use getPreferences which does not require a filename
    DOCTOR_EMAIL = settings.getString("doctorEmail", DEF_DOCTOR);

    FILENAME = getString( R.string.weightFileName );
  }

  @Override
  protected void onStop(){
    super.onStop();
    savePreferences();
  }

  public void savePreferences(){
    // We need an Editor object to make preference changes.
    // All objects are from android.context.Context
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("doctorEmail", DOCTOR_EMAIL );

    // Commit the edits!
    editor.commit();
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu ) {
    getMenuInflater().inflate( R.menu.mastermenu, menu );
    return true;
  }

  @Override
  public boolean onOptionsItemSelected( MenuItem item ) {
    switch( item.getItemId() ) {

      case R.id.switchToBMI:
        startActivity( new Intent( this, BMI.class ) );
        break;
      case R.id.switchToLog:
        switchToLogger( null );
        break;
      case R.id.switchToAlarm:
        startActivity( new Intent( this, MedicineAlarm.class ) );
        break;
      case R.id.switchToChart:
        startActivity( new Intent( this, Chart.class ) );
        break;
      case R.id.switchToPreferences:
        startActivity( new Intent( this, Preferences.class ) );
        break;
      case R.id.switchToMap:
        startActivity( new Intent( this, Map.class ) );
        break;
      default:
        return super.onOptionsItemSelected( item );
    }
    return true;
  }

  public void switchToLogger( View v ) {
    Intent weightLogger = new Intent( this, WeightLogger.class );
    weightLogger.putExtra( "weight", BMI.weightIn.getText().toString() );
    weightLogger.putExtra( "height", BMI.heightIn.getText().toString() );
    startActivity( weightLogger );
  }

  public  File copyFileToExternal(String fileName) {
    File file = null;
    String newPath = Environment.getExternalStorageDirectory() + EXT_FOLDERNAME;
    try {
      File f = new File(newPath);
      f.mkdirs();
      FileInputStream fin = openFileInput(fileName);
      FileOutputStream fos = new FileOutputStream(newPath + fileName);
      byte[] buffer = new byte[1024];
      int len1 = 0;
      while ((len1 = fin.read(buffer)) != -1) {
        fos.write(buffer, 0, len1);
      }
      fin.close();
      fos.close();
      file = new File(newPath + fileName);
      if (file.exists())
        return file;
    } catch (Exception e) {
      toastIt( "HELP!" );
    }
    return null;
  }


  public void toastIt( String msg ) {
    Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT ).show();
  }
}
