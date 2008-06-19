package net.sf.jmuserver.gs.serverPackage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * This class ...
 * 
 * @version $Revision: 1.3 $ $Date: 2004/07/04 11:14:53 $
 */
public abstract class ServerBasePacket implements ServerPacketModel {

    private static Logger _log = Logger.getLogger(ServerBasePacket.class.getName());
    protected ByteArrayOutputStream _bao;

    protected ServerBasePacket() {
        _bao = new ByteArrayOutputStream();
        _log.finest(getType());
    }

    /**
     * put the long value to byte array
     * @param value
     */
    protected void writeL(long value) {
        _bao.write((int) (value & 0xff));
        _bao.write((int) (value >> 8 & 0xff));
        _bao.write((int) (value >> 16 & 0xff));
        _bao.write((int) (value >> 24 & 0xff));
    }

    /**
     * put integer value to bytearray
     * @param value
     */
    protected void writeI(int value) {
        _bao.write(value & 0xff);
        _bao.write(value >> 8 & 0xff);

    }

    /**
     * writing integer with diverted bits 
     * some tims mu protocol using diverted bits of integer
     * @param value
     */
    protected void writeIDiverted(int value) {
        _bao.write(value >> 8 & 0xff);
        _bao.write(value & 0xff);

    }

    /**
     * put byte nalue to byte array
     * @param value
     */
    protected void writeC(int value) {
        _bao.write(value & 0xff);
    }

    /**
     * put double value to bytearray
     * @param org
     */
    protected void writeF(double org) {
        long value = Double.doubleToRawLongBits(org);
        _bao.write((int) (value & 0xff));
        _bao.write((int) (value >> 8 & 0xff));
        _bao.write((int) (value >> 16 & 0xff));
        _bao.write((int) (value >> 24 & 0xff));
        _bao.write((int) (value >> 32 & 0xff));
        _bao.write((int) (value >> 40 & 0xff));
        _bao.write((int) (value >> 48 & 0xff));
        _bao.write((int) (value >> 56 & 0xff));
    }

    /**
     * put bytearray to ..
     * @param a
     */
    protected void writeB(byte[] a) {
        try {
            _bao.write(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
 * write string to byte array
 * @param text
 */
    protected void writeS(String text) {
        try {
            if (text != null) {
                _bao.write(text.getBytes("ISO-8859-1"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * writes fixed nick full by 0x00 to 10 bytes
     * @param nick
     */
    protected void writeNick(String nick) {
        try {

            if (nick != null) {
                int l = nick.length(); // lenght of nnick 
                _bao.write(nick.getBytes("ISO-8859-1"));
                for (int i = 0; i < 10 - l; i++) {
                    _bao.write(0x00);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * write strimng to bytearray
     * @param text 
     * @param from 
     * @param ile size of string to write
     */
    protected void writeS(String text, int from, int ile) {
        try {
            if (text != null) {
                _bao.write(text.getBytes("ISO-8859-1"), from, ile);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * return lengh of byte array
     * @return
     */
    public int getLength() {
        return _bao.size();
    }

    /**
     * 
     * @return zwraca zarartosc w byte[]
     */
    @Override
    /**
     * return byte array
     */
    public byte[] getBytes() {
        return _bao.toByteArray();
    }

    /**
     * make c1 header template with 1'st class protocol
     * @param typ
     * @param s
     */
    public void mC1Header(int typ, int s) {
        _bao.write(0xc1);
        _bao.write((byte) s);
        _bao.write((byte) typ);
    }

    /**
     * make c1 header with 2 clases of protool
     * @param typ
     * @param typ2
     * @param s
     */
    public void mC1Header(int typ, int typ2, int s) {
        _bao.write(0xc1);
        _bao.write((byte) s);
        _bao.write((byte) typ);
        _bao.write((byte) typ2);
    //System.out.println("mc1headrec done2");

    }

    /**
     * make c3 template header with 2 types
     * @param typ
     * @param typ2
     * @param s
     */
    public void mC3Header(int typ, int typ2, int s) {
        _bao.write(0xc3);
        _bao.write((byte) s);
        _bao.write((byte) typ);
        _bao.write((byte) typ2);
    //System.out.println("mc1headrec done2");
    }

    /**
     * make c2 header with 2 types
     * @param typ
     * @param typ2
     * @param s
     */
    public void mC2Header(int typ, int typ2, int s) {
        _bao.write(0xc2);
        _bao.write(s >> 8 & 0xff);
        _bao.write(s & 0xff);
        _bao.write(typ & 0xff);
        _bao.write(typ2 & 0xff);
    }

    /**
     * compare two bitarrays
     * @param a
     * @param b
     * @return
     */
    protected boolean CompareBits(byte[] a, byte[] b) {

        if (a.length != b.length) {
            return false;
        } else {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * printe data to String as Hex look edytor
     * @param data
     * @param len
     * @param string
     * @return
     */
    public String printData(byte[] data, int len, String string) {
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

    /**
     * fill hex helped function 4 print hex 
     * @param data
     * @param digits
     * @return
     */
    private String fillHex(int data, int digits) {
        String number = Integer.toHexString(data);

        for (int i = number.length(); i < digits; i++) {
            number = "0" + number;
        }

        return number;
    }
}


