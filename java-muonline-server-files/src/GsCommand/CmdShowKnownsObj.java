/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GsCommand;

import java.util.List;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObjects.MuCharacter;
import net.sf.jmuserver.gs.muObjects.MuPcInstance;

/**
 *
 * @author Miki i Linka
 */
public class CmdShowKnownsObj extends GsBaseCommand {

    private MuPcInstance _pcInstance;
    private List knowns;

    @Override
  public  boolean RunCommand( ClientThread _cli) {
        _pcInstance = _cli.getActiveChar();
        System.out.println("List of knowns Obj " + _pcInstance);
        knowns = _pcInstance.oldgetKnownObjects();
        for (int i = 0; i < knowns.size(); i++) {
            MuCharacter object = (MuCharacter) knowns.get(i);
            System.out.println("|-List of knowns Obj " + object);
            List knownsKnowned = object.oldgetKnownObjects();
            for (int j = 0; j < knownsKnowned.size(); j++) {
                MuCharacter object1 = (MuCharacter) knownsKnowned.get(j);
                System.out.println("|--List of knowns Obj " + object1);
            }

        }
        return true;
    }

    @Override
    public String getCmdString() {
    return "ShowKnowns";
    }

    @Override
    public String getHelpToCommand() {
       return "Show all Ids kown andknownsthats ids";
    }

    @Override
    public String getShortDesc() {
      return "show all knownobjects";
    }
    
}
