/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinnersample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.srodrigo.androidhintspinner.HintAdapter;
import com.srodrigo.androidhintspinner.HintSpinner;

import java.util.List;

/**
 * Sample hint spinner.
 */
public class UserHintSpinner extends HintSpinner<User> {
	public UserHintSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initAdapter(List<User> items) {
		super.initAdapter(new HintAdapter(
				getContext(),
				R.string.user_spinner_hint,
				items) {

			@Override
			protected void setDropDownViewText(int position, TextView dropDownView) {
				final User user = (User) getItem(position);
				String name = user.getName();
				String lastName = user.getLastName();
				final String text = getResources().getString(R.string.user_spinner_text, name, lastName);
				dropDownView.setText(text);
			}
		});
	}
}
