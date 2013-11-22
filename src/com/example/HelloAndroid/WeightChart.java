package com.example.HelloAndroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: dljones
 * Date: 10/22/13
 * Time: 8:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeightChart extends BaseActivity {
  Paint paint;
  ArrayList<Integer> weightList = new ArrayList<Integer>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE );
    readWeightLog();
    setContentView(R.layout.weightchart);
    initializeView();
  }

  private void initializeView() {
    paint = new Paint();
    paint.setColor( Color.BLACK );
    paint.setStrokeWidth( 2 );
    paint.setTextSize( 20 );
 //   paint.setStyle(Paint.Style.STROKE);

    setContentView(new Panel(this));
  }

  class Panel extends View {
    public Panel( Context context ){
      super( context );
    }

    @Override
    public void onDraw( Canvas canvas ){
      int originX = 10, originY = 800;
      canvas.drawColor( Color.WHITE );  // Background color
      canvas.drawCircle( 300, 80, 30, paint );

      canvas.drawText( "" + canvas.getWidth() + ", " + canvas.getHeight(), 0, 200, paint);
      canvas.drawLine(originX, originY, 600, originY, paint);
      canvas.drawLine( originX, originY, originX, originY - 600, paint);

      // Weight
      // x = time
      // y = weight
      // 10, 200 - 20, 210 - 30, 180
//      canvas.drawLine( originX + 10, originY - 200, originX + 20, originY - 210, paint );
//      canvas.drawLine( originX + 20, originY - 210, originX + 30, originY - 180, paint );

      paint.setColor( Color.RED );
      int currentY = weightList.get(0);
      int currentX = 10;
      for( Integer weight : weightList ){
        canvas.drawLine( currentX + originX, originY - currentY,
                         currentX + 10 + originX, originY - weight,
                         paint );
        currentX += 10;
        currentY = weight;
      }

    }
  }

  private void readWeightLog(){
    FileInputStream inputStream = null;
    String temp;
    String a[];

    try {
      inputStream = openFileInput( FILENAME );
      byte[] reader = new byte[ inputStream.available() ];
      while( inputStream.read( reader ) != -1 ){}

      // reader array now holds the entire file
      Scanner s = new Scanner( new String(reader) );
      s.useDelimiter("\\n");
      while( s.hasNext() ){
        temp = s.next();
        a = temp.split(",");
        weightList.add(Integer.parseInt(a[2]));
      }
      s.close();

    } catch (Exception e ){
      Log.e( "Chart", e.getMessage() );
    } finally {
        if( inputStream != null ) {
          try {
            inputStream.close();
          } catch( IOException e){
            Log.e( "Chart", e.getMessage());
          }
        }
    }
  }
}