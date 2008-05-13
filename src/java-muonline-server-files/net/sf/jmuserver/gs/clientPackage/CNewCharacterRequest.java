package net.sf.jmuserver.gs.clientPackage;

import java.io.IOException;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuCharacterBase;
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
	_client.getChList().addNew(new MuCharacterBase(_name,1,_class,5));
	_client.getConnection().sendPacket(
			new SNewCharacterAnsfer(
					new MuCharacterBase("test",1,1,5),
					_off, _class));
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
