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
 * 
 * @author Miki
 */
public interface MuItemOptBits {
	byte IT_NORM = 0x00; // 00000000b
	byte IT_OPTp4 = 0x01; // 00000001b
	byte IT_OPTp8 = 0x02; // 00000010b
	byte IT_OPTp12 = 0x03; // 00000011b
	byte IT_LUCK = 0x04; // 00000100b
	byte IT_LVL1 = 0x08; // 00001000b
							// 00010000b
							// 00100000b
	// 01000000b
	// 01111
	byte IT_SKILL = (byte) 0x80; // 10000000b
	byte IT_BIT_OPT = 1;

	int getOption();

	int getLvl();

	boolean isLuck();

	boolean isSkill();

	void setOption(int opt);

	void setLvl(int lvl);

	void setLuck();

	void setSkill();

}
