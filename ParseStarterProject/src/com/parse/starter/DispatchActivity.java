package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;


public class DispatchActivity extends Activity
{
	public DispatchActivity()
	{

	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if(ParseUser.getCurrentUser() != null)
		{
			startActivity(new Intent(this,WelcomeActivity.class));
		}
		else
		{
			startActivity(new Intent(this,WelcomeActivity.class));
		}
	}
}
