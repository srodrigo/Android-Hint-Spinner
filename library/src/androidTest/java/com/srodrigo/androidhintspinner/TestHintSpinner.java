/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.List;

public class TestHintSpinner extends HintSpinner<User> {

	public static final String HINT_TEXT = "Please select a user";

	public TestHintSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initAdapter(List<User> items) {
		super.initAdapter(new HintAdapter(
				getContext(),
				HINT_TEXT,
				items) {

			@Override
			protected void setDropDownViewText(int position, TextView dropDownView) {
				final User user = (User) getItem(position);
				String name = user.getName();
				String lastName = user.getLastName();
				final String text = String.format("%s %s", name, lastName);
				dropDownView.setText(text);
			}
		});
	}
}
