package com.example.DiabetesApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MyActivity extends Activity {
  SharedPreferences getAppPrefs;
  RadioGroup radGroupCalcM1;

  EditText txtEag;
  EditText txtA1C;
  TextView lblResults;
  Button btnCalculate;
  RadioButton radADAG;
  RadioButton radDCCT;

  Double results;
  String diabetesLogFileName = "DiabetesLog.txt";
  String delimiter = "<=>";
  String meal;
  public void toastIt(String msg) {
    Toast.makeText( getApplicationContext(),
        msg, Toast.LENGTH_SHORT ).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate( R.menu.menu, menu );
    return true;
  }


  public void loadPrefs(){
    getAppPrefs = getSharedPreferences( "prefInfo", Context.MODE_PRIVATE );
    String def_pref = getAppPrefs.getString("def_calc", "ADAG");
    if(def_pref.equals( "ADAG" )){
      radADAG.isChecked();
    }else{
      radDCCT.isChecked();}
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item){
//    Log.i("Diabetes", item.getTitle().toString());
    switch(item.getItemId()){
      case R.id.btnPrefs:
        switchToPrefs( null );
        break;
      case R.id.btnAbout:
        switchToAbout( null );
        break;
      case R.id.switchToMain:
        switchToMain( null );
        break;
      case R.id.switchToLog:
        switchToLogger( lblResults );
        break;
      case R.id.switchToChart:
        switchToChart( null );
        break;
      case R.id.switchToAlarm:
        switchToAlarms( null );
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }


  /**
   * Called when the activity is first created.
   */
  @Override
  public void onPause(){
    super.onPause();
  }
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.main );

    btnCalculate = (Button) findViewById( R.id.btnCalculate );
    txtEag = (EditText) findViewById( R.id.txtEag );
    txtA1C = (EditText) findViewById( R.id.txtA1C );
    lblResults = (TextView) findViewById( R.id.lblResults );
    radADAG = (RadioButton) findViewById( R.id.radADAG );
    radDCCT = (RadioButton) findViewById( R.id.radDCCT );
    loadPrefs();
  }

  public void Calculate(View v) {
    try {
      Double eAG = Double.parseDouble( txtEag.getText().toString() );
      Double A1C = Double.parseDouble( txtA1C.getText().toString() );
//     Log.i("Diabetes","Inside Calculate: A1C" + A1C.toString());

      if (radADAG.isChecked()) {
        results = ((1.583 * A1C - 2.52) * 18.05);
      } else if (radDCCT.isChecked()) {
        results = ((A1C * 35.6) - 77.3);
      }
      lblResults.setText( String.format( "%.2f", results ) );
    } catch (Exception ex) {
      toastIt( "Follow instructions, dumbass." );
    }
  }
  public void onSaveInstanceState(Bundle savedState){
    super.onSaveInstanceState( savedState );
//    savedState.putDouble( "Results", results );
  }

  public void switchToMain(View v){
    startActivity(new Intent(this, MyActivity.class));
  }
  public void switchToChart(View v){
    startActivity(new Intent(this, DiabetesChart.class));
  }
  public void switchToAlarms(View v){
    startActivity(new Intent(this, DiabetesAlarmP1.class));
  }
  public void switchToLogger(View v){
    Intent extras = new Intent(this, DiabetesLog.class);
    extras.putExtra("Results", results);
    extras.setFlags(extras.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(extras);
  }

  public void alarmNext(View v){startActivity( new Intent(this,DiabetesAlarmP2.class) );}
  public void alarmLast(View v){startActivity( new Intent(this,DiabetesAlarmP1.class) );}
  public void switchToAbout(View v){startActivity(new Intent(this, About.class));}
  public void switchToPrefs(View v){startActivity(new Intent(this, DiabetesPrefs.class));}
}
