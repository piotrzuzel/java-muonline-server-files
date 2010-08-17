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
package com.google.code.openmu.gs.muObjects.Abstract;

import com.google.code.openmu.gs.muObjects.MuMonsterInstance;

/**
 * 
 * @author Miki i Linka
 */
public abstract class AbstractAI {

	private final MuMonsterInstance _instance;

	public AbstractAI(MuMonsterInstance _instance) {
		this._instance = _instance;
	}

	public MuMonsterInstance getMonster() {
		return _instance;
	}

	public static final int ST_IDE = 0;
	public static final int ST_AutoMove = 1;
	public static final int ST_TargetSeek = 2;
	public static final int ST_AtackTarget = 3;

	abstract void StatusUpdate(int status);
}
