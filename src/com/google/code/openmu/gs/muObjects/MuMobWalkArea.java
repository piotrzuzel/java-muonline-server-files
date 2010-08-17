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

/**
 * 
 * @author Miki i Linka
 */
class MuMobWalkArea {

	private final int _radius;

	public MuMobWalkArea(int x1, int y1, int x2, int y2, int rad) {
		_radius = rad;
	}

	public int getRandX(int actX) {
		int tx = tx = actX
				+ ((int) (Math.random() * (_radius * 2)) - (_radius));
		// while (!((_x1<tx)&&(tx<_x2))) {
		// tx = actX + ((int) (Math.random() * (_radius * 2)) - (_radius));
		// System.out.println("newx "+_x1+"<"+tx+"<"+_x2);
		// }
		return tx;
	}

	public int getRandY(int actY) {
		final int ty = actY
				+ ((int) (Math.random() * (_radius * 2)) - (_radius));
		// while (!((_y1<ty)&&(ty<_y2))) {
		// ty = actY + ((int) (Math.random() * (_radius * 2)) - (_radius));
		// System.out.println("newy "+_y1+">"+ty+"<"+_y2);
		// }
		return ty;
	}
}
