/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.commands;

import com.google.code.openmu.gs.ClientThread;
import com.google.code.openmu.gs.CommandHandler;

/**
 * Base interface for command
 * 
 * @author Miki i Linka
 */
public abstract class GsBaseCommand extends Thread {

	public GsBaseCommand() {
	}

	/**
	 * the command run procedure
	 * 
	 * @return
	 */
	abstract public boolean RunCommand();

	protected ClientThread _cli;

	/**
	 * Parce arguments method
	 * 
	 * @param args
	 *            to parse
	 */
	public void ParseArgs(String[] args) {
	}

	/**
	 * Set ClientTheard from which wos run this command
	 * 
	 * @param _cli
	 *            Client
	 * @see ClientThread
	 */
	public void SetClientTheard(ClientThread _cli) {
		this._cli = _cli;
	}

	/**
	 * @return command string
	 */
	abstract public String getCmdString();

	/**
	 * 
	 * @return Help to command
	 * @see CommandHandler
	 */
	abstract public String getHelpToCommand();

	/**
	 * 
	 * @return shortdescrysion of command
	 * @see CommandHandler
	 */
	abstract public String getShortDesc();

	/**
	 * Send debug informacions to stdout and to client
	 * 
	 * @param s
	 */
	protected void SendDbgMsg(String s) {
		System.out.println(getCmdString() + " : " + s);
		// try {
		// _cli.getConnection().sendPacket(new SPublicMsg(getCmdString(), s));
		// } catch (IOException ex) {
		// Logger.getLogger(GsBaseCommand.class.getName()).log(Level.SEVERE,
		// null, ex);
		// } catch (Throwable ex) {
		// Logger.getLogger(GsBaseCommand.class.getName()).log(Level.SEVERE,
		// null, ex);
		// }
	}
}
