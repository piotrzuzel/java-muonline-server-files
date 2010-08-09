/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * 
 * @author Miki
 */
public interface ServerPacketModel {

	
	public abstract IoBuffer getContent(int SesionID);;

}
