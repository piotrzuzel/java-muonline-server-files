/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

/**
 * 
 * @author Miki
 */
public interface ServerPacketModel {

	/**
	 * 
	 * @return zwraca zarartosc w byte[]
	 */
	byte[] getBytes();

	byte[] getContent() throws IOException, Throwable;

	int getLength();

	/**
	 * just for information and debug purposes
	 * 
	 * @return
	 */
	String getType();

	String printData(byte[] data, int len, String string);

	boolean testMe();

}
