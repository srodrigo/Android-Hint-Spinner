/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinnersample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srodrigo.androidhintspinner.HintAdapter;
import com.srodrigo.androidhintspinner.HintSpinner;

import java.util.List;

/**
 * Sample hint spinner.
 */
public class UserHintSpinner extends HintSpinner<User> {

	private static final int RESOURCE_LAYOUT = R.layout.row_user_spinner;

	public UserHintSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initAdapter(List<User> items) {
		super.initAdapter(new HintAdapter(
				getContext(),
				RESOURCE_LAYOUT,
				R.string.user_spinner_hint,
				items) {

			@Override
			protected View getCustomView(int position, View convertView, ViewGroup parent) {
				final User user = (User) getItem(position);
				final String name = user.getName();
				final String lastName = user.getLastName();

				View view = getLayoutInflater().inflate(RESOURCE_LAYOUT, parent, false);
				view.findViewById(R.id.user_image_view).setBackgroundResource(
						R.drawable.ic_action_face_unlock);
				((TextView) view.findViewById(R.id.user_name_text_view)).setText(name);
				((TextView) view.findViewById(R.id.user_last_name_text_view)).setText(lastName);

				return view;
			}
		});
	}
}
