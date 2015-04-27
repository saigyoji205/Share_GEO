package com.parse.starter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;


public class SettingsActivity extends Activity
{
	private List<Float> availableOptions = ParseApplication.getConfigHelper().getSearchDistanceAvailableOptions();

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		float currentSearchDistance = ParseApplication.getSearchDistance();
		if(!availableOptions.contains(currentSearchDistance))
		{
			availableOptions.add(currentSearchDistance);
		}
		Collections.sort(availableOptions);

		RadioGroup searchDistanceRadioGroup = (RadioGroup)findViewById(R.id.searchdistance_radiogroup);

		for(int index = 0; index < availableOptions.size(); index++)
		{
			float searchDistance = availableOptions.get(index);

			RadioButton button = new RadioButton(this);
			button.setId(index);
			button.setText(getString(R.string.settings_distance_format, (int) searchDistance));
			searchDistanceRadioGroup.addView(button,index);

			if(currentSearchDistance == searchDistance)
			{
				searchDistanceRadioGroup.check(index);
			}
		}

		searchDistanceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				ParseApplication.setSearchDistance(availableOptions.get(checkedId));
			}
		});

		Button logoutButton = (Button)findViewById(R.id.logout_button);
		logoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				Intent intent = new Intent(SettingsActivity.this,DispatchActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}
}
