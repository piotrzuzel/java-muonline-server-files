package net.sf.jmuserver.gs.clientPackage;

import java.io.IOException;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuCharacterBase;
import net.sf.jmuserver.gs.muObjects.MuCharacterWear;
import net.sf.jmuserver.gs.serverPackage.SNewCharacterAnsfer;

public class CNewCharacterRequest extends ClientBasePacket {

	private String _name;
	private short _class;
	public CNewCharacterRequest(byte[] decrypt, ClientThread _client) throws IOException, Throwable {
            super(decrypt);
            _name = readS(2, 10);
            _class=decrypt[12];
            System.out.println(decrypt.length);
            System.out.println("Create Character '"+_name+ "' Reuest class = "+_class);
            MuCharacterBase newCB = new  MuCharacterBase(_name,1,_class,5,new MuCharacterWear());
            _client.getChList().addNew(newCB);
            // TODO: Save char in DB, and pass result to SNewCharacterAnsfer (2nd param)
            // (use stored proc add_new_character())
            _client.getConnection().sendPacket(new SNewCharacterAnsfer(newCB, (short)1));
        }

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
