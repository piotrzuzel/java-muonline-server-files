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

package com.google.code.openmu.gs.commands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Miki i Linka
 */
public class CmdOpenTookWnd extends GsBaseCommand {
	byte[] packet = { (byte) 0xc1, (byte) 0x25, (byte) 0xca, (byte) 0x31,
			(byte) 0x32, (byte) 0x38, (byte) 0x2e, (byte) 0x30, (byte) 0x2e,
			(byte) 0x30, (byte) 0x2e, (byte) 0x31, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			(byte) 0x01, (byte) 0x03, (byte) 0x00, (byte) 0x44, (byte) 0x05,
			(byte) 0x00, (byte) 0x6d, (byte) 0x69, (byte) 0x6b, (byte) 0x69,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x01, (byte) 0xf6 };

	@Override
	public boolean RunCommand() {
		try {
			_cli.getConnection().sendPacket(packet);
		} catch (final IOException ex) {
			Logger.getLogger(CmdOpenTookWnd.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return true;
	}

	@Override
	public String getCmdString() {
		return "took";
	}

	@Override
	public String getHelpToCommand() {
		return "Open window chat window";
	}

	@Override
	public String getShortDesc() {
		return "Open chat window";
	}

}
