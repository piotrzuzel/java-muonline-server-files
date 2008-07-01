package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;
import java.util.Vector;

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

    private Vector<Integer> _ids = new Vector<Integer>();

    /**
     * single id constructor
     * @param i
     */
    public SForgetId(int i) {
        _ids.add(i);
    }

    /**
     * constructor with grup ids to forget
     * @param ids
     */
    public SForgetId(Vector<Integer> ids) {
        _ids.addAll(ids);
    }

    @Override
    public byte[] getContent() throws IOException {
        System.out.println("ToForgetId Run");
        
        mC1Header(0x14, 0x04 + (_ids.size() * 2));
        writeC(_ids.size()); //Count of ids to forget
        for (Integer integer : _ids) {
            writeI(integer);
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
