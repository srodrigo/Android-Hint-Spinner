package com.srodrigo.androidhintspinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Allows adding a hint at the end of the list. It will show the hint when adding it and selecting
 * the last object. Otherwise, it will show the dropdown view implemented by the concrete class.
 */
public abstract class HintAdapter extends ArrayAdapter {

	private boolean hintEnabled;
	private String hintResource;

	public HintAdapter(Context context, int layoutResource, int hintResource, List objects) {
		this(context, layoutResource, context.getString(hintResource), objects);
	}

	public HintAdapter(Context context, int layoutResource, String hintResource, List objects) {
		super(context, layoutResource, objects);
		this.hintResource = hintResource;
	}

	public void addHint() {
		// Prevent adding the hint more than once
		if (!isHintEnabled()) {
			addHintObject();
			hintEnabled = true;
		}
	}

	private void addHintObject() {
		add(new Object());
	}

	@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final TextView dropDownView = (TextView) super.getDropDownView(position, convertView, parent);
        setDropDownViewText(position, dropDownView);
        return dropDownView;
    }

    protected abstract void setDropDownViewText(int position, TextView dropDownView);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView view = (TextView) super.getView(position, convertView, parent);
        if (position == getCount()) {
            ((TextView)view.findViewById(android.R.id.text1)).setText("");
            ((TextView)view.findViewById(android.R.id.text1)).setHint(hintResource);
        } else {
            setDropDownViewText(position, view);
        }
        return view;
    }

	@Override
     public int getCount() {
		int count = super.getCount();
		if (hintEnabled) {
			return count > 0 ? count - 1 : count;
		} else {
			return count;
		}
	}

	public boolean isHintEnabled() {
		return hintEnabled;
	}

	public int getHintPosition() {
		return getCount();
	}

	// This is the funny method. Every time we update the adapter data, the hint gets removed, so
	// we need to add it again
	public void updateData(List data) {
		clear();
		if (data != null) {
			addAll(data);
			if (isHintEnabled()) {
				addHintObject();
			}
		}
		notifyDataSetChanged();
	}
}
