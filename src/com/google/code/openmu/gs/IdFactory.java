/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu.gs;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class IdFactory {

	private static IdFactory _instance = null;
	private final Random random = new Random();

	/**
	 * private costructor
	 */
	private IdFactory() {
	}

	/**
	 * @return instance of IfFactory
	 */
	public static IdFactory getInstance() {
		if (_instance == null) {
			_instance = new IdFactory();
		}
		return _instance;
	}

	private final Set<Integer> _set = new HashSet<Integer>();

	/**
	 * Sherch for new Id
	 * 
	 * @return new id
	 */
	public int newId() {
		int found = 1;
		int t = 1 + (int) (Math.random() * 250);

		while (_set.contains(t)) {
			t = 1 + (int) (Math.random() * 250);
			found++;
		}
		_set.add(t);
		System.out.println("|IdFactory: Found  new id [" + t + "] on " + found
				+ " time");
		return t;
	}

	/**
	 * remove id from set
	 * 
	 * @param id
	 *            to remove
	 */
	public void deleteId(int id) {
		_set.remove(id);
	}
}
