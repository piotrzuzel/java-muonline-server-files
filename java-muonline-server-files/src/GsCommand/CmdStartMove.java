/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;

import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObiects.MuMonsterInstance;
import net.sf.jmuserver.gs.muObiects.MuObiect;
import net.sf.jmuserver.gs.muObiects.MuWorld;

/**
 *
 * @author Miki i Linka
 */
public class CmdStartMove extends GsBaseCommand {

    @Override
    public boolean RunCommand() {
//        MuObiect[] t = _cli.getActiveChar().getCurrentWorldRegion().getVisibleObjects();
//        for (MuObiect muObject : t) {
//            if(muObject instanceof MuMonsterInstance) {
//                ((MuMonsterInstance)muObject).startRandomWalking();
//            }}
        return true;
    }

    @Override
    public String getCmdString() {
        return "StartMove";
    }

    @Override
    public String getHelpToCommand() {
        return " if \\startmove off then swich off";
    }

    @Override
    public String getShortDesc() {
        return "start move all monsters";
    }

}
