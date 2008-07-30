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
import GsCommand.CmdMake;
import GsCommand.CmdMobTest;
import GsCommand.CmdStartMove;
import GsCommand.GsBaseCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CLass to manage and run commands in server
 * Command Must be type of  GsBaseCommand
 * @see GsBaseCommand
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
     *  registe new command 'com' 
     * Com must be type of GSbaseCommand
     * @see GsBaseCommand
     * @param com command to register
     */
    public void registeNewCommand(GsBaseCommand com) {
        System.out.println("Register New Command '" + com.getCmdString() + "'  -   " + com.getShortDesc());
        _commands.put(com.getCmdString().toLowerCase(), com);
        _commandsA.add(com);
    }

    /**
     * get help string for selected command
     * @param Com command 
     * @return string of help 
     */
    public String GetHelpStr(String Com) {
        System.out.println("try gethelp  for: '" + Com + "'");
        GsBaseCommand commandToExecute = (GsBaseCommand) _commands.get(Com);
        if (commandToExecute == null) {
            return Com + ": Command not exist!!!";
        }
        return commandToExecute.getHelpToCommand();
    }

    /**
     * constructor 
     * actuali we there added all comand to menager
     * @ISUASE  1 we can write get command from directory
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
        System.out.println("=-=-=-=-=- Commands Registring End =-=-=-=-");
    }

    /**
     * geting instace to CommandHandler
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
     * @param _cli clientTheard whos runcommand
     * @see ClientThread
     * @see GsBaseCommand
     * @see CommandHandler
     * @param CommandLine ommand line rorun
     * @return true if seccesful flase if command dont exist or command returnfalse 
     */
    public boolean Execude(ClientThread _cli, String CommandLine) {
        boolean runned = false;
        String[] commP = CommandLine.toLowerCase().split(" ");
        GsBaseCommand commandToExecute = (GsBaseCommand) _commands.get(commP[0]);

        if (commandToExecute == null) {
            return false;
        }
        commandToExecute.SetClientTheard(_cli);
        commandToExecute.ParseArgs(commP);
        boolean wyn = commandToExecute.RunCommand();
        commandToExecute.SetClientTheard(null);
        return wyn;
    }
}
