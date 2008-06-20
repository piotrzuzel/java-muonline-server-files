package net.sf.jmuserver.gs.clientPackage;

import java.io.IOException;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.serverPackage.SDeleteChar;
import net.sf.jmuserver.gs.database.MuCharacterListDB;

/**
 *
 * @author Marcel
 * @ToDo: Verify personalcode, verify if player is guild master.
 */
public class CDeleteChar extends ClientBasePacket {
    private String _personalcode;
    private String _name;
    
    public CDeleteChar(byte[] decrypt, ClientThread _client) throws IOException, Throwable {
        super(decrypt);
        int result = 0x01;
        _name = readS(2,10);
        _personalcode = readS(12,7);
        
        if (_personalcode.length() != 7)
            result = 0x02;
        if (result == 0x01) {
            _client.getChList().removeChar(_name);
            MuCharacterListDB cdb = new MuCharacterListDB(_client.getUser().getId());
            cdb.removeCharacterFromDB(_name);
        }
        _client.getConnection().sendPacket(new SDeleteChar(_name,result));
    }
    
    @Override
    public String getType() {
	return null;
    }
}
