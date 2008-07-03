package net.sf.jmuserver.fs;

import net.sf.jmuserver.gs.*;
import java.io.IOException;
import net.sf.jmuserver.fs.clijentPackets.HelloFriendServer;


/**
 * This class ...
 * 
 * @version $Revision: 1.18 $ $Date: 2004/10/26 20:43:03 $
 */
public class PacketHandler {
    // private static Logger _log = Logger
    // .getLogger(PacketHandler.class.getName());
    private FriendTheard _friend;

    public PacketHandler(FriendTheard friend) {
        _friend = friend;
    }

    public void handlePacket(byte[] data) throws IOException, Throwable {
        // int pos = 0;
        // System.out.println("lenght="+data.length);
        int id = data[0] & 0xff;
        int id2 = 0;

        if (data.length > 1) {
            id2 = data[1] & 0xff;
        }
        System.out.println(printData(data, data.length, "FS[C->S]"));
        switch (id) {
            case 0x00:
                new HelloFriendServer(data, _friend);
                break;
            default:
                System.out.println("FS:Unknown Packet or no implament: " + Integer.toHexString(id));

        }

    }

    private String printData(byte[] data, int len, String string) {
        StringBuffer result = new StringBuffer();

        int counter = 0;

        for (int i = 0; i < len; i++) {
            if (counter % 16 == 0) {
                result.append(string + " ");
                result.append(fillHex(i, 4) + ": ");
            }

            result.append(fillHex(data[i] & 0xff, 2) + " ");
            counter++;
            if (counter == 16) {
                result.append("   ");

                int charpoint = i - 15;
                for (int a = 0; a < 16; a++) {
                    int t1 = data[charpoint++];
                    if (t1 > 0x1f && t1 < 0x80) {
                        result.append((char) t1);
                    } else {
                        result.append('.');
                    }
                }

                result.append("\n");
                counter = 0;
            }
        }

        int rest = data.length % 16;
        if (rest > 0) {
            for (int i = 0; i < 17 - rest; i++) {
                result.append("   ");
            }

            int charpoint = data.length - rest;
            for (int a = 0; a < rest; a++) {
                int t1 = data[charpoint++];
                if (t1 > 0x1f && t1 < 0x80) {
                    result.append((char) t1);
                } else {
                    result.append('.');
                }
            }

            result.append("\n");
        }

        return result.toString();
    }

    private String fillHex(int data, int digits) {
        String number = Integer.toHexString(data);

        for (int i = number.length(); i < digits; i++) {
            number = "0" + number;
        }

        return number;
    }
}
