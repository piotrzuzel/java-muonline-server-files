/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
