package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.ClientThread;

public class CMoveItemRequest extends ClientBasePacket {
//23 ad 7f 0c
    /**
     * @param decrypt
     * @param _client 
     * @example
     * 24 00 01 c0 00 16 00 00 00 0c
     */
    private int _slotFrom=0;
    private int _slotTo=0;
    private int _windowFrom=0;
    private int _windowTo=0;
    public CMoveItemRequest(byte[] decrypt, ClientThread _client) {
       
	super(decrypt);
        
	// 24 00 01 c0 00 16 00 00 00 0c
        // 24 00 0c e3 00 00 80 00 00 14
        _windowFrom=decrypt[1];
        _windowTo=decrypt[8];
        _slotFrom=decrypt[2];
        _slotTo=decrypt[9];
        System.out.println("Move Item Request : from wid["+_slotFrom+"] slot["+_slotFrom + "] to " +
                "wid["+_windowTo+"] slot["+_slotTo+"]");
        
    }

    @Override
    public String getType() {
	// TODO Auto-generated method stub
	return null;
    }

}
