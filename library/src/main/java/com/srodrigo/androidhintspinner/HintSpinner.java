/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Provides methods to work with a hint element. The method setCallback must be called to manage
 * the On Item Selected events.
 */
public class HintSpinner<T> extends Spinner {
	private static final String TAG = HintSpinner.class.getSimpleName();
	private HintAdapter adapter;
	private Callback<T> callback;

	public HintSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initAdapter(HintAdapter adapter) {
		this.adapter = adapter;
		setAdapter(adapter);
		selectHint();
		setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d(TAG, "position selected: " + position);
				if (HintSpinner.this.callback == null) {
					throw new IllegalStateException("callback cannot be null");
				}
				if (!isHintPosition(position)) {
					HintSpinner.this.callback.onItemSelected(position, (T) getItemAtPosition(position));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.d(TAG, "Nothing selected");
			}
		});
	}

	private boolean isHintPosition(int position) {
		return adapter.isHintEnabled() && position == adapter.getHintPosition();
	}

	public void selectHint() {
		adapter.addHint();
		setSelection(adapter.getHintPosition());
	}

	public void setCallback(Callback<T> callback) {
		this.callback = callback;
	}

	public interface Callback<T> {
		void onItemSelected(int position, T itemAtPosition);
	}
}

