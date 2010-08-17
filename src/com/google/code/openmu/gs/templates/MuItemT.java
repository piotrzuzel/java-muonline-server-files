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
package com.google.code.openmu.gs.templates;

/**
 * tesst class for item
 * 
 * @author Miki i Linka
 */
public class MuItemT {

	private final byte[] _item = { 0, 0, 0, 0, 0 }; // 5 bites

	public MuItemT() {
	}

	/**
	 * constructor wich init bytes from b
	 * 
	 * @param b
	 */
	public MuItemT(byte[] b) {
		for (int i = 0; i < 5; i++) {
			_item[i] = b[i];
		}
	}

	/**
	 * read item from byte buffor
	 * 
	 * @param buf
	 *            buffor with item
	 * @param pos
	 *            position in buffor
	 */
	public void ReadItem(byte[] buf, int pos) {
		for (int i = 0; i <= 5; i++) {
			_item[i] = buf[i + pos];
		}
	}

	/**
	 * set item bytes from buff
	 * 
	 * @param buf
	 */
	public void setItem(byte[] buf) {
		for (int i = 0; i <= 5; i++) {
			_item[i] = buf[i];
		}
	}

	/**
	 * 
	 * get the bytesrepresentings item
	 * 
	 * @return
	 */
	public byte[] getItem() {
		return _item;
	}
}
