package com.example.DiabetesApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Joel on 4/7/2016.
 */
public class About extends MyActivity {
//  Button about = (Button)findViewById( R.id.btnAbout );
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.diabetes_about );
  }

  public void onClickAbout(View v){
    Intent browserIntent = new Intent( Intent.ACTION_VIEW,
        Uri.parse("http://www.about.joelsimmons75.com/about.html"));
    startActivity(browserIntent);
  }
  public void onClickContact(View v){
    Intent browserIntent = new Intent( Intent.ACTION_VIEW,
        Uri.parse("http://contact.joelsimmons75.com/contact.php"));
    startActivity(browserIntent);
  }
}



