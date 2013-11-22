package com.example.HelloAndroid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: dljones
 * Date: 11/20/13
 * Time: 7:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Alarm {
  Intent alarmIntent;
  PendingIntent pi;
  EditText notesText, dateText;
  ToggleButton tb;
  Integer alarmID;
  Context context;
  Calendar cal;

  public Alarm( Context context,
                EditText notesText,
                EditText dateText,
                ToggleButton tb,
                Integer alarmID,
                Calendar cal ){
    this.alarmID = alarmID;
    this.context = context;
    this.notesText = notesText;
    this.dateText = dateText;
    this.tb = tb;
    this.cal = cal;

    this.alarmIntent = new Intent( "com.example.FirstAndroid");
    alarmIntent.putExtra("notes", this.notesText.getText().toString() );
    this.pi = PendingIntent.getBroadcast( context, this.alarmID,
        this.alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT );
    updateDateTime();
  }

  public void setNotes( String notes ){
    notesText.setText( notes );
    alarmIntent.putExtra("notes", notesText.getText().toString());
  }

  public void updateDateTime() {
    String myFormat = "MM-dd-yy hh:mm a";
    SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );
    dateText.setText(sdf.format( cal.getTime()));
  }

}
