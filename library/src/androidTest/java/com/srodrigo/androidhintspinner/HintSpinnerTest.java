/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class HintSpinnerTest {

	private TestHintSpinner testHintSpinner;
	private MockCallback emptyCallback = new MockCallback();

	private List<User> createUserList() {
		List<User> users = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			users.add(User.generateRandom());
		}
		return users;
	}

	@Before
	public void setUp() throws Exception {
		testHintSpinner = new TestHintSpinner(Robolectric.application, null);
	}

	@Test
	public void testSpinnerSetsAdapterElements() throws Exception {
		List<User> users = createUserList();
		testHintSpinner.initAdapter(users);
		testHintSpinner.setCallback(emptyCallback);

		Assert.assertEquals(users.size(), testHintSpinner.getAdapter().getCount());
	}

	@Test
	public void testSelectHintSelectsLastPosition() throws Exception {
		List<User> users = createUserList();
		testHintSpinner.initAdapter(users);
		testHintSpinner.setCallback(emptyCallback);

		testHintSpinner.selectHint();

		Assert.assertEquals(users.size(), testHintSpinner.getSelectedItemPosition());
	}

	@Test
	public void testAddNewElementsUpdatesElements() throws Exception {
		List<User> users = createUserList();
		testHintSpinner.initAdapter(users);
		testHintSpinner.setCallback(emptyCallback);

		List<User> newUsers = new ArrayList<>(users);
		newUsers.add(new User("new user", "lastname"));
		testHintSpinner.updateData(newUsers);

		Assert.assertEquals(newUsers.size(), testHintSpinner.getCount());
	}

	@Test
	public void testAddNewElementsSelectHint() throws Exception {
		List<User> users = createUserList();
		testHintSpinner.initAdapter(users);
		testHintSpinner.setCallback(emptyCallback);

		List<User> newUsers = new ArrayList<>(users);
		newUsers.add(new User("new user", "lastname"));
		testHintSpinner.updateData(newUsers);
		testHintSpinner.selectHint();

		Assert.assertEquals(newUsers.size(), testHintSpinner.getSelectedItemPosition());
	}

	@Test
	public void testSelectElement() throws Exception {
		List<User> users = createUserList();
		testHintSpinner.initAdapter(users);
		testHintSpinner.setCallback(emptyCallback);

		int selectedPosition = 1;
		testHintSpinner.setSelection(selectedPosition);

		Assert.assertEquals(selectedPosition, testHintSpinner.getSelectedItemPosition());
	}

	@Test
	public void testSelectHintAfterSelectingElement() throws Exception {
		List<User> users = createUserList();
		testHintSpinner.initAdapter(users);
		testHintSpinner.setCallback(emptyCallback);

		testHintSpinner.setSelection(1);
		testHintSpinner.selectHint();

		Assert.assertEquals(users.size(), testHintSpinner.getSelectedItemPosition());
	}

	@Test
	public void testSelectElementShouldFireCallback() throws Exception {
		List<User> users = createUserList();
		testHintSpinner.initAdapter(users);
		HintSpinner.Callback<User> spyCallback = spy(emptyCallback);
		testHintSpinner.setCallback(spyCallback);

		int selectedPosition = 1;
		testHintSpinner.setSelection(selectedPosition);

		verify(spyCallback).onItemSelected(selectedPosition, users.get(selectedPosition));
		verifyNoMoreInteractions(spyCallback);
	}

}