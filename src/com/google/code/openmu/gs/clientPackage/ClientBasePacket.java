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

package com.google.code.openmu.gs.clientPackage;

import java.util.logging.Logger;

/**
 * This class ...
 * 
 * @version $Revision: 1.5 $ $Date: 2004/10/23 17:26:18 $
 */
public abstract class ClientBasePacket {
	static Logger _log = Logger.getLogger(ClientBasePacket.class.getName());
	private final byte[] d3key = { (byte) 0xfc, (byte) 0xcf, (byte) 0xab };
	protected byte[] _decrypt;

	protected int _off;

	public ClientBasePacket(byte[] decrypt) {
		_log.finest(getType());
		_decrypt = decrypt;
		_off = 3; // skip packet type id
	}

	public int readD() {
		int result = _decrypt[_off++] & 0xff;
		result |= _decrypt[_off++] << 8 & 0xff00;
		result |= _decrypt[_off++] << 0x10 & 0xff0000;
		result |= _decrypt[_off++] << 0x18 & 0xff000000;
		return result;
	}

	public int readC() {
		final int result = _decrypt[_off++] & 0xff;
		return result;
	}

	public int readH() {
		int result = _decrypt[_off++] & 0xff;
		result |= _decrypt[_off++] << 8 & 0xff00;
		return result;
	}

	public int readH(int of) {
		int result = _decrypt[of] & 0xff;
		result |= _decrypt[of + 1] << 8 & 0xff00;
		return result;
	}

	public double readF() {
		long result = _decrypt[_off++] & 0xff;
		result |= _decrypt[_off++] << 8 & 0xff00;
		result |= _decrypt[_off++] << 0x10 & 0xff0000;
		result |= _decrypt[_off++] << 0x18 & 0xff000000;
		result |= (long) _decrypt[_off++] << 0x20 & 0xff00000000l;
		result |= (long) _decrypt[_off++] << 0x28 & 0xff0000000000l;
		result |= (long) _decrypt[_off++] << 0x30 & 0xff000000000000l;
		result |= (long) _decrypt[_off++] << 0x38 & 0xff00000000000000l;
		return Double.longBitsToDouble(result);
	}

	public String readS(int from, int to) {
		String result = null;
		try {
			result = new String(_decrypt, from, to, "ISO-8859-1");
			// System.out.println(result+"\nl:"+result.length()+"\n"+result.indexOf(0x00));

			if (result.indexOf(0x00) != -1) {
				result = result.substring(0, result.indexOf(0x00));
			}
		} catch (final Exception e) {
			result = "";
		}

		_off += result.length() * 2 + 2;
		return result;
	}

	public byte[] readB(int length) {
		final byte[] result = new byte[length];
		System.arraycopy(_decrypt, _off, result, 0, length);
		_off += length;

		return result;
	}

	public byte[] toByteArray() {
		return _decrypt;
	};

	public void Dec3bit(int start, int len) {
		for (int i = start; i < start + len; i++) {
			_decrypt[i] = (byte) (_decrypt[i] ^ d3key[(i - start) % 3]);
		}
	}

	public abstract String getType();
}
