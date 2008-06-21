/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jmuserver.gs;

import GsCommand.CmdShowKnownsObj;
import GsCommand.GsBaseCommand;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Miki i Linka
 */
public class CommandHandler {
    static CommandHandler  _instance = null;
    private Map _commands = new HashMap();
    public void registeNewCommand(GsBaseCommand com)
    {
        System.out.println("Register New Command '"+com.getCmdString()+"'");
        _commands.put(com.getCmdString().toLowerCase(), com);
    }
    private CommandHandler()
    {
        System.out.println("=-=-=-=-=- Commands Registring Begin =-=-=-");
        registeNewCommand(new CmdShowKnownsObj());
        System.out.println("=-=-=-=-=- Commands Registring End =-=-=-=-");
    }
    
    static public CommandHandler getInstancec()
    {
        if (_instance==null)_instance=new CommandHandler();
        return _instance;
    }
    
    public boolean Execude(ClientThread _cli,String com)
    {
        System.out.println("try to run command: '"+com+"'");
        boolean runned=false;
        String comm=com.toLowerCase();
        GsBaseCommand commandToExecute=(GsBaseCommand) _commands.get(comm);
        if(commandToExecute==null) return false;
        return commandToExecute.RunCommand(_cli);
    }

}
