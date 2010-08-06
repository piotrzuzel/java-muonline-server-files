/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;

/**
 * 
 * @author Miki
 */
public class CItemDropFromInwentoryRequest extends ClientBasePacket {

	private int _xPos = 0;
	private int _yPos = 0;
	private int _slotFrom = 0;

	public CItemDropFromInwentoryRequest(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		// readC();
		_xPos = decrypt[1] & 0xff;
		_yPos = decrypt[2] & 0xff;
		_slotFrom = decrypt[3] & 0xff;
		// 23 ad 7f 0c
		System.out.println("Drop Request from slot[" + _slotFrom + "] to wsp ["
				+ _xPos + "," + _yPos + "]");
	}// 26 0c 00

	@Override
	public String getType() {
		return "inwentory roop item request";
	}
	// 23 ad 7f 0c
}
