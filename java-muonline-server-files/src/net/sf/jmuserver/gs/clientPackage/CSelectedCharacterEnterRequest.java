package net.sf.jmuserver.gs.clientPackage;
import java.io.IOException;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObiects.MuPcInstance;





public class CSelectedCharacterEnterRequest extends ClientBasePacket {

	public CSelectedCharacterEnterRequest(byte[] decrypt, ClientThread c)
			throws IOException {
		super(decrypt);
		String selected = SelectedName();
		MuPcInstance cha = c.loadCharFromDisk(selected);
		
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
