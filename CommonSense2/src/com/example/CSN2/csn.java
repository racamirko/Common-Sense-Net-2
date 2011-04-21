package com.example.CSN2;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.io.FileOutputStream;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class csn extends Activity {
	private EditText text;
	private EditText text2;
	
	int market=0;
	int news=0;
	int radio=0;
	int weather=0;
	String STR_MARKET="Market ";
	String STR_NEWS="News ";
	String STR_RADIO="Radio ";
	String STR_WEATHER="Weather ";
	
	String TESTSTRING = "Hello";
	
	String TESTA;
	
	
	
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
           
       }
     
	public void myClickHandler(View view) {
	
		try { // catches IOException below
	        TESTSTRING ="Hello Android";
	        
	      
	        FileOutputStream fOut = openFileOutput("csnlog.txt",
	        MODE_WORLD_READABLE);
	        OutputStreamWriter osw = new OutputStreamWriter(fOut);
	        
	        
	        // Write the string to the file
	        osw.write(TESTSTRING);
	        
	        osw.flush();
	        osw.close();
	        
	        FileInputStream fIn = openFileInput("csnlog.txt");
	        InputStreamReader isr = new InputStreamReader(fIn);
	      
	        char[] inputBuffer = new char[TESTSTRING.length()];
	        // Fill the Buffer with data from the file
	        isr.read(inputBuffer);
	        
	        // Transform the chars to a String
	        TESTA = String.valueOf(inputBuffer); 
	        
	       
	    } 
		catch (IOException ioe) {
	        ioe.printStackTrace();
	    }
		
		
		switch (view.getId()) {
		case R.id.button1:
			market++;
			
			
			text.setText(STR_MARKET.concat(String.valueOf(market)));
			
			break;
		case R.id.button2:
			news++;
			text.setText(STR_NEWS.concat(String.valueOf(news)));
			break;
		case R.id.button3:
			radio++;
			text.setText(STR_RADIO.concat(String.valueOf(radio)));
			break;
		case R.id.button4:
			weather++;
			text.setText(STR_WEATHER.concat(String.valueOf(weather)));
			break;
			default:
				text2.setText(TESTA);
		}
    }
}