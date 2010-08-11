/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.mina.tests;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

import com.google.code.openmu.gs.serverPackets.SHello;
import com.google.code.openmu.cs.ServerList;

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
