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
public class CmdGiveSheld extends GsBaseCommand {
	byte[] _pact = { (byte) 0xc3, (byte) 0x08, (byte) 0x1e, (byte) 0x9e,
			(byte) 0xa7, (byte) 0x4d, (byte) 0xc3, (byte) 0xbc };
	byte[] _inw = { (byte) 0xc4, (byte) 0x00, (byte) 0x19, (byte) 0xc8,
			(byte) 0x0c, (byte) 0x35, (byte) 0xee, (byte) 0x0e, (byte) 0x5e,
			(byte) 0xa6, (byte) 0xe9, (byte) 0x41, (byte) 0x1c, (byte) 0x29,
			(byte) 0xa4, (byte) 0x4e, (byte) 0x0d, (byte) 0xea, (byte) 0x9d,
			(byte) 0xb5, (byte) 0xc2, (byte) 0xa6, (byte) 0x24, (byte) 0x70,
			(byte) 0x4e };

	// ?? world x y
	byte[] gateans = { (byte) 0xc3, (byte) 0x07, (byte) 0x1e, (byte) 0/* xbe */,
			(byte) 0x80, (byte) 0x80, (byte) 0x0d };
	byte[] giveexp = { (byte) 0xc3, (byte) 0x08, (byte) 0x16, (byte) 0x03,
			(byte) 0x87, (byte) 0xc6, (byte) 0xf0, (byte) 0x00 };
	byte[] _pac1 = { (byte) 0xc3, (byte) 0x09, (byte) 0x24, (byte) 0xad,
			(byte) 0xb2, (byte) 0x9c, (byte) 0xbf, (byte) 0x61, (byte) 0x31,
			(byte) 0xa8 }; // nothing heppend
	byte[] _pac2 = { (byte) 0xc3, (byte) 0x08, (byte) 0x22, (byte) 0xa7,
			(byte) 0x90, (byte) 0x92, (byte) 0x8d, (byte) 0x61, (byte) 0x23 }; // nothing
	byte _pac[] = { (byte) 0xc3, (byte) 0x0c, (byte) 0x24, (byte) 0x00,
			(byte) 0x00, (byte) 0xc0, (byte) 0x00, (byte) 0x16, (byte) 0x00,
			(byte) 0x00, (byte) 0xff, (byte) 0x0c, 0x00 };

	@Override
	public boolean RunCommand() {
		try {
			_cli.getConnection().sendPacket(giveexp);

			// _cli.getConnection().sendPacket(_pac);

		} catch (final IOException ex) {
			Logger.getLogger(CmdGiveSheld.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return true;
	}

	@Override
	public String getCmdString() {
		return "GiveSheld";
	}

	@Override
	public String getHelpToCommand() {
		return "Type \\GiveSheld to get shelg in hand";
	}

	@Override
	public String getShortDesc() {
		return "Add Sheld to inwentory ";
	}

}
