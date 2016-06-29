package com.example.DiabetesApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Joel on 4/17/2016.
 */
public class DiabetesPrefs extends MyActivity {
  EditText txtDrEmail;
  EditText txtDrSubject;
  EditText txtDrMessage;
  RadioGroup radGroup2;
  RadioButton radButtonDefault;
  Button btnSendDrEmail;
  SharedPreferences appPrefs;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.diabetes_prefs );

    txtDrEmail = (EditText) findViewById( R.id.txtDrEmail );
    txtDrSubject = (EditText) findViewById( R.id.txtDrSubject );
    txtDrMessage = (EditText) findViewById( R.id.txtDrMessage );

    radGroup2 = (RadioGroup) findViewById( R.id.radGroup2 );

    appPrefs = getSharedPreferences( "prefInfo", Context.MODE_PRIVATE );

    btnSendDrEmail = (Button) findViewById( R.id.btnSendDrEmail );
    Button savePrefs = (Button) findViewById( R.id.btnSavePrefs );

  }


  public void onClickEmailDr(View v) {

    String drEmail = txtDrEmail.getText().toString();
    String drSubject = txtDrSubject.getText().toString();
    String drMessage = txtDrMessage.getText().toString();

    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType( "message" );
    intent.putExtra(Intent.EXTRA_EMAIL, drEmail);
    intent.putExtra( Intent.EXTRA_SUBJECT, drSubject );
    intent.putExtra( Intent.EXTRA_SUBJECT, drMessage );
//    startActivity( Intent.createChooser( intent, "Email Test" ) );
    toastIt( "Email Sent!" );
  }

  public void savePrefs() {
    SharedPreferences sharePref = getSharedPreferences( "savedPrefs", Context.MODE_PRIVATE );
    SharedPreferences.Editor editor = sharePref.edit();
    if(radADAG.isChecked()){
      editor.putString( "ADAG", radADAG.getText().toString() );
    }else{
      editor.putString( "DCCT", radADAG.getText().toString() );
    }
    editor.apply();
    toastIt( "Saved!" );
  }
}




