package com.example.HelloAndroid;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: dljones
 * Date: 11/12/13
 * Time: 7:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class MedicineAlarm extends BaseActivity {

  private Alarm currentAlarm;
  private ToggleButton toggleButton1;
  private EditText editNotes1;
  private EditText dateText1;
  private ToggleButton toggleButton2;
  private EditText editNotes2;
  private EditText dateText2;
  private PendingIntent pi;
  private BroadcastReceiver br;
  private AlarmManager am;
  TimePickerDialog timePicker;
  private Alarm[] alarms = new Alarm[10];
  Calendar c = Calendar.getInstance();

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.alarmsettings );
    initializeApp();
  }

  private void initializeApp() {
    am = (AlarmManager)( this.getSystemService( Context.ALARM_SERVICE ) );
    toggleButton1 = (ToggleButton)findViewById( R.id.toggleButton1 );
    editNotes1 = (EditText)findViewById( R.id.editNotes1 );
    dateText1 = (EditText)findViewById( R.id.editText1 );
    toggleButton2 = (ToggleButton)findViewById( R.id.toggleButton2 );
    editNotes2 = (EditText)findViewById( R.id.editNotes2 );
    dateText2 = (EditText)findViewById( R.id.editText2 );

    // BROADCAST RECEIVER **********************************************
    br = new BroadcastReceiver() {
      @Override
      public void onReceive( Context context, Intent intent ) {
        String notes = "";
        Bundle extras = intent.getExtras();
        if( extras != null ) {
          notes = extras.getString( "notes" );
        }
        toastIt( "Wake UP: " + notes );
        // Reschedule a new alarm if this is recurring
        createNotification( notes );
      }
    };
    // Register the receiver and create the intents for passing information
    registerReceiver( br, new IntentFilter( "com.example.FirstAndroid" ) );

    // Create all of my Alarm Objects
    // TODO: Set date for each calendar from file
    alarms[0] = new Alarm( this, editNotes1, dateText1, toggleButton1, 0, Calendar.getInstance() );
    alarms[0].setTags();

    alarms[1] = new Alarm( this, editNotes2, dateText2, toggleButton2, 1, Calendar.getInstance() );
    alarms[1].setTags();
  }

  public void toggleAlarm( View v ) {
    if( v.getId() == toggleButton1.getId() ) {
      if( toggleButton1.isChecked() ) {
        alarms[0].setNotes( editNotes1.getText().toString() );
        am.set( AlarmManager.RTC, c.getTimeInMillis(), alarms[0].pi );
        toastIt( "Alarm On: " + alarms[0] );

      } else {
        toastIt( "Alarm Off: " + editNotes1.getText().toString() );
      }
    } else if( v.getId() == toggleButton2.getId() ) {
      if( toggleButton2.isChecked() ) {
        alarms[1].setNotes( editNotes2.getText().toString() );
        am.set( AlarmManager.RTC, c.getTimeInMillis(), alarms[1].pi );
        toastIt( "Alarm On: " + alarms[1] );

      } else {
        toastIt( "Alarm Off: " + editNotes1.getText().toString() );
      }
    }
  }

  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
      Alarm am = (Alarm)currentAlarm;

      am.cal.set( Calendar.YEAR, year );
      am.cal.set( Calendar.MONTH, monthOfYear );
      am.cal.set( Calendar.DAY_OF_MONTH, dayOfMonth );

      timePicker.show();  // Launches the TimePicker right after the DatePicker closes
    }
  };

  TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
    @Override
    public void onTimeSet( TimePicker view, int hour, int minute ) {
      Alarm am = (Alarm)currentAlarm;
      am.cal.set( Calendar.HOUR, hour );
      am.cal.set( Calendar.MINUTE, minute );
      am.updateDateTime();
    }
  };

  public void dateOnClick( View view ) {
    Alarm am = (Alarm)view.getTag();
    currentAlarm = am;
    timePicker = new TimePickerDialog( MedicineAlarm.this, time,
        am.cal.get( Calendar.HOUR ),
        am.cal.get( Calendar.MINUTE ), false );
    new DatePickerDialog( MedicineAlarm.this, date,
        am.cal.get( Calendar.YEAR ),
        am.cal.get( Calendar.MONTH ),
        am.cal.get( Calendar.DAY_OF_MONTH ) ).show();
  }

  private void createNotification( String notes ) {
    // prepare intent which is triggered if the notification is selected
    Intent intent = new Intent( this, MedicineAlarm.class );
    PendingIntent pIntent = PendingIntent.getActivity( this, 0, intent, 0 );
    Notification n = new Notification.Builder( this )
        .setContentTitle( "Medicine Alarm" )
        .setContentText( notes )
        .setSmallIcon( R.drawable.ic_launcher )
        .setContentIntent( pIntent )
        .setAutoCancel( true )
        .addAction( R.drawable.ic_launcher, "Call", pIntent )
        .addAction( R.drawable.ic_launcher, "More", pIntent )
        .build();
    NotificationManager notificationManager =
        (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
    notificationManager.notify( 0, n );
  }

  @Override
  protected void onDestroy() {
    am.cancel( pi );
    unregisterReceiver( br );
    super.onDestroy();
  }
}
