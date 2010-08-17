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

package com.google.code.openmu.gs.muObjects;

import com.google.code.openmu.gs.ClientThread;

/**
 * 
 */

/**
 * @author MikiOne
 * 
 */
public class MuCharacterList {
	/**
	 * 
	 */

	private boolean _needRead = true;
	private final MuCharacterBase[] _chars = { null, null, null, null, null };

	ClientThread _th = null;

	public MuCharacterList() {

	}

	/**
	 * get character base fron name
	 * 
	 * @param _name
	 *            to get
	 * @return character base
	 */
	public MuCharacterBase getChar(String _name) {
		for (final MuCharacterBase muCharacterBase : _chars) {
			if (muCharacterBase != null) {
				if (muCharacterBase.getName().compareTo(_name) == 0) {
					return muCharacterBase;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return firs free slot
	 */
	public byte getFirstFreeSlot() {
		byte i;
		for (i = 0; i < 5; i++) {
			if (_chars[i] == null) {
				break;
			}
		}
		return i;
	}

	public boolean addNew(MuCharacterBase c) {
		final int count = getCharsCount();
		if (count >= 5) {
			return false;
		}
		final byte i = getFirstFreeSlot();
		System.out.println("Added new character to chlist :" + c.getName()
				+ " on pos " + i);
		_chars[i] = c;
		return true;
	}

	public boolean removeChar(String name) {
		byte i;
		for (i = 0; i < 5; i++) {
			if (name.equalsIgnoreCase(_chars[i].getName())) {
				break;
			}
		}
		if (i < 5) {
			_chars[i] = null;
			return true;
		} else {
			return false;
		}
	}

	public MuCharacterBase getChar(int nr) {
		return _chars[nr];
	}

	public int getCharsCount() {
		int count = 0;
		for (byte i = 0; i < 5; i++) {
			if (_chars[i] != null) {
				count++;
			}
		}
		return count;
	}

	public boolean needRead() {
		return _needRead;
	}

	public void noNeedRead() {
		_needRead = false;
	}
}
