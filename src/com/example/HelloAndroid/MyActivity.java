package com.example.HelloAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.text.DecimalFormat;
import java.text.ParseException;

public class MyActivity extends BaseActivity {

  public static EditText heightIn;
  public static EditText weightIn;
  private Button btnCalcBMI;
  private TextView bmiOut;
  private TextView bmiStatus;
  private double weight = 0;
  private double height = 0;

  private RadioGroup weightLocationGroup;
  private RadioButton rdoAmerican;
  private RadioButton rdoWorld;
  private boolean american = true;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    initializeApp();
  }

  private void initializeApp() {
    weightIn = (EditText) findViewById(R.id.weightInput);
    heightIn = (EditText) findViewById(R.id.heightInput);
    btnCalcBMI = (Button) findViewById(R.id.btnCalcBMI);
    bmiOut = (TextView) findViewById(R.id.bmiOut);
    bmiStatus = (TextView) findViewById(R.id.bmiStatus);

    weightLocationGroup = (RadioGroup) findViewById(R.id.weightLocationGroup);
    rdoAmerican = (RadioButton) findViewById(R.id.rdoAmerican);
    rdoWorld = (RadioButton) findViewById(R.id.rdoWorld);

    weightIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        handleOnFocusChange(v, hasFocus);
      }
    });
    heightIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        handleOnFocusChange(v, hasFocus);
      }
    });
  }

  public void handleOnFocusChange(View v, boolean hasFocus) {
    if (hasFocus) {
      toastIt(v.toString() + " got the focus");
    } else {
      toastIt(v.toString() + " lost the focus");
    }
    calculateBMI(null);
  }

  public void radioButtonClicked(View v) {
    //   Log.i( "MyActivity", "Radio Button Clicked");
    int selectedId = weightLocationGroup.getCheckedRadioButtonId();
    RadioButton selected = (RadioButton) findViewById(selectedId);
//    Log.i( "MyActivity", selected.getText().toString() );
    toastIt(selected.getText().toString());

    american = rdoAmerican.isChecked();  // Main code
    calculateBMI(null);
  }

  public void calculateBMI(View v) {
    String status;
//    Log.i("calculateBMI", "Button was pressed");
//    weight = Double.parseDouble( weightIn.getText().toString() );
    DecimalFormat formatter = new DecimalFormat();
    try {
      weight = formatter.parse(weightIn.getText().toString()).doubleValue();
      height = formatter.parse(heightIn.getText().toString()).doubleValue();
    } catch (ParseException ex) {
      Log.d("MyActivity", "Cannot Parse Number: " +
                weightIn.getText().toString() +
                " or " +
                heightIn.getText().toString()
      );
      System.out.println("Cannot parse number");
    }
    if (american)
      weight += 20;

    double bmi = (weight / (height * height)) * 703.0;
    String result = String.format("%.2f", bmi);
    Log.d("MyActivity", result);
    bmiOut.setText(result, TextView.BufferType.NORMAL);

    // < 16  seriously underweight
    // 16 - 18 underweight
    // 19 - 24 normal weight
    // 25 - 29 overweight
    // 30 - 35 seriously overweight
    // > 35 Gravely overweight

    // Set status
    if (bmi < 16.0) {
      status = getString(R.string.seriously_underweight);
    } else if (bmi >= 16.0 && bmi < 18.0) {
      status = getString(R.string.underweight);
    } else if (bmi >= 18.0 && bmi < 24.0) {
      status = getString(R.string.normal_weight);
    } else if (bmi >= 24.0 && bmi < 29.0) {
      status = getString(R.string.overweight);
    } else if (bmi >= 29.0 && bmi < 35.0) {
      status = getString(R.string.seriously_overweight);
    } else {
      status = getString(R.string.gravely_overweight);
    }

    bmiStatus.setText(status);
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    savedInstanceState.putBoolean("american", rdoAmerican.isChecked());
    savedInstanceState.putString("heightIn", heightIn.getText().toString());
    savedInstanceState.putString("weightIn", weightIn.getText().toString());
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    american = savedInstanceState.getBoolean("american");
    weightIn.setText(savedInstanceState.getString("weightIn"));
    heightIn.setText(savedInstanceState.getString("heightIn"));
  }
}
