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
package com.google.code.openmu.gs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.code.openmu.gs.commands.CmdGiveSheld;
import com.google.code.openmu.gs.commands.CmdHelp;
import com.google.code.openmu.gs.commands.CmdMake;
import com.google.code.openmu.gs.commands.CmdMobTest;
import com.google.code.openmu.gs.commands.CmdOpenTookWnd;
import com.google.code.openmu.gs.commands.CmdShowInvSlots;
import com.google.code.openmu.gs.commands.CmdShowKnownsObj;
import com.google.code.openmu.gs.commands.CmdStartMove;
import com.google.code.openmu.gs.commands.CmdTestArgs;
import com.google.code.openmu.gs.commands.GsBaseCommand;


/**
 * CLass to manage and run commands in server Command Must be type of
 * GsBaseCommand
 * 
 * @see GsBaseCommand
 * @author Miki i Linka
 */
public class CommandHandler {

	static CommandHandler _instance = null;
	private final Map _commands = new HashMap();
	private final ArrayList _commandsA = new ArrayList();

	public ArrayList getList() {
		return _commandsA;
	}

	/**
	 * registe new command 'com' Com must be type of GSbaseCommand
	 * 
	 * @see GsBaseCommand
	 * @param com
	 *            command to register
	 */
	public void registeNewCommand(GsBaseCommand com) {
		System.out.println("Register New Command '" + com.getCmdString()
				+ "'  -   " + com.getShortDesc());
		_commands.put(com.getCmdString().toLowerCase(), com);
		_commandsA.add(com);
	}

	/**
	 * get help string for selected command
	 * 
	 * @param Com
	 *            command
	 * @return string of help
	 */
	public String GetHelpStr(String Com) {
		System.out.println("try gethelp  for: '" + Com + "'");
		final GsBaseCommand commandToExecute = (GsBaseCommand) _commands
				.get(Com);
		if (commandToExecute == null) {
			return Com + ": Command not exist!!!";
		}
		return commandToExecute.getHelpToCommand();
	}

	/**
	 * constructor actuali we there added all comand to menager
	 * 
	 * @ISUASE 1 we can write get command from directory
	 */
	private CommandHandler() {
		System.out.println("=-=-=-=-=- Commands Registring Begin =-=-=-");
		registeNewCommand(new CmdHelp());
		registeNewCommand(new CmdShowKnownsObj());
		registeNewCommand(new CmdTestArgs());
		registeNewCommand(new CmdOpenTookWnd());
		registeNewCommand(new CmdGiveSheld());
		registeNewCommand(new CmdStartMove());
		registeNewCommand(new CmdMobTest());
		registeNewCommand(new CmdMake());
		registeNewCommand(new CmdShowInvSlots());
		System.out.println("=-=-=-=-=- Commands Registring End =-=-=-=-");
	}

	/**
	 * geting instace to CommandHandler
	 * 
	 * @return
	 */
	static public CommandHandler getInstancec() {
		if (_instance == null) {
			_instance = new CommandHandler();
		}
		return _instance;
	}

	/**
	 * run the command
	 * 
	 * @param _cli
	 *            clientTheard whos runcommand
	 * @see ClientThread
	 * @see GsBaseCommand
	 * @see CommandHandler
	 * @param CommandLine
	 *            ommand line rorun
	 * @return true if seccesful flase if command dont exist or command
	 *         returnfalse
	 */
	public boolean Execude(ClientThread _cli, String CommandLine) {
		final String[] commP = CommandLine.toLowerCase().split(" ");
		final GsBaseCommand commandToExecute = (GsBaseCommand) _commands
				.get(commP[0]);

		if (commandToExecute == null) {
			return false;
		}
		commandToExecute.SetClientTheard(_cli);
		commandToExecute.ParseArgs(commP);
		final boolean wyn = commandToExecute.RunCommand();
		commandToExecute.SetClientTheard(null);
		return wyn;
	}
}
