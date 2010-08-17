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
 * interface for exe options bits 4 items
 * 
 * @author Miki
 */
public interface MuItemExeBits {

	int EXEOPT1 = 0x01; // 00000001b
	int EXEOPT2 = 0x02; // 00000010b
	int EXEOPT3 = 0x04; // 00000100b
	int EXEOPT4 = 0x08;// 00001000b
	int EXEOPT5 = 0x10; // 00010000b
	int EXEOPT6 = 0x20; // 00100000b
	int IT_p16 = 0x40; // 01000000b
	int IT_LONGID = 0x80; // 10000000b
	int IT_EXE_BIT = 3;

	boolean isExeOpt1();

	boolean isExeOpt2();

	boolean isExeOpt3();

	boolean isExeOpt4();

	boolean isExeOpt5();

	boolean isExeOpt6();

	boolean isOpt_p16();

	boolean isLongId();

	void setExeOpt(byte ExcellentOption);

	void setOpt_p16();

	void setLongId();
}
