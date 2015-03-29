/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinner;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

public class TestHintAdapter extends HintAdapter {

	public TestHintAdapter(Context context, String hint, List data) {
		super(context, hint, data);
	}

	@Override
	protected void setDropDownViewText(int position, TextView dropDownView) {
		// Empty
	}
}
