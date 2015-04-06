/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinnersample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.srodrigo.androidhintspinner.HintAdapter;
import com.srodrigo.androidhintspinner.HintSpinner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

	private static final String VALUE_STRING = "Value %d";

	private HintSpinner<String> defaultHintSpinner;
	private List<String> defaults;

	private HintSpinner<User> userHintSpinner;
	private List<User> users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Hint Spinner using default provided layout
		initDefaultHintSpinner();

		// Hint Spinner using custom layout
		initUserHintSpinner();
	}

	private void initDefaultHintSpinner() {
		defaults = new ArrayList<>();
		defaults.add(String.format(VALUE_STRING, 1));
		defaults.add(String.format(VALUE_STRING, 2));
		defaults.add(String.format(VALUE_STRING, 3));

		Spinner defaultSpinner = (Spinner) findViewById(R.id.default_spinner);

		defaultHintSpinner = new HintSpinner<>(
				defaultSpinner,
				new HintAdapter<>(this, R.string.default_spinner_hint, defaults),
				new HintSpinner.Callback<String>() {
					@Override
					public void onItemSelected(int position, String itemAtPosition) {
						showSelectedItem(itemAtPosition);
					}
				});

		Button addMoreButton = (Button) findViewById(R.id.add_new_value_button);
		addMoreButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String randomValue = String.format(VALUE_STRING, Util.generateRandomPositive());
				defaults.add(randomValue);
				defaultHintSpinner.selectHint();
			}
		});
		defaultHintSpinner.init();
		Log.d("Count", "Count: " + defaultSpinner.getCount());
	}

	private void initUserHintSpinner() {
		users = new ArrayList<>();
		users.add(new User("Albert", "Einstein"));
		users.add(new User("Charles", "Darwin"));
		users.add(new User("Isaac", "Newton"));

		Spinner userSpinner = (Spinner) findViewById(R.id.user_spinner);

		userHintSpinner = new HintSpinner<>(
				userSpinner,
				new HintAdapter<User>(
						this,
						R.layout.row_user_spinner,
						R.string.user_spinner_hint,
						users) {

					@Override
					protected View getCustomView(int position, View convertView, ViewGroup parent) {
						final User user = getItem(position);
						final String name = user.getName();
						final String lastName = user.getLastName();

						View view = inflateLayout(parent, false);
						view.findViewById(R.id.user_image_view).setBackgroundResource(
								R.drawable.ic_action_face_unlock);
						((TextView) view.findViewById(R.id.user_name_text_view)).setText(name);
						((TextView) view.findViewById(R.id.user_last_name_text_view)).setText(lastName);

						return view;
					}
				},
				new HintSpinner.Callback<User>() {
					@Override
					public void onItemSelected(int position, User itemAtPosition) {
						Toast.makeText(MainActivity.this, "Selected " + itemAtPosition, Toast.LENGTH_SHORT).show();
					}
				});
		userHintSpinner.init();

		Button addMoreButton = (Button) findViewById(R.id.add_new_user_button);
		addMoreButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				users.add(User.generateRandom());
				userHintSpinner.selectHint();
			}
		});
	}

	private void showSelectedItem(String itemAtPosition) {
		Toast.makeText(this, "Selected " + itemAtPosition, Toast.LENGTH_SHORT).show();
	}
}
