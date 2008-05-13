/*
 * HexPacket.java
 *
 * Created on 12 grudzieñ 2007, 11:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package PacEditor.Definitations;

import net.sf.jmuserver.gs.clientPackage.ClientBasePacket;
import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;

/**
 *
 * @author Miki
 */
public class HexPacket extends ClientBasePacket {

  

    public HexPacket(ClientBasePacket p) {
        super(p.toByteArray());
    }

    public HexPacket(ServerBasePacket p) {
        super(p.getBytes());
    }

    public HexPacket(byte[] dec) {
        super(dec);
    }

    public int Size() {
        System.out.println("test");
        return toByteArray().length;
        
    }

    @Override
    public String getType() {
        return "nic";
    }

    public byte getElementAt(int el) {
        return _decrypt[el];
    }

    public int getPackageType() {
        return _decrypt[0];
    }

    public String[] ConvertToHex() {
        String[] _ret = new String[Size()];
        for (int t = 0; t < Size(); t++) {
            int b = _decrypt[t] & 0xff;
            String _ret1 = Integer.toHexString(b).toUpperCase();
            for (int i = _ret1.length(); i < 2; i++) {
                _ret1 = "0" + _ret1;
            }
            _ret[t] = _ret1;
        }
        return _ret;
    }
  
    public String[] ConvertToInt() {
        String[] _ret =new String[Size()];;
        for (int t = 0; t < Size(); t++) {
            String _ret1 = ""+((int) _decrypt[t] & 0xff);
            for (int i = _ret1.length(); i < 3; i++) _ret1 = "0" + _ret1;
		
            _ret[t]=_ret1;

        }
        return _ret;
    }
    public String ConvertToStr() {
        StringBuffer _ret = new StringBuffer();
        for (int a = 0; a < Size(); a++) {
            int t1 = _decrypt[a];
            if (t1 > 0x1f && t1 < 0x80) {
                _ret.append((char) t1);
            } else {
                _ret.append('.');
            }
        }

        return _ret.toString();
    }
    
}
