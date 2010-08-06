/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.clientPackage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuNpcInstance;
import net.sf.jmuserver.gs.muObjects.MuWorld;

/**
 * 
 * @author Miki i Linka
 */
public class CNpcRunRequest extends ClientBasePacket {

	private int _idNpcToRun = 0;

	public CNpcRunRequest(byte[] decrypt, ClientThread _client) {

		super(decrypt);
		_idNpcToRun = decrypt[1];
		System.out.print("Request to npc it :" + _idNpcToRun + " ");
		final Object o = MuWorld.getInstance().getObject(_idNpcToRun);
		if (o instanceof MuNpcInstance) {
			final MuNpcInstance npc = (MuNpcInstance) o;
			System.out.println("object name '" + npc.getName() + "'");
		} else {
			System.out
					.println("error objct requested is not kind of munpcinstance!!");
		}
		try {

			_client.getConnection().sendPacket(
					new byte[] { (byte) 0xc1, (byte) 0x07, (byte) 0xf3,
							(byte) 0x40, (byte) 0x01, (byte) 0x1b, 0x00 });
			_client.getConnection().sendPacket(
					new byte[] { (byte) 0xc3, (byte) 0x07, (byte) 0x30,
							(byte) 0xb9, (byte) 0xf7, (byte) 0xf9, (byte) 0x5c // ,(byte)0xb5
					});
			_client.getConnection().sendPacket(
					new byte[] { (byte) 0xc2, (byte) 0x00, (byte) 0x9c,
							(byte) 0x31, (byte) 0x00, (byte) 0x19, (byte) 0x00,
							(byte) 0x8d, (byte) 0x00, (byte) 0xff, (byte) 0x80,
							(byte) 0x00, (byte) 0x01, (byte) 0x87, (byte) 0x00,
							(byte) 0xff, (byte) 0x80, (byte) 0x00, (byte) 0x02,
							(byte) 0x8e, (byte) 0x00, (byte) 0xff, (byte) 0x80,
							(byte) 0x00, (byte) 0x03, (byte) 0x93, (byte) 0x00,
							(byte) 0xff, (byte) 0x80, (byte) 0x00, (byte) 0x04,
							(byte) 0x8c, (byte) 0x00, (byte) 0xff, (byte) 0x80,
							(byte) 0x00, (byte) 0x05, (byte) 0x90, (byte) 0x00,
							(byte) 0xff, (byte) 0x80, (byte) 0x00, (byte) 0x06,
							(byte) 0x95, (byte) 0x00, (byte) 0x00, (byte) 0x80,
							(byte) 0x00, (byte) 0x07, (byte) 0x96, (byte) 0x00,
							(byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x08,
							(byte) 0x97, (byte) 0x00, (byte) 0x00, (byte) 0x80,
							(byte) 0x00, (byte) 0x09, (byte) 0x98, (byte) 0x00,
							(byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x0a,
							(byte) 0x00, (byte) 0x04, (byte) 0xff, (byte) 0x00,
							(byte) 0x00, (byte) 0x0b, (byte) 0x01, (byte) 0x04,
							(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x0c,
							(byte) 0x02, (byte) 0x04, (byte) 0xff, (byte) 0x00,
							(byte) 0x00, (byte) 0x0d, (byte) 0x03, (byte) 0x84,
							(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x16,
							(byte) 0x04, (byte) 0x84, (byte) 0xff, (byte) 0x00,
							(byte) 0x00, (byte) 0x17, (byte) 0x05, (byte) 0x84,
							(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x18,
							(byte) 0x06, (byte) 0x84, (byte) 0xff, (byte) 0x00,
							(byte) 0x00, (byte) 0x19, (byte) 0x07, (byte) 0x84,
							(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x1a,
							(byte) 0x08, (byte) 0x84, (byte) 0xff, (byte) 0x00,
							(byte) 0x00, (byte) 0x23, (byte) 0x09, (byte) 0x84,
							(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x2d,
							(byte) 0x0a, (byte) 0x84, (byte) 0xff, (byte) 0x00,
							(byte) 0x00, (byte) 0x30, (byte) 0x0b, (byte) 0x84,
							(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x3a,
							(byte) 0x0c, (byte) 0x84, (byte) 0xff, (byte) 0x00,
							(byte) 0x00, (byte) 0x2f, (byte) 0x0d, (byte) 0x84,
							(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x3c,
							(byte) 0x0e, (byte) 0x84, (byte) 0xff, (byte) 0x00,
							(byte) 0x00 });
			// _client.getConnection().sendPacket(new byte[]{
			// (byte) 0xc1, (byte) 0x06, (byte) 0x14,
			// (byte) 0x01, (byte) 0x03, (byte) 0x32
			// });
		} catch (final IOException ex) {
			Logger.getLogger(CNpcRunRequest.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	@Override
	public String getType() {
		return "0x30 Run Npc request";
	}
}
