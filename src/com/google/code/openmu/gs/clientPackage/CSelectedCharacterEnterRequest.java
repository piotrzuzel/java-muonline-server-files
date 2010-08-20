package com.google.code.openmu.gs.clientPackage;

import java.io.IOException;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.muObjects.MuPcInstance;


public class CSelectedCharacterEnterRequest extends ClientBasePacket {

	public CSelectedCharacterEnterRequest(byte[] decrypt, ClientThread c)
			throws IOException {
		super(decrypt);
		final String selected = SelectedName();
		final MuPcInstance cha = c.loadCharFromDisk(selected);

		System.out.println("Selected Character: " + selected);
		c.setActiveChar(cha);

	}

	public String SelectedName() {
		return readS(2, 10);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
