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
        for (int i=1;i<args.length;i++) {
            System.out.println("|-- Arg["+args[i]+"]");
        }
        System.out.println("|End Arguements list fo commaand :'"+getCmdString()+"'");
    }

    @Override
    public String getCmdString() {
       return "CommTestArgs";
    }

    @Override
    public String getHelpToCommand() {
       return "Test for args :\n type CommTestArgs args to show this args parsed";
    }

    @Override
    public String getShortDesc() {
      return " tests for command args";
    }

}
