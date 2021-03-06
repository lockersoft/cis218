package com.example.HelloAndroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: dljones
 * Date: 10/15/13
 * Time: 7:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeightLogger extends BaseActivity {

  private Intent currentIntent;
  private Button btnChart;
  private EditText edtWeight;
  private EditText edtDate;
  private EditText edtTime;
  final Calendar c = Calendar.getInstance();

  @Override
  public void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.weight );
    initializeView();
  }

  private void initializeView() {
    btnChart = (Button)findViewById( R.id.btnChart );
    edtWeight = (EditText)findViewById( R.id.edtWeight );
    edtDate = (EditText)findViewById( R.id.edtDate );
    edtTime = (EditText)findViewById( R.id.edtTime );
    currentIntent = getIntent();
    Bundle extras = currentIntent.getExtras();
    if( extras != null ) {
      edtWeight.setText( extras.getString( "weight" ) );
    }
    setCurrentDateOnView();
    File file = getApplicationContext().getFileStreamPath( FILENAME );
    if( !file.exists() ) {
      // Disable the Chart Button
      btnChart.setEnabled( false );
    }
  }

  public void switchToChart( View view ) {
    Intent chart = new Intent( this, Chart.class );
    startActivity( chart );
  }

  public void saveLogOnClick( View view ) {
    String entry = edtDate.getText().toString() + "," +
        edtTime.getText().toString() + "," +
        edtWeight.getText().toString() + "\n";
    try {
      FileOutputStream out = openFileOutput( FILENAME, Context.MODE_APPEND );
      out.write( entry.getBytes() );
      out.close();
      toastIt( "Entry Saved" );
      btnChart.setEnabled( true );
    } catch( Exception e ) {
      e.printStackTrace();
    }
  }

  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
      c.set( Calendar.YEAR, year );
      c.set( Calendar.MONTH, monthOfYear );
      c.set( Calendar.DAY_OF_MONTH, dayOfMonth );
      setCurrentDateOnView();
    }
  };

  TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
    @Override
    public void onTimeSet( TimePicker view, int hourOfDay, int minute ) {
      c.set( Calendar.HOUR_OF_DAY, hourOfDay );
      c.set( Calendar.MINUTE, minute );
      setCurrentDateOnView();
    }
  };

  public void dateOnClick( View view ) {
    new DatePickerDialog( WeightLogger.this, date,
        c.get( Calendar.YEAR ), c.get( Calendar.MONTH ), c.get( Calendar.DAY_OF_MONTH ) ).show();
  }

  public void timeOnClick( View view ) {
    new TimePickerDialog( WeightLogger.this, time,
        c.get( Calendar.HOUR ), c.get( Calendar.MINUTE ), false ).show();
  }

  public void setCurrentDateOnView() {
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat( dateFormat, Locale.US );
    edtDate.setText( sdf.format( c.getTime() ) );

    String timeFormat = "hh:mm a";
    SimpleDateFormat stf = new SimpleDateFormat( timeFormat, Locale.US );
    edtTime.setText( stf.format( c.getTime() ) );
  }

  public void emailDrOnClick( View v ){
    // Email the log to the Dr.
    try {
      String email = "dave@lockersoft.com";
      String subject = "Dave Jones Weight Log";
      String message = "Dr. Heavy,\nHere is my latest weight log for you to peruse.\n\nDave";

      copyFileToExternal( FILENAME );
      File file = new File( Environment.getExternalStorageDirectory() + EXT_FOLDERNAME + FILENAME);
      Uri path = Uri.fromFile(file);

      final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
      emailIntent.setType( "plain/text" );
      emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
      new String[] { email });
      emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
      emailIntent.putExtra(Intent.EXTRA_STREAM, path);

      emailIntent.putExtra( android.content.Intent.EXTRA_TEXT, message );
      startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."), 4242);

    } catch (Throwable t) {
      toastIt( "Request failed try again: " + t.toString() );
    }
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 4242) {
      File file = new File(Environment.getExternalStorageDirectory() + EXT_FOLDERNAME + FILENAME);
      file.delete();
    }
  }


  public void switchToCalc( View v ) {
    finish();
  }
}
