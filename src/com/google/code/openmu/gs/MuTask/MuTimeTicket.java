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
package com.google.code.openmu.gs.MuTask;

import com.google.code.openmu.utils.MuTimeCotroler;

/**
 * The TimeTicket class
 * 
 * @author Miki i Linka
 * @version
 */
public class MuTimeTicket {

	private final int tick;

	/**
	 * set ticket to s sec
	 * 
	 * @param s
	 *            how long ticket is ok
	 */
	public MuTimeTicket(int s) {
		tick = MuTimeCotroler.getInstance().getGameTime() + s;
	}

	/**
	 * 
	 * @return true when ticket expired
	 */
	public boolean TicketEnd() {
		return tick < MuTimeCotroler.getInstance().getGameTime();
	}

	/**
	 * 
	 * @return time when ticket expired
	 */
	public int getTime() {
		return tick;
	}

	/**
	 * compare 2 tickets
	 * 
	 * @param a
	 *            1'st ticket
	 * @param b
	 *            2'nd ticket
	 * @return true when 1'st ticket expired before secend
	 */
	public boolean compareTicket(MuTimeTicket a, MuTimeTicket b) {
		return a.getTime() < b.getTime();
	}
}
