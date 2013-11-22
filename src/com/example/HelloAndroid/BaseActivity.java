package com.example.HelloAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: dljones
 * Date: 11/6/13
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends Activity {

  public String FILENAME = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FILENAME = getString(R.string.weightFileName);
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu){
    getMenuInflater().inflate(R.menu.mastermenu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected( MenuItem item ){
    switch ( item.getItemId() ){

      case R.id.switchToBMI :
        startActivity( new Intent(this, MyActivity.class));
        break;
      case R.id.switchToLog :
        switchToLogger( null );
        break;
      case R.id.switchToAlarm :
        startActivity( new Intent(this, MedicineAlarm.class));
      default:
        return super.onOptionsItemSelected( item );
    }
    return true;
  }

  public void switchToLogger( View v){
    Intent weightLogger = new Intent( this, WeightLogger.class );
    weightLogger.putExtra("weight", MyActivity.weightIn.getText().toString());
    weightLogger.putExtra("height", MyActivity.heightIn.getText().toString());
    startActivity( weightLogger );
  }

  public void toastIt( String msg ){
    Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
  }
}
