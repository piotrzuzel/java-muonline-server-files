/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.ClientThread;

/**
 *
 * @author Miki i Linka
 */
public class CmdGiveSheld extends GsBaseCommand {
byte[] _pact ={(byte)0xc3 ,(byte)0x08 ,(byte)0x1e ,(byte)0x9e ,(byte)0xa7 ,(byte)0x4d ,(byte)0xc3 ,(byte)0xbc
};
    //                                                         ??       world      x          y     
    byte[]gateans={(byte)0xc3 ,(byte)0x07 ,(byte)0x1e ,(byte)0/*xbe*/ ,(byte)0x80 ,(byte)0x80 ,(byte)0x0d};    
     byte[] giveexp   ={(byte)0xc3 ,(byte)0x08 ,(byte)0x16 ,(byte)0x95 ,(byte)0xa1 ,(byte)0xf2 ,(byte)0xf8 ,(byte)0x43 };
    byte [] _pac1= {(byte)0xc3 ,(byte)0x09 ,(byte)0x24 ,(byte)0xad ,(byte)0xb2 ,(byte)0x9c ,(byte)0xbf ,(byte)0x61 ,(byte)0x31 ,(byte)0xa8}; //nothing heppend
    byte  [] _pac2={(byte)0xc3 ,(byte)0x08 ,(byte)0x22 ,(byte)0xa7 ,(byte)0x90 ,(byte)0x92 ,(byte)0x8d ,(byte)0x61 ,(byte)0x23}; // nothing
    byte _pac[] =  {(byte)0xc3 ,(byte)0x0c, (byte)0x24, (byte)0x00, (byte) 0x00,(byte)0xc0, (byte) 0x00, (byte) 0x16, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0x0c,0x00};
    @Override
    public boolean RunCommand(ClientThread _cli) {
        try {
            _cli.getConnection().sendPacket(_pact);
           
//             _cli.getConnection().sendPacket(_pac);

           
        } catch (IOException ex) {
            Logger.getLogger(CmdGiveSheld.class.getName()).log(Level.SEVERE, null, ex);
        }
         return true;
    }

    @Override
    public String getCmdString() {
        return "GiveSheld";
    }

    @Override
    public String getHelpToCommand() {
        return "Type \\GiveSheld to get shelg in hand";
    }

    @Override
    public String getShortDesc() {
        return "Add Sheld to inwentory ";
    }

}
