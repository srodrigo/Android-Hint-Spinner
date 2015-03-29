package com.srodrigo.androidhintspinner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by srodrigo on 27/3/15.
 */
public class HintSpinner<T> extends Spinner {

    private static final String TAG = HintSpinner.class.getSimpleName();

    public interface Callback<T> {
        void onItemSelected(int position, T itemAtPosition);
    }

    private HintAdapter adapter;

    private Callback<T> callback;

    public HintSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // TODO: Remove callback
    public void initAdapter(HintAdapter adapter) {
        this.adapter = adapter;
        setAdapter(adapter);
        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        if (adapter.isHintEnabled()) {
            return position == adapter.getHintPosition();
        }
        return false;
    }

    public void selectHint() {
        adapter.addHint();
        setSelection(adapter.getHintPosition());
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }
}

