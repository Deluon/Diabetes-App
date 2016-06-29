package com.example.DiabetesApp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DiabetesLog extends MyActivity {
  TextView lblTime;
  TextView lblDate;
  EditText txtNotes;
  TextView lblBloodSugar;
  RadioButton radPreMeal;
  RadioButton radPostMeal;
  RadioButton radNoMeal;
  Button btnBackMain;

  private Calendar c = Calendar.getInstance();

  String meal;
  Double results;
  String userData;


  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.diabetes_log);
    lblDate = (TextView) findViewById( R.id.lblDate );
    lblTime = (TextView) findViewById( R.id.lblTime );
    txtNotes = (EditText) findViewById( R.id.txtNotes );
    radPreMeal = (RadioButton)findViewById( R.id.radPreMeal );
    radPostMeal = (RadioButton)findViewById( R.id.radPostMeal );
    radNoMeal = (RadioButton)findViewById( R.id.radNoMeal );

   // getIntentData();
    setCurrentDateOnView();

  }
  public void switchToChart(View v){
    Intent extras = new Intent(this, DiabetesChart.class);
    extras.putExtra("Results", results);
    startActivity(extras);
  }
  private void setCurrentDateOnView(){
    //String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    lblDate.setText(sdf.format(c.getTime()));

    SimpleDateFormat stf = new SimpleDateFormat( "hh:mm a", Locale.US );
    lblTime.setText(stf.format(c.getTime()));
  }

  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
      c.set(Calendar.YEAR, year);
      c.set(Calendar.MONTH, monthOfYear);
      c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      setCurrentDateOnView();
    }
  };
  TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
      c.set(Calendar.HOUR_OF_DAY, hourOfDay);
      c.set(Calendar.
          MINUTE, minute);
      setCurrentDateOnView();
    }
  };

  public void saveData(View v){
    haveEaten();
    userData = lblDate.getText().toString() + delimiter
            + lblTime.getText().toString() + delimiter + meal + delimiter
            + lblBloodSugar.getText().toString() + delimiter
            + txtNotes.getText().toString() + "\n";
    Log.i("Results", userData);

    try{
      FileOutputStream out = openFileOutput( diabetesLogFileName, Context.MODE_APPEND );
      out.write(userData.getBytes());
      out.close();
      toastIt("Entry Saved");
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public void toastIt(String msg) {
    Toast.makeText( getApplicationContext(),
        msg, Toast.LENGTH_SHORT ).show();
  }

  @Override
  protected void onResume(){
      super.onResume();
    Intent currentIntent = getIntent();
    lblBloodSugar = (TextView) findViewById( R.id.lblResults );
    Bundle extras = currentIntent.getExtras();
    if(extras != null){
      results = extras.getDouble("Results");
      lblBloodSugar.setText(String.format("%.2f", results));
    }
  }

  public void haveEaten(){
    if (radPreMeal.isChecked()){
      meal = "Before Meal";
    } else if (radPostMeal.isChecked()) {
      meal = "After Meal";
    } else if (radNoMeal.isChecked()) {
      meal = "No Meal";
    }
  }

  public void dateOnClick(View v){
    new DatePickerDialog(this, date,
        c.get( Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
  }

  public void timeOnClick(View v){
    new TimePickerDialog( this, time, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false ).show();
  }

}