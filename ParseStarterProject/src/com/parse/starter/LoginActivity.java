package com.parse.starter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity
{
	/*ログイン用user name、パスワード*/
	private EditText usernameEditText;
	private EditText passwordEditText;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// Set up the login form.
		usernameEditText = (EditText)findViewById(R.id.username);
		passwordEditText = (EditText)findViewById(R.id.password);
		passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
				if(actionId == R.id.edittext_action_login || actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
				{
					login();
					return true;
				}
				return false;
			}
		});
		//set up the submit button click handler
		Button actionButton = (Button)findViewById(R.id.action_button);
		actionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				login();
			}
		});
	}

	private void login()
	{
		String username = usernameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();

		//ログイン時のエラーチェック
		boolean validationError = false;
		StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
		//usernameが未入力の場合
		if(username.length() == 0)
		{
			validationError = true;
			validationErrorMessage.append(getString(R.string.error_blank_username));
		}
		//passwordが未入力の場合
		if(password.length() == 0)
		{
			//usernamも未入力の場合、"+"を表示させる
			if(validationError)
			{
				validationErrorMessage.append(getString(R.string.error_join));
			}
			validationError = true;
			validationErrorMessage.append(getString(R.string.error_blank_password));
		}
		//validationErrorに一つでもtrueが入った場合、エラーメッセージを表示
		if(validationError)
		{
			Toast.makeText(LoginActivity.this,validationErrorMessage.toString(),Toast.LENGTH_LONG).show();
			return;
		}

		//set up a progress dialog
		final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
		dialog.setMessage(getString(R.string.progress_login));
		dialog.show();
		//Parsのログインメソッドを呼び出す。
		ParseUser.logInInBackground(username, password, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				dialog.dismiss();
				if(e != null)
				{
					Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(LoginActivity.this, "Successfully", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
