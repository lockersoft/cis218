package com.example.HelloAndroid;

import android.app.Activity;
import android.content.Intent;
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
  public String EXT_FOLDERNAME = "/HealthApp/";

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    FILENAME = getString( R.string.weightFileName );
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
