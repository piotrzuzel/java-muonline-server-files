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

import java.util.HashMap;

import com.google.code.openmu.gs.templates.MuNpc;


public class MuAtackableInstance extends MuNpcInstance {

	private boolean _active;
	private final HashMap _aggroList = new HashMap();

	public MuAtackableInstance(MuNpc tem) {
		super(tem);
		// _live=new MuHeal(50,3);
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean b) {
		_active = b;
	}

	/**
	 * @hmm
	 */
	@Override
	public void IDie() {
		super.IDie();
		System.out.println("i'm day in atackable instance");
	}
}
