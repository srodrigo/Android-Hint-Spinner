/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinner;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows adding a hint at the end of the list. It will show the hint when adding it and selecting
 * the last object. Otherwise, it will show the dropdown view implemented by the concrete class.
 */
public abstract class HintAdapter extends ArrayAdapter {
	private static final String TAG = HintAdapter.class.getSimpleName();

	private boolean hintAdded;
	private String hintResource;

	public HintAdapter(Context context, int hintResource, List data) {
		this(context, context.getString(hintResource), data);
	}

	public HintAdapter(Context context, String hint, List data) {
		this(context, android.R.layout.simple_spinner_dropdown_item, hint, data);
	}

	protected HintAdapter(Context context, int layoutResource, String hintResource, List data) {
		// Create a copy, as we need to be able to add the hint without modifying the array passed in
		// or crashing when the user sets an unmodifiable.
		super(context, layoutResource, new ArrayList(data));
		this.hintResource = hintResource;
		addHint();
	}

	private void addHint() {
		// Prevent adding the hint more than once
		if (!hintAdded) {
			Log.d(TAG, "Adding hint object");
			add(new Object());
			hintAdded = true;
		}
	}

	/**
	 * This needs to be called to update the adapter data.
	 *
	 * @param data List of elements
	 */
	public void updateData(List data) {
		clear();
		hintAdded = false;
		if (data != null) {
			addAll(data);
			// Every time we update the adapter data, the hint gets removed, so  we need to add it
			// again.
			addHint();
		}
		notifyDataSetChanged();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		final TextView dropDownView = (TextView) super.getDropDownView(position, convertView, parent);
		setDropDownViewText(position, dropDownView);
		return dropDownView;
	}

	/**
	 * Hook method to set a custom text value.
	 *
	 * @param position Position selected
	 * @param dropDownView Text View
	 */
	protected abstract void setDropDownViewText(int position, TextView dropDownView);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "position: " + position + ", getCount: " + getCount());
		final TextView view = (TextView) super.getView(position, convertView, parent);
		if (position == getCount()) {
			((TextView) view.findViewById(android.R.id.text1)).setText("");
			((TextView) view.findViewById(android.R.id.text1)).setHint(hintResource);
		} else {
			setDropDownViewText(position, view);
		}
		return view;
	}

	/**
	 * Returns the elements count without the hint element.
	 *
	 * @return The number of elements
	 */
	@Override
	public int getCount() {
		int count = super.getCount();
		if (hintAdded) {
			return count > 0 ? count - 1 : count;
		} else {
			return count;
		}
	}

	/**
	 * Gets the position of the hint element. The hint gets added at the end of the list.
	 *
	 * @return Position of the hint element
	 */
	public int getHintPosition() {
		return getCount();
	}
}
