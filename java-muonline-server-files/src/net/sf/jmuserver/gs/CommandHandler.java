/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs;

import GsCommand.CmdShowKnownsObj;
import GsCommand.CmdTestArgs;
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

    /**
     *  registe ew ommand 'com'
     * @param com
     */
    public void registeNewCommand(GsBaseCommand com) {
        System.out.println("Register New Command '" + com.getCmdString() + "'");
        _commands.put(com.getCmdString().toLowerCase(), com);
    }

    private CommandHandler() {
        System.out.println("=-=-=-=-=- Commands Registring Begin =-=-=-");
        registeNewCommand(new CmdShowKnownsObj());
        registeNewCommand(new CmdTestArgs());
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
        String comm = getCommand(CommandLine).toLowerCase();
        GsBaseCommand commandToExecute = (GsBaseCommand) _commands.get(comm);
        
        if (commandToExecute == null) {
            return false;
        }
       commandToExecute.ParseArgs(CommandLine.substring(comm.length()).split(" "));
        return commandToExecute.RunCommand(_cli);
    }

    /**
     * @param CommandLine
     * @return command from commandline like <b>"test 123 foo"</b> return <b>"test"</b> 
     */
    private String getCommand(String CommandLine) {
        String ResultString = "";
        try {
            Pattern regex = Pattern.compile("\\w+", Pattern.COMMENTS);
            Matcher regexMatcher = regex.matcher(CommandLine);
            if (regexMatcher.find()) {
                ResultString = regexMatcher.group();
            }
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }

        return ResultString;
    }
   
}
