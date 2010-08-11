package com.google.code.openmu.gs.clientPackage;

import java.io.IOException;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.muObjects.MuCharacterBase;
import com.google.code.openmu.gs.muObjects.MuCharacterWear;
import com.google.code.openmu.gs.serverPackets.SNewCharacterAnsfer;


public class CNewCharacterRequest extends ClientBasePacket {

	private String _name;
	private int _class;

	public CNewCharacterRequest(byte[] decrypt, ClientThread _client)
			throws IOException, Throwable {
		super(decrypt);
		_name = readS(2, 10);
		_name = _name.trim();
		_class = decrypt[12];
		_class = _class * 2;
		System.out.println(decrypt.length);
		System.out.println("Create Character '" + _name
				+ "' Requested class = " + _class);

		final int position = _client.getChList().getFirstFreeSlot();

		final MuCharacterBase newCB = new MuCharacterBase(_name, 1, _class,
				position, new MuCharacterWear());

		final boolean success = _client.storeNewChar(_client.getUser().getId(),
				_name, _class);
		if (success) {
			_client.getChList().addNew(newCB);
		}
		_client.getConnection().sendPacket(
				new SNewCharacterAnsfer(newCB, success, position));
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
