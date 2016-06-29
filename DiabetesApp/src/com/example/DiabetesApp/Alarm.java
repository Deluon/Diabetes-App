package com.example.DiabetesApp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;

/**
 * Created by Joel on 3/17/2016.
 */
public class Alarm {
  Intent alarmIntent;
  PendingIntent pi;
  EditText txtAlarmNotes, txtAlarmDate;
  Switch switchActive, switchRecurring;
  Integer alarmID;
  Context context;
  Calendar cal;

  public Alarm(EditText txtAlarmNotes,
               EditText txtAlarmDate, Switch switchActive, Switch switchRecurring,
               Integer alarmID, Context context, Calendar cal) {
    this.txtAlarmNotes = txtAlarmNotes;
    this.txtAlarmDate = txtAlarmDate;
    this.switchActive = switchActive;
    this.switchRecurring = switchRecurring;
    this.alarmID = alarmID;
    this.context = context;
    this.cal = cal;

    this.alarmIntent = new Intent("com.example.DiabetesApp");
    this.pi = PendingIntent.getBroadcast( context, alarmID, alarmIntent,PendingIntent.FLAG_CANCEL_CURRENT );
  }

  public void setTags(){
    txtAlarmNotes.setTag(this);
    txtAlarmDate.setTag(this);
    switchActive.setTag(this);
    switchRecurring.setTag(this);
  }
}
