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
package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

import com.google.code.openmu.gs.muObjects.MuPcInstance;

/**
 * 
 * @author Miki i Linka
 */
public class SLvlUp extends ServerBasePacket {

	int Level;
	int LevelUpPoint;
	int MaxLife;
	int MaxMana;
	int MaxBP;
	int AddPoint;
	int MaxAddPoint;

	public SLvlUp(MuPcInstance pc) {
		super();
		Level = pc.getLvl();
		LevelUpPoint = pc.getLp();
		MaxLife = pc.getMaxHp();
		MaxMana = pc.getMaxMp();
		MaxBP = pc.getMaxSp();
		AddPoint = 1; // ??
		MaxAddPoint = 1;// ??
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {
		mC3Header(0xf3, 0x05, 0x12);
		writeI(Level);
		writeI(LevelUpPoint);
		writeI(MaxLife);
		writeI(MaxMana);
		writeI(MaxBP);
		writeI(AddPoint); // ?
		writeI(MaxAddPoint); // ?
		return getBytes();
	}

	@Override
	public String getType() {
		return "";
	}

	@Override
	public boolean testMe() {
		return true;
	}
}
