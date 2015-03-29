/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package com.srodrigo.androidhintspinnersample;

import java.util.Random;

/**
 * Model class.
 */
public class User {
	private String name;
	private String lastName;

	private static Random random = new Random();

	public static User generateRandom() {
		int num = Math.abs(random.nextInt());
		return new User("Random user", String.valueOf(num));
	}

	public User(String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}
