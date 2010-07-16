/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.tests;

import java.io.IOException;
import net.sf.jmuserver.gs.serverPackage.SHello;
import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @author mikiones
 */
public class test1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        SHello s = new  SHello(1, "09928");
    IoBuffer b = IoBuffer.wrap(s.getContent());
    byte[] t = new byte[3];
    System.out.println(b);
    b.get(t, 0, 3);
    IoBuffer a1= IoBuffer.wrap(t);
    System.out.println(a1);
    System.out.println(b);

    }

}
