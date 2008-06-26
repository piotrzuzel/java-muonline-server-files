package net.sf.jmuserver.gs.clientPackage;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.CommandHandler;
import net.sf.jmuserver.gs.serverPackage.SPublicMsg;

public class CPublicMsg extends ClientBasePacket {

    private String _from;
    private String _msg;
    private byte[] _learnSkill = {(byte) 0xc1, 0x08, (byte) 0xf3, 0x11, (byte) 0xfe, 0x00, 0x11, 0x00};
    private byte[] _itemMove = {(byte) 0xC3, 0x0A, 0x24, 0x00, 0x0D, (byte) 0xB4, 0x10, 0x20, (byte) 0xC0, 0x00, 0x00};
    private byte[] _skilllist = {(byte) 0xc1, 0x08, (byte) 0xf3, 0x11, 0x01, 0x00, 0x11, 0x01};
    private byte[] _itemPack = {(byte) 0xc4, 0x00, 0x06 + 6, (byte) 0xf3, 0x10, 0x01, 0x01, (byte) 0xC0, 0x00, 0x16, 0x00, 0x00};

    public CPublicMsg(byte[] data, ClientThread _client) {
        super(data);
        _from = readS(1, 10).trim();
        _msg = readS(11, data.length - 12);
        System.out.println("waidomosc publiczna od :" + _from + " o tresci: " + _msg + ". ");
        try {
            if (_msg.charAt(0) == '\\') {
             if( !CommandHandler.getInstancec().Execude(_client, _msg.substring(1)))
                 try {
                    _client.getConnection().sendPacket(new SPublicMsg("System", "Wrong Command try again !"));
                } catch (IOException ex) {
                    Logger.getLogger(CPublicMsg.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Throwable ex) {
                    Logger.getLogger(CPublicMsg.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                switch (_msg.charAt(1)) {
                    case '1':
                        _client.getConnection().sendPacket(_learnSkill);
                        break;
                    case '2':
                        _client.getConnection().sendPacket(_itemMove);
                        break;
                    case '3':
                        _client.getConnection().sendPacket(_skilllist);
                        break;
                    case '4':
                        _client.getConnection().sendPacket(_itemPack);
                        break;
                }

            }
            else
            {
                _client.getActiveChar().broadcastPacket(new SPublicMsg(_from,_msg));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
