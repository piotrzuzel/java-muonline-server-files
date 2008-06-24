/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GsCommand;

import net.sf.jmuserver.gs.ClientThread;

/**
 * base COmmanf clas 
 * @author Miki i Linka
 */
public abstract class GsBaseCommand {

    private String _command = "";

    /**
     * return the client theard for user who run command
     * @return
     */
    public GsBaseCommand() {
    }

    /**
     * the command run procedure
     * @return
     */
    abstract public boolean RunCommand(ClientThread _cli);
    public void ParseArgs(String [] args){}
    /**
     * @return command string
     */
    abstract public String getCmdString(); 
    abstract public String getHelpToCommand();
        
    
}
