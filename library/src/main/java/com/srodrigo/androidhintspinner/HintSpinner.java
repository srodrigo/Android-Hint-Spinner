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

import java.util.List;

/**
 * Provides methods to work with a hint element. The method setCallback must be called to manage
 * the On Item Selected events.
 */
public class HintSpinner<T> extends Spinner {
	private static final String TAG = HintSpinner.class.getSimpleName();

	/**
	 * Used to handle the spinner events.
	 *
	 * @param <T> Type of the data used by the spinner
	 */
	public interface Callback<T> {
		/**
		 * When a spinner item has been selected.
		 *
		 * @param position Position selected
		 * @param itemAtPosition Item selected
		 */
		void onItemSelected(int position, T itemAtPosition);
	}

	private HintAdapter adapter;
	private Callback<T> callback;

	public HintSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Initializes the spinner.
	 *
	 * By default, the hint is selected when calling this method.
	 *
	 * @param adapter
	 */
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
		return position == adapter.getHintPosition();
	}

	/**
	 * Selects the hint element.
	 */
	public void selectHint() {
		setSelection(adapter.getHintPosition());
	}

	/**
	 * Updates the spinner data with the data provided.
	 *
	 * @param newData Data used to update the spinner
	 */
	public void updateData(List<T> newData) {
		adapter.updateData(newData);
		setAdapter(adapter);
	}

	public void setCallback(Callback<T> callback) {
		this.callback = callback;
	}
}

