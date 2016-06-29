package com.example.DiabetesApp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Joel on 2/26/2016.
 */

public class DiabetesChart extends MyActivity {
  ArrayList<BarEntry> entries = new ArrayList<>(  );
  ArrayList<String> labels = new ArrayList<String>( );
  BarChart chart;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    setContentView( R.layout.diabetes_chart );
    chart = (BarChart) findViewById( R.id.chartViewer );

    showChart();
  }

  private void showChart() {
    labels.clear();
    entries.clear();

    chart.clear();
    readDiabetesLog();
    BarDataSet dataSet = new BarDataSet( entries, "Blood Sugar" );
    BarData data = new BarData( labels, dataSet );
    chart.setData( data );
    chart.invalidate();
  }

  int eCount = 0;
  private void readDiabetesLog(){
    FileInputStream inputStream = null;
    try {
      inputStream = openFileInput( diabetesLogFileName );
      byte[] pureData = new byte[inputStream.available()];
      while(inputStream.read(pureData) != -1 ){}
      Scanner s = new Scanner(new String(pureData));
      s.useDelimiter( "\\n" );
      while (s.hasNext()){
        String temp = s.next();
        String a[] = temp.split(delimiter);
        entries.add(new BarEntry( Float.parseFloat( a[3]), eCount ));
        eCount += 1;
        labels.add(a[0]);
      }
      s.close();
    }catch(Exception e){
      Log.e("CHART", e.getMessage());
    }finally{
      if (inputStream != null){
        try {
          inputStream.close();
        }catch(IOException e){
          Log.e("CHART", e.getMessage());
        }
      }
    }
  }
}