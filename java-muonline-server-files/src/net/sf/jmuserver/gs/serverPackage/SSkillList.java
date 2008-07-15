package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

public class SSkillList extends ServerBasePacket {

    public byte[] getContent() throws IOException {
        // 02 001101 010404

        byte[] t = {
            //       c1    08           f3    11    01    00    11    01
            //(byte) 0xc1 ,0x08 ,(byte) 0xf3 ,0x11 ,0x01 ,0x00 ,0x11 ,0x01 
            (byte) 0xc1, (byte) 0x0b, (byte) 0xf3, (byte) 0x11, //header
            (byte) 0x02, //count skills
            (byte) 0x00, (byte) 0x11, (byte) 0x01, //skill
            (byte) 0x01, (byte) 0x04, (byte) 0x04
        };
        //mC1Header(0xf3, 0x11, 8);
        //writeC(1);
        //writeC(0x00);writeC(0x11);writeC(0x01);
        System.out.println(t.length);
        return t;
    }

    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean testMe() {
        // TODO Auto-generated method stub
        return false;
    }
    /**
     * @param args
     */
}
