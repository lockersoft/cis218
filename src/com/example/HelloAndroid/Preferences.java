package com.example.HelloAndroid;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 * User: lockersoft
 * Date: 12/18/13
 * Time: 10:14 AM
 */
public class Preferences extends BaseActivity {

  EditText edtDoctorEmail;
  Button setPrefs;

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.preferences );
    initializeApp();
  }

  private void initializeApp() {
    setPrefs = (Button)findViewById( R.id.savePrefs );
    edtDoctorEmail = (EditText)findViewById( R.id.editTextDrEmail );
    edtDoctorEmail.setText( DOCTOR_EMAIL );
  }

  public void savePrefsOnClick(View v ){
    DOCTOR_EMAIL = edtDoctorEmail.getText().toString();
    savePreferences();
    toastIt( "Preferences have been saved." );
  }

}
