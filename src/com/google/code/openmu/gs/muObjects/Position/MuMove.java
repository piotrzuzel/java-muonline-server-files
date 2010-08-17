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
package com.google.code.openmu.gs.muObjects.Position;

import java.util.ArrayList;

/**
 * The MuMove Class contines the struct ofmoving list ofcharacter th the
 * direction and sterpsof moving
 * 
 * @author Miki i Linka
 * @TODO Write going steps depends time using to got the last one wsp and also
 *       change moving quene when player change path request in half way
 */
public class MuMove {

	private int i;// the count of done
	private ArrayList<MoveStep> _steps; // the list of steps

	/**
	 * geting finalposition afterall steps
	 * 
	 * @param old
	 * @return final position
	 */
	public MuWsp getFinalPosition(MuWsp old) {
		for (int x = 0; x < _steps.size(); x++) {
			old = getNewPosition(old);
		}
		return old;
	}

	/**
	 * getting next position of moving quene
	 * 
	 * @param old
	 *            actual positions
	 * @return new actualizedposition ae its this same instace what old
	 */
	public MuWsp getNewPosition(MuWsp old) {
		if (_steps.size() == i) { // wegetthe last one
			_steps.clear(); // clear fifo array
			i = 0; // reatart counter
			return old; // return wsp unchanged
		}

		old.add(_steps.get(i).CalculateStepPosition()); // getting actualize wsp
		i++; // incrase pointer to last
		return old;
	}

	/**
	 * added new instance from gettes valuse from byte
	 * 
	 * @param b
	 */
	public void addFromByte(byte b) {
		final int s = b & 0xff;
		final int diection = s >> 4;
		final int steps = s % 8;
		_steps.add(new MoveStep((byte) diection, (short) steps));

	}

	/**
	 * added new instance from values
	 * 
	 * @param direction
	 *            where go
	 * @param steps
	 *            how many steps
	 */
	public void add(byte direction, short steps) {
		_steps.add(new MoveStep(direction, steps));
	}

	/**
	 * add existing instance of MoveStep to move quene
	 * 
	 * @param m
	 *            move step instancne
	 */
	public void addExist(MoveStep m) {
		_steps.add(m);
	}

	/**
	 * inner class for definie moving steps;
	 */
	public class MoveStep {

		byte _diretion;
		short _sepCount;

		/**
		 * 
		 * @return cout of steps
		 */
		public short getStepCount() {
			return _sepCount;
		}

		/**
		 * 
		 * @return direction of going
		 */
		public byte getDirection() {
			return _diretion;
		}

		/**
		 * @return wsp modyficator
		 */
		MuWsp CalculateStepPosition() {
			int upx = 0;
			int upy = 0;
			final int steps = getStepCount();
			switch (getDirection()) {
			case 0x00:
				upx = steps;
			case 0x01:
				upx = steps;
				upy = steps;
				break;
			case 0x02:
				upx = steps;
				break;
			case 0x03:
				upx = steps;
				upy = -steps;
				break;
			case 0x04:
				upy = -steps;
				break;
			case 0x05:
				upx = -steps;
				upy = -steps;
				break;
			case 0x06:
				upx = -steps;
				break;
			case 0x07:
				upx = -steps;
				upy = steps;
				break;
			}
			return new MuWsp(upx, upy, getDirection());
		}

		public MoveStep(byte _diretion, short _sepCount) {
			this._diretion = _diretion;
			this._sepCount = _sepCount;
		}
	}
}
