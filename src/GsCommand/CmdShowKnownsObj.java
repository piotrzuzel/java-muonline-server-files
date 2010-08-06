/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GsCommand;

import java.util.ArrayList;
import java.util.List;

import net.sf.jmuserver.gs.muObjects.MuObject;
import net.sf.jmuserver.gs.muObjects.MuPcInstance;

/**
 * 
 * @author Miki i Linka
 */
public class CmdShowKnownsObj extends GsBaseCommand {

	private MuPcInstance _pcInstance;
	private final List knowns = new ArrayList();

	@Override
	public boolean RunCommand() {
		_pcInstance = _cli.getActiveChar();
		System.out.println("List of knowns Obj " + _pcInstance);
		knowns.addAll(_pcInstance.oldgetKnownObjects().values());
		for (int i = 0; i < knowns.size(); i++) {
			final MuObject object = (MuObject) knowns.get(i);
			System.out.println("|-List of knowns Obj " + object);
			final List knownsKnowned = new ArrayList(object
					.oldgetKnownObjects().values());
			for (int j = 0; j < knownsKnowned.size(); j++) {
				final MuObject object1 = (MuObject) knownsKnowned.get(j);
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
