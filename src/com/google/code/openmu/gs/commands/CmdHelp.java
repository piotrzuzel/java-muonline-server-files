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

import com.google.code.openmu.gs.CommandHandler;

/**
 * 
 * @author Miki i Linka
 */
public class CmdHelp extends GsBaseCommand {

	String helpStr = "";

	@Override
	public boolean RunCommand() {
		System.out.println(helpStr);
		helpStr = "";
		return true;
	}

	@Override
	public void ParseArgs(String[] args) {
		helpStr = "";
		if (args.length == 1) {
			helpStr = getHelpToCommand();
			return;
		}
		final CommandHandler CH = CommandHandler.getInstancec();
		GsBaseCommand cmd;
		if (args[1].contains("-l")) {
			System.out.println("List oh GS commands:");
			for (int i = 0; i < CH.getList().size(); i++) {
				cmd = (GsBaseCommand) CH.getList().get(i);
				System.out.println("\\" + cmd.getCmdString() + "   "
						+ cmd.getShortDesc());
			}
		} else {
			for (int i = 1; i < args.length; i++) {
				helpStr += "Help for :" + args[i] + "\n"
						+ CH.GetHelpStr(args[i]) + "\n";
			}
		}
	}

	@Override
	public String getCmdString() {
		return "Help";
	}

	@Override
	public String getHelpToCommand() {
		return "Show help :\nUsage\n '\\Help command' show help about command\n '\\help -l' :show all commands";
	}

	@Override
	public String getShortDesc() {
		return "Help system";
	}
}
