package com.commonsensenet.realfarm.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsensenet.realfarm.R;

public class LoginActivity extends Activity {

	/** Password used to access the Administrator section. */
	private static final String PASSWORD = "a";

	/** Field where the User enters the password. */
	private EditText mPasswordTextField;
	/** Button used to submit the password. */
	private Button mSubmitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_admin_login);

		// gets the related fields.
		mPasswordTextField = (EditText) findViewById(R.id.admin_login_textfield_password);
		mSubmitButton = (Button) findViewById(R.id.admin_login_button_submit);

		// adds the click listener.
		mSubmitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				String enteredPassword = mPasswordTextField.getText()
						.toString();

				if (enteredPassword.equals(PASSWORD)) {

					Intent adminintent = new Intent(LoginActivity.this,
							UserListActivity.class);
					startActivity(adminintent);
					LoginActivity.this.finish();
				} else {
					Toast.makeText(LoginActivity.this,
							"Enter correct password", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
}