/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package me.srodrigo.androidhintspinner;

import android.content.Context;
import android.widget.Spinner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class HintSpinnerTest {

	public static final String HINT_TEXT = "Please select a user";

	private HintAdapter<User> hintAdapter;
	private HintSpinner<User> testHintSpinner;
	private Spinner spinner;

	private HintSpinner.Callback<User> emptyCallback = new HintSpinner.Callback<User>() {
		@Override
		public void onItemSelected(int position, User itemAtPosition) {
			// Empty
		}
	};

	private List<User> createUserList() {
		List<User> users = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			users.add(User.generateRandom());
		}
		return users;
	}

	private HintSpinner<User> createHintSpinner(Context context,
	                                            List<User> items,
	                                            HintSpinner.Callback<User> callback) {
		spinner = new Spinner(context, null);
		hintAdapter = new HintAdapter<>(context, HINT_TEXT, items);
		return new HintSpinner<>(
				spinner,
				hintAdapter,
				callback);
	}

	@Test
	public void testSpinnerSetsAdapterElements() throws Exception {
		List<User> users = initHintSpinnerWithUsers();

		Assert.assertEquals(users.size(), hintAdapter.getCount());
	}

	private List<User> initHintSpinnerWithUsers() {
		List<User> users = createUserList();
		testHintSpinner = createHintSpinner(Robolectric.application, users, emptyCallback);
		testHintSpinner.init();
		return users;
	}

	@Test
	public void testSelectHintSelectsLastPosition() throws Exception {
		initHintSpinnerWithUsers();

		testHintSpinner.selectHint();

		Assert.assertEquals(hintAdapter.getHintPosition(), spinner.getSelectedItemPosition());
	}

	@Test
	public void testAddNewElementsUpdatesElements() throws Exception {
		List<User> users = initHintSpinnerWithUsers();

		users.add(new User("new user", "lastname"));

		Assert.assertEquals(users.size(), hintAdapter.getCount());
	}

	@Test
	public void testAddNewElementsSelectHint() throws Exception {
		List<User> users = initHintSpinnerWithUsers();

		users.add(new User("new user", "lastname"));
		testHintSpinner.selectHint();

		Assert.assertEquals(hintAdapter.getHintPosition(), spinner.getSelectedItemPosition());
	}

	@Test
	public void testSelectElement() throws Exception {
		initHintSpinnerWithUsers();

		int selectedPosition = 1;
		spinner.setSelection(selectedPosition);

		Assert.assertEquals(selectedPosition, spinner.getSelectedItemPosition());
	}

	@Test
	public void testSelectHintAfterSelectingElement() throws Exception {
		initHintSpinnerWithUsers();

		spinner.setSelection(1);
		testHintSpinner.selectHint();

		Assert.assertEquals(hintAdapter.getHintPosition(), spinner.getSelectedItemPosition());
	}

}