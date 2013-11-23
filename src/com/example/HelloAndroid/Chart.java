package com.example.HelloAndroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: dljones
 * Date: 10/30/13
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class Chart extends BaseActivity {

  private enum ChartStyle {BARCHART, LINECHART}

  ;
  ChartStyle selectedChartStyle = ChartStyle.BARCHART;

  ArrayList<Integer> weightList = new ArrayList<Integer>();
  ArrayList<Date> dateList = new ArrayList<Date>();

  private GraphicalView mChart;
  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
  private XYSeries mCurrentSeries;
  private XYSeriesRenderer mCurrentRenderer;

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    readWeightLog();
    setContentView( R.layout.chart );
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu ) {
    super.onCreateOptionsMenu( menu );
    getMenuInflater().inflate( R.menu.chart, menu );
    return true;
  }

  @Override
  public boolean onOptionsItemSelected( MenuItem item ) {
    switch( item.getItemId() ) {
      case R.id.barChart:
        // Draw the barchart
        selectedChartStyle = ChartStyle.BARCHART;
        drawChart();
        break;
      case R.id.lineChart:
        // Draw the linechart
        selectedChartStyle = ChartStyle.LINECHART;
        drawChart();
        break;
      default:
        return super.onOptionsItemSelected( item );
    }
    return true;
  }

  private void drawChart() {
    LinearLayout layout = (LinearLayout)findViewById( R.id.chart );
    layout.removeView( mChart );
    switch( selectedChartStyle ) {
      case BARCHART:
        mChart = ChartFactory.getBarChartView( this, mDataset, mRenderer, BarChart.Type.DEFAULT );
        break;
      case LINECHART:
        mChart = ChartFactory.getTimeChartView( this, mDataset, mRenderer, "MM/dd/yyyy" );
        break;
      default:
        break;
    }
    layout.addView( mChart );
  }

  private void initChart() {
    mCurrentSeries = new TimeSeries( "Weight Log" );
    mDataset.addSeries( mCurrentSeries );
    mCurrentRenderer = new XYSeriesRenderer();
    mRenderer.addSeriesRenderer( mCurrentRenderer );

    mRenderer.setAxisTitleTextSize( 24 );
    mRenderer.setLabelsTextSize( 24 );
    mRenderer.setChartTitleTextSize( 48 );
  }

  private void addWeightData() {
    for( int i = 0; i < weightList.size(); i++ ) {
      mCurrentSeries.add( dateList.get( i ).getTime(), weightList.get( i ) );
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if( mChart == null ) {
      addWeightData();
      initChart();
      drawChart();
    } else {
      //mChart.repaint();
      drawChart();
    }
  }

  private void readWeightLog() {
    FileInputStream inputStream = null;
    String temp;
    String a[];

    try {
      inputStream = openFileInput( FILENAME );
      byte[] reader = new byte[inputStream.available()];
      while( inputStream.read( reader ) != -1 ) {
      }

      // reader array now holds the entire file
      Scanner s = new Scanner( new String( reader ) );
      s.useDelimiter( "\\n" );
      while( s.hasNext() ) {
        temp = s.next();
        a = temp.split( "," );
        weightList.add( Integer.parseInt( a[2] ) );
        String dateTime = a[0] + " " + a[1];  // 10/30/2013	7:31 AM
        dateList.add( new SimpleDateFormat( "MM/dd/yyyy hh:mm a" ).parse( dateTime ) );
      }
      s.close();

    } catch( Exception e ) {
      Log.e( "Chart", e.getMessage() );
    } finally {
      if( inputStream != null ) {
        try {
          inputStream.close();
        } catch( IOException e ) {
          Log.e( "Chart", e.getMessage() );
        }
      }
    }
  }
}
