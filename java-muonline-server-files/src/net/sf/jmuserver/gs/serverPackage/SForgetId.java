package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import net.sf.jmuserver.gs.muObiects.MuObiect;

/**
 * @author Miki
 * @opis
 * paczka z lista id'ow do zapomnienia
 * @example 
 *  c1 16 14 01 00 01 00 <br>
 *  xx xx xx x1 x2 x2 x3
 *  <br>
 *  x1 - ilosc idow do zaponienia
 *  <br> x2-id do zapomnienia
 *  <br> x3 --bit 00 stopu
 */
public class SForgetId extends ServerBasePacket {

    private ArrayList<MuObiect> _ids = new ArrayList<MuObiect>();

    /**
     * single id constructor
     * @param i
     */
    public SForgetId(MuObiect i) {
        _ids.add(i);
    }

    /**
     * constructor with grup ids to forget
     * @param ids
     */
    public SForgetId(ArrayList<MuObiect> ids) {
        _ids.addAll(ids);
    }

    @Override
    public byte[] getContent() throws IOException {
        System.out.println("ToForgetId Run");
        
        mC1Header(0x14, 0x04 + (_ids.size() * 2));
        writeC(_ids.size()); //Count of ids to forget
        for (MuObiect integer : _ids) {
            writeC(integer.getObjectId() >> 8);
            writeC(integer.getObjectId() & 0x00FF);
        }
        return _bao.toByteArray();
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean testMe() {
        // TODO Auto-generated method stub
        return false;
    }
}
