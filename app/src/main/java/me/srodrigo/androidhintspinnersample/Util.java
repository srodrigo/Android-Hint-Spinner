/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package me.srodrigo.androidhintspinnersample;

import java.util.Random;

public class Util {

	private static Random random = new Random();

	public static int generateRandomPositive() {
		return Math.abs(random.nextInt());
	}
}
