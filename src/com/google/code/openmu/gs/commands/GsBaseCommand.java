/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
