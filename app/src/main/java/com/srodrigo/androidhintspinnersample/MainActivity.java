/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinnersample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.srodrigo.androidhintspinner.HintAdapter;
import com.srodrigo.androidhintspinner.HintSpinner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

	private static final String VALUE_STRING = "Value %d";

	private HintSpinner<String> defaultHintSpinner;
	private List<String> defaults;

	private UserHintSpinner userHintSpinner;
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
		defaultHintSpinner = (HintSpinner<String>) findViewById(R.id.default_hint_spinner);

		defaults = new ArrayList<>();
		defaults.add(String.format(VALUE_STRING, 1));
		defaults.add(String.format(VALUE_STRING, 2));
		defaults.add(String.format(VALUE_STRING, 3));

		HintAdapter defaultsAdapter = new HintAdapter(this, R.string.default_spinner_hint, defaults);
		defaultHintSpinner.initAdapter(defaultsAdapter);
		defaultHintSpinner.setCallback(new HintSpinner.Callback<String>() {
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
				defaultHintSpinner.updateData(defaults);
				defaultHintSpinner.selectHint();
			}
		});
	}

	private void initUserHintSpinner() {
		userHintSpinner = (UserHintSpinner) findViewById(R.id.user_hint_spinner);

		users = new ArrayList<>();
		users.add(new User("Albert", "Einstein"));
		users.add(new User("Charles", "Darwin"));
		users.add(new User("Isaac", "Newton"));

		userHintSpinner.initAdapter(users);
		userHintSpinner.setCallback(new HintSpinner.Callback<User>() {
			@Override
			public void onItemSelected(int position, User itemAtPosition) {
				Toast.makeText(MainActivity.this, "Selected " + itemAtPosition, Toast.LENGTH_SHORT).show();
			}
		});

		Button addMoreButton = (Button) findViewById(R.id.add_new_user_button);
		addMoreButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				users.add(User.generateRandom());
				userHintSpinner.updateData(users);
				userHintSpinner.selectHint();
			}
		});
	}

	private void showSelectedItem(String itemAtPosition) {
		Toast.makeText(this, "Selected " + itemAtPosition, Toast.LENGTH_SHORT).show();
	}
}
