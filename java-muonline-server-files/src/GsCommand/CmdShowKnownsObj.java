/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GsCommand;

import java.util.ArrayList;
import java.util.List;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.muObiects.MuCharacter;
import net.sf.jmuserver.gs.muObiects.MuObiect;
import net.sf.jmuserver.gs.muObiects.MuPcInstance;

/**
 *
 * @author Miki i Linka
 */
public class CmdShowKnownsObj extends GsBaseCommand {

    private MuPcInstance _pcInstance;
    private List knowns=new ArrayList();

    @Override
  public  boolean RunCommand( ) {
        _pcInstance = _cli.getActiveChar();
        System.out.println("List of knowns Obj " + _pcInstance);
        knowns.addAll( _pcInstance.oldgetKnownObjects().values());
        for (int i = 0; i < knowns.size(); i++) {
            MuObiect object =  (MuObiect) knowns.get(i);
            System.out.println("|-List of knowns Obj " + object);
            List knownsKnowned = new ArrayList( object.oldgetKnownObjects().values());
            for (int j = 0; j < knownsKnowned.size(); j++) {
                MuObiect object1 =  (MuObiect) knownsKnowned.get(j);
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
