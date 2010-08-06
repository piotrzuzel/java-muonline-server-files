/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GsCommand;


/**
 * 
 * @author Miki i Linka
 */
public class CmdStartMove extends GsBaseCommand {

	@Override
	public boolean RunCommand() {
		// MuObject[] t =
		// _cli.getActiveChar().getCurrentWorldRegion().getVisibleObjects();
		// for (MuObject muObject : t) {
		// if(muObject instanceof MuMonsterInstance) {
		// ((MuMonsterInstance)muObject).startRandomWalking();
		// }}
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
