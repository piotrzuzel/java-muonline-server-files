/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
