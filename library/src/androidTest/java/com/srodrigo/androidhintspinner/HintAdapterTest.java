/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinner;

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
public class HintAdapterTest {

	public static final String HINT_TEXT = "hint text";

	private HintAdapter createEmptyTestHintAdapter() {
		return createTestHintAdapter(new ArrayList<User>());
	}

	private HintAdapter createTestHintAdapter(List<User> users) {
		return new TestHintAdapter(Robolectric.application, HINT_TEXT, users);
	}

	private List<User> createUserList() {
		List<User> users = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			users.add(User.generateRandom());
		}
		return users;
	}

	@Test
	public void testEmptyAdapterShouldHaveNoElements() throws Exception {
		HintAdapter testHintAdapter = createEmptyTestHintAdapter();

		Assert.assertEquals(0, testHintAdapter.getCount());
	}

	@Test
	public void testEmptyAdapterShouldContainHintAtZero() throws Exception {
		HintAdapter testHintAdapter = createEmptyTestHintAdapter();

		Assert.assertEquals(0, testHintAdapter.getHintPosition());
	}

	@Test
	public void testNonEmptyAdapterShouldContainAllElements() throws Exception {
		List<User> userList = createUserList();
		HintAdapter testHintAdapter = createTestHintAdapter(userList);

		Assert.assertEquals(userList.size(), testHintAdapter.getCount());
	}

	@Test
	public void testNonEmptyAdapterShouldContainHintAtLastPosition() throws Exception {
		List<User> userList = createUserList();
		HintAdapter testHintAdapter = createTestHintAdapter(userList);

		int hintPosition = testHintAdapter.getHintPosition();
		Assert.assertEquals(userList.size(), hintPosition);
	}

	@Test
	public void testUpdateDataShouldAddAllElements() throws Exception {
		HintAdapter testHintAdapter = createEmptyTestHintAdapter();

		List<User> userList = createUserList();
		testHintAdapter.updateData(userList);

		Assert.assertEquals(userList.size(), testHintAdapter.getCount());
	}

}