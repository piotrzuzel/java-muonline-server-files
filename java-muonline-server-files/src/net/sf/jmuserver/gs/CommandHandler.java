/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs;

import GsCommand.CmdHelp;
import GsCommand.CmdShowKnownsObj;
import GsCommand.CmdTestArgs;
import GsCommand.CmdOpenTookWnd;
import GsCommand.CmdGiveSheld;
import GsCommand.CmdMobTest;
import GsCommand.CmdStartMove;
import GsCommand.GsBaseCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 
 * @author Miki i Linka
 */
public class CommandHandler {

    static CommandHandler _instance = null;
    private Map _commands = new HashMap();
    private ArrayList _commandsA = new ArrayList();

    public ArrayList getList() {
        return _commandsA;
    }

    /**
     *  registe ew ommand 'com'
     * @param com
     */
    public void registeNewCommand(GsBaseCommand com) {
        System.out.println("Register New Command '" + com.getCmdString() + "'  -   " + com.getShortDesc());
        _commands.put(com.getCmdString().toLowerCase(), com);
        _commandsA.add(com);
    }

    public String GetHelpStr(String Com) {
        System.out.println("tgygethelp  for: '" + Com + "'");
        GsBaseCommand commandToExecute = (GsBaseCommand) _commands.get(Com);
        if (commandToExecute == null) {
            return Com + ": Command not exist!!!";
        }
        return commandToExecute.getHelpToCommand();
    }

    private CommandHandler() {
        System.out.println("=-=-=-=-=- Commands Registring Begin =-=-=-");
        registeNewCommand(new CmdHelp());
        registeNewCommand(new CmdShowKnownsObj());
        registeNewCommand(new CmdTestArgs());
        registeNewCommand(new CmdOpenTookWnd());
        registeNewCommand(new CmdGiveSheld());
        registeNewCommand(new CmdStartMove());
        registeNewCommand(new CmdMobTest());
        System.out.println("=-=-=-=-=- Commands Registring End =-=-=-=-");
    }

    static public CommandHandler getInstancec() {
        if (_instance == null) {
            _instance = new CommandHandler();
        }
        return _instance;
    }

    /**
     * run the command
     * @param _cli clientTheard whos runcommand
     * @param CommandLine ommand line rorun
     * @return true if seccesful flase if command dont exist or command returnfalse 
     */
    public boolean Execude(ClientThread _cli, String CommandLine) {
        System.out.println("try to run command: '" + CommandLine + "'");
        boolean runned = false;

        String[] commP = CommandLine.toLowerCase().split(" ");
        GsBaseCommand commandToExecute = (GsBaseCommand) _commands.get(commP[0]);

        if (commandToExecute == null) {
            return false;
        }
        commandToExecute.SetClientTheard(_cli);
        commandToExecute.ParseArgs(commP);
        boolean wyn= commandToExecute.RunCommand();
        commandToExecute.SetClientTheard(null);
        return wyn;
    }
}
