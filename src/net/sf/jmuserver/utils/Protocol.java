/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.utils;

/**
 *
 * @author mikiones
 */
public class Protocol {
 /**
     * printe data to String as Hex look edytor
     * @param data
     * @param len
     * @param string
     * @return
     */
    public static String printData(byte[] data, int len, String string) {
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
    public static String fillHex(int data, int digits) {
        String number = Integer.toHexString(data);

        for (int i = number.length(); i < digits; i++) {
            number = "0" + number;
        }

        return number;
    }
}
