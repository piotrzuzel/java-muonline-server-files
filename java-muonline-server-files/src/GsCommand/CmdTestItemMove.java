/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.serverPackage.SItemMoveInInwentory;

/**
 *
 * @author Miki i Linka
 */
public class CmdTestItemMove extends GsBaseCommand{

    @Override
    public boolean RunCommand(ClientThread _cli) {
        _cli.getActiveChar().sendPacket(new SItemMoveInInwentory());
        return true;
    }

    @Override
    public String getCmdString() {
        return "ItemMove";
    }

    @Override
    public String getHelpToCommand() {
        return "testing itemmove in inw";
    }

    @Override
    public String getShortDesc() {
                return "testing itemmove in inw";
    }

}
