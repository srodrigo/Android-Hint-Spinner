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

import com.srodrigo.androidhintspinner.HintSpinner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

	private List<User> users;

	private UserHintSpinner userHintSpinner;
	private Button addMoreButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

		addMoreButton = (Button) findViewById(R.id.add_more_button);
		addMoreButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				users.add(User.generateRandom());
				userHintSpinner.updateData(users);
				userHintSpinner.selectHint();
			}
		});
	}
}
