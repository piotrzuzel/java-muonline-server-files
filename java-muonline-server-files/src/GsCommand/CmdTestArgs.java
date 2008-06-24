/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;

import net.sf.jmuserver.gs.ClientThread;

/**
 *
 * @author Miki i Linka
 */
public class CmdTestArgs extends GsBaseCommand{

    @Override
    public boolean RunCommand(ClientThread _cli) {
        System.out.println(getCmdString()+" Runned!");
       return true;
    }

    @Override
    public void ParseArgs(String[] args) {
        System.out.println("|Arguements list fo commaand :"+getCmdString());
        for (String string : args) {
            System.out.println("|-- Arg["+string);
        }
        System.out.println("|End Arguements list fo commaand :"+getCmdString());
    }

    @Override
    public String getCmdString() {
       return "CommTestArgs";
    }

}
