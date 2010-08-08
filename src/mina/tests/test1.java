/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mina.tests;

import java.io.IOException;

import net.sf.jmuserver.cs.ServerList;
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
    ServerList l = ServerList.getInstance();
    l.load();
    }

}
