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

public class MuUser {

	/**
     *
     */
	private final String user;
	private final String pass;
	private int id;
	private int ch_c;
	int flag;
	private final String p_code; // code to delete chars

	public MuUser() {
		user = "";
		pass = "";
		flag = 0;
		p_code = "";
	}

	public MuUser(int u_id, String us, String pa, int f, int u_ch_c,
			String ch_code) {
		id = u_id;
		ch_c = u_ch_c;
		user = us;
		flag = f;
		pass = pa;
		p_code = ch_code;
	}

	/**
	 * @return the ch_c
	 */
	public int getCh_c() {
		return ch_c;
	}

	public int getFlag() {
		return flag;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	public String getUser() {
		return user;
	}

	public String getChCode() {
		return p_code;
	}
}
