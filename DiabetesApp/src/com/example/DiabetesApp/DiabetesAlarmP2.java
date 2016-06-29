package com.example.DiabetesApp;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Joel on 3/15/2016.
 */
public class DiabetesAlarmP2 extends MyActivity {
  private Calendar c = Calendar.getInstance();
  EditText txtAlarmDate;
  EditText txtAlarmNotes;
  Switch switchActive;
  Switch switchRecurring;


  private AlarmManager am;
  private BroadcastReceiver br;
  private Alarm[] alarms = new Alarm[5];

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.diabetes_alarm_p2 );
    txtAlarmNotes = (EditText) findViewById( R.id.txtAlarmNotes );
    txtAlarmDate = (EditText) findViewById( R.id.txtAlarmDate );
    switchActive = (Switch) findViewById( R.id.switchActive );
    switchRecurring = (Switch) findViewById( R.id.switchRecurring );

    br = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        toastIt( "WAKE UP!" );
        createNotification();
      }

    };
    registerReceiver( br, new IntentFilter( getPackageName() ));
    am = (AlarmManager) this.getSystemService( Context.ALARM_SERVICE );
//    am.set( AlarmManager.RTC, c.getTimeInMillis() + 5000, pi );
//    txtAlarmDate = (EditText) findViewById( R.id.txtAlarmDate );
    alarms[0] = new Alarm(txtAlarmNotes, txtAlarmDate, switchRecurring, switchActive, 0, this, Calendar.getInstance());
    alarms[1] = new Alarm(txtAlarmNotes, txtAlarmDate, switchRecurring, switchActive, 0, this, Calendar.getInstance());
    alarms[2] = new Alarm(txtAlarmNotes, txtAlarmDate, switchRecurring, switchActive, 0, this, Calendar.getInstance());
    alarms[3] = new Alarm(txtAlarmNotes, txtAlarmDate, switchRecurring, switchActive, 0, this, Calendar.getInstance());
    alarms[4] = new Alarm(txtAlarmNotes, txtAlarmDate, switchRecurring, switchActive, 0, this, Calendar.getInstance());
  }

  public void activeOnClick(View v) {
    if (switchActive.isChecked()){
      toastIt( "Alarm is active!" );
      alarms[0].cal.add(Calendar.SECOND, 5);
      alarms[1].cal.add(Calendar.SECOND, 5);
      alarms[2].cal.add(Calendar.SECOND, 5);
      alarms[3].cal.add(Calendar.SECOND, 5);
      alarms[4].cal.add(Calendar.SECOND, 5);
      Calendar cal = Calendar.getInstance();
      cal.add( Calendar.SECOND, 5);
      am.set(AlarmManager.RTC_WAKEUP,  cal.getTimeInMillis(), alarms[0].pi ); //[0].cal.getTimeInMillis(), alarms[0].pi);
    }
    else{
      toastIt( "Alarm is off!" );
    }
  }
  public void dateOnClick(View v){
    new DatePickerDialog(this, date,
        c.get( Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
  }
  private void setCurrentDateOnView(){
    //String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    SimpleDateFormat stf = new SimpleDateFormat( "hh:mm a", Locale.US );
    txtAlarmDate.setText(sdf.format(c.getTime()) + " " + stf.format( c.getTime() ));
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

  public void createNotification(){
    Intent intent = new Intent(this, DiabetesAlarmP2.class);
    PendingIntent pIntent = PendingIntent.getActivity( this,0,intent,0 );
    Notification n = new Notification.Builder(this)
        .setContentTitle("Medicine Alarm")
        .setContentText("Medication Notes")
        .setSmallIcon(R.drawable.ic_alarm)
        .setContentIntent(pIntent)
        .build();
    NotificationManager notificationManager =
        (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify( 0,n );
  }


}
