/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GsCommand;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.CommandHandler;

/**
 *
 * @author Miki i Linka
 */
public class CmdHelp extends GsBaseCommand {

    String helpStr = "";

    @Override
    public boolean RunCommand(ClientThread _cli) {
        System.out.println(helpStr);
        return true;
    }

    @Override
    public void ParseArgs(String[] args) {
        CommandHandler CH = CommandHandler.getInstancec();
        for(int i=1;i<args.length;i++){
            helpStr += "Help for :" + args[i] + "\n" + CH.GetHelpStr(args[i]) + "\n";
        }
    }

    @Override
    public String getCmdString() {
        return "Help";
    }

    @Override
    public String getHelpToCommand() {
        return "Show help :\n Usage \\Help command";
    }
}
