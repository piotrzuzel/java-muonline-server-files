package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.ArrayList;

import net.sf.jmuserver.gs.muObjects.MuMonsterInstance;
import net.sf.jmuserver.gs.muObjects.MuObject;

public class SNpcMiting extends ServerBasePacket {

    ArrayList<MuObject> _newNpc;

    public SNpcMiting(ArrayList<MuObject> newNpc) {
        _newNpc = newNpc;
    }

    @Override
    public byte[] getContent() throws IOException {
        makeHead(_newNpc.size());
        for (int i = 0; i < _newNpc.size(); i++) {
			System.out.println("Obj id"+_newNpc.get(i).getObjectId());
            makeSub((MuMonsterInstance) _newNpc.get(i));


        }
        return _bao.toByteArray();

    }

    private void makeHead(int l) {
        //0xc2,0x00,0xFF,0x13,0xFF
        int size = 5 + (l * 12);  //size of pakage
        mC2Header(0x13, l, size);

    }

    private void makeSub(MuMonsterInstance m) {
        writeC(0x00);
        writeC(m.getObjectId());//00 00 
        writeC(0x00);			//00
        writeC(m.getNpcId());	//00
        writeC(0x00);			//00
        writeC(0x00);			//00
        writeC(m.getOldX());		//00
        writeC(m.getOldY());		//00
        writeC(m.getX());	//00
        writeC(m.getY());	//00
        writeC(m.getStatus());	//00
        writeC(0x00);			//00
    //12 bits total
    }

    @Override
    public String getType() {

        return "13 SNpcMiting";
    }

    @Override
    public boolean testMe() {

        return false;
    }
}
