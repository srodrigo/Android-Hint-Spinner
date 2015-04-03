/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
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
public class HintAdapter extends ArrayAdapter {
	private static final String TAG = HintAdapter.class.getSimpleName();

	private static final int DEFAULT_LAYOUT_RESOURCE = android.R.layout.simple_spinner_dropdown_item;

	private boolean hintAdded;
	private int layoutResource;
	private String hintResource;

	private final LayoutInflater layoutInflater;

	public HintAdapter(Context context, int hintResource, List data) {
		this(context, DEFAULT_LAYOUT_RESOURCE, context.getString(hintResource), data);
	}

	public HintAdapter(Context context, String hint, List data) {
		this(context, DEFAULT_LAYOUT_RESOURCE, hint, data);
	}

	public HintAdapter(Context context, int layoutResource, int hintResource, List data) {
		this(context, layoutResource, context.getString(hintResource), data);
	}

	public HintAdapter(Context context, int layoutResource, String hintResource, List data) {
		// Create a copy, as we need to be able to add the hint without modifying the array passed in
		// or crashing when the user sets an unmodifiable.
		super(context, layoutResource, new ArrayList(data));
		this.layoutResource = layoutResource;
		this.hintResource = hintResource;
		this.layoutInflater = LayoutInflater.from(context);
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
		return getCustomView(position, convertView, parent);
	}

	/**
	 * Hook method to set a custom view.
	 *
	 * Provides a default implementation using the simple spinner dropdown item.
	 *
	 * @param position Position selected
	 * @param convertView View
	 * @param parent Parent view group
	 */
	protected View getCustomView(int position, View convertView, ViewGroup parent) {
		View view = inflateDefaultLayout(parent);
		Object item = getItem(position);
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setText(item.toString());
		textView.setHint("");
		return view;
	}

	private View inflateDefaultLayout(ViewGroup parent) {
		return inflateLayout(DEFAULT_LAYOUT_RESOURCE, parent, false);
	}

	private View inflateLayout(int resource, android.view.ViewGroup root, boolean attachToRoot) {
		return layoutInflater.inflate(resource, root, attachToRoot);
	}

	public View inflateLayout(android.view.ViewGroup root, boolean attachToRoot) {
		return layoutInflater.inflate(layoutResource, root, attachToRoot);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "position: " + position + ", getCount: " + getCount());
		View view;
		if (position == getCount()) {
			view = getDefaultView(parent);
		} else {
			view = getCustomView(position, convertView, parent);
		}
		return view;
	}

	private View getDefaultView(ViewGroup parent) {
		View view = inflateDefaultLayout(parent);
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setText("");
		textView.setHint(hintResource);
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
		return count > 0 ? count - 1 : count;
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
