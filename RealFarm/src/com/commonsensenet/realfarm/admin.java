package com.commonsensenet.realfarm;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class admin extends Activity {
 
  public EditText password;
  public Button btnSubmit;
  public String pwd = "a";
  public String EnterPwd ;
  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.admin);
 
	addListenerOnButton();
 
  }
 
  public void addListenerOnButton() {
 
	password = (EditText) findViewById(R.id.txtPassword);	
	btnSubmit = (Button) findViewById(R.id.btnSubmit);
 
	btnSubmit.setOnClickListener(new OnClickListener() {
 
		public void onClick(View v) {
 
		//  Toast.makeText(admin.this, password.getText(),
		//	Toast.LENGTH_SHORT).show();                           //Displays the password
			EnterPwd = password.getText().toString();
			System.out.println(EnterPwd);
			if(EnterPwd.equals(pwd))
			{
				System.out.println("Correct password");
				Intent adminintent = new Intent(admin.this,admincall.class);
			      startActivity(adminintent);
			      admin.this.finish();
			}
			else
			{
				Toast.makeText(admin.this, "Enter correct password",
							Toast.LENGTH_SHORT).show();
			}
			
			
		}

		
 
	});
 
  }
}