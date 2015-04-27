package com.parse.starter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class PostActivity extends Activity
{
	private EditText postEditText;
	private TextView characterCountTextView;
	private Button postButton;

	private int maxCharacterCount = ParseApplication.getConfigHelper().getPostMaxCharacterCount();
	private ParseGeoPoint geoPoint;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

		Intent intent = getIntent();
		Location location = intent.getParcelableExtra(ParseApplication.INTENT_EXTRA_LOCATION);
		geoPoint = new ParseGeoPoint(location.getLatitude(),location.getLongitude());

		postEditText = (EditText)findViewById(R.id.post_edittext);
		postEditText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable s)
			{
				updatePostButtonState();
				updateCharacterCountTextViewText();
			}
		});
		characterCountTextView = (TextView)findViewById(R.id.character_count_textview);
		postButton = (Button)findViewById(R.id.post_button);
		postButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				post();
			}
		});
		updatePostButtonState();
		updateCharacterCountTextViewText();
	}

	private void post()
	{
		String text = postEditText.getText().toString().trim();

		final ProgressDialog dialog = new ProgressDialog(PostActivity.this);
		dialog.setMessage(getString(R.string.progress_post));
		dialog.show();

		//Create  a Post
		GeoShare post = new GeoShare();

		post.setLocation(geoPoint);
		post.setText(text);
		post.setUser(ParseUser.getCurrentUser());
		ParseACL acl = new ParseACL();

		acl.setPublicReadAccess(true);
		post.setACL(acl);

		//Save the post
		post.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				dialog.dismiss();
				finish();
			}
		});
	}

	private String getPostEditText()
	{
		return postEditText.getText().toString().trim();
	}

	private void updatePostButtonState()
	{
		int length = getPostEditText().length();
		boolean enabled = length > 0 && length < maxCharacterCount;
		postButton.setEnabled(enabled);
	}

	private void updateCharacterCountTextViewText()
	{
		String characterCountString = String.format("%d%d",postEditText.length(),maxCharacterCount);
		characterCountTextView.setText(characterCountString);
	}
}
