/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GsCommand;

import java.util.Timer;
import java.util.TimerTask;
import net.sf.jmuserver.gs.ClientThread;
import net.sf.jmuserver.gs.IdFactory;
import net.sf.jmuserver.gs.muObjects.MuMonsterInstance;
import net.sf.jmuserver.gs.templates.MuNpc;

/**
 *
 * @author Miki i Linka
 */
public class CmdMobTest extends GsBaseCommand {

    private Timer _timer;
    private testmob _testtask = null;

    class testmob extends TimerTask {

        MuMonsterInstance mob;

        public testmob(MuMonsterInstance mo) {
            mob = mo;
        }

        public void run() {
        }
    }
    private int _x; // xof player;
    private int _y; //y of player;
    private int _xm; // x of mob
    private int _ym; // y of mob
    private MuNpc _npc;
    private MuMonsterInstance mob;

    public CmdMobTest() {
        _npc = new MuNpc();
        _npc.setName("test");
        _npc.setMaxHp(400);
        _npc.setNpcId(2);


    }

    @Override
    public boolean RunCommand() {
        _x = _cli.getActiveChar().getX();
        _y = _cli.getActiveChar().getY();
        SendDbgMsg("=-=-=-=-=-==start build test mob-=-=--=-");
        mob = new MuMonsterInstance(_npc);
        mob.SetPos(_x + 3, _y + 2, 0);
        mob.setM(_cli.getActiveChar().getCurrentWorldRegion().getByteCode());
        mob.setObiectId((short) IdFactory.getInstance().newId());
        mob.setCurrentWorldRegion(_cli.getActiveChar().getCurrentWorldRegion());
        mob.ISpown();
        SendDbgMsg("-=-=--=-=Mob sended to spown-=-=-=-=");
        SendDbgMsg("1] checking known lists");
        mob.ShowNyKnowList();
        if (!mob.oldgetKnownObjects().containsKey(_cli.getActiveChar().getObjectId())) {
            SendDbgMsg("Error mob dont know pc  after spown!!!");
        } else if (!_cli.getActiveChar().oldgetKnownObjects().containsKey(mob.getObjectId())) {
            SendDbgMsg("Error me  dont know mob  after spown!!");
        } else {
            SendDbgMsg("Knowns ok lets go !!");
        }
         SendDbgMsg("2]Checking moving  !!");
         SendDbgMsg("2.1] moving away from see area character !!");
         mob.moveTo(_x+20, _y);
         if (mob.oldgetKnownObjects().containsKey(_cli.getActiveChar().getObjectId())) {
            SendDbgMsg("Error mob  know pc  after mob go away!!!");
        } else if (_cli.getActiveChar().oldgetKnownObjects().containsKey(mob.getObjectId())) {
            SendDbgMsg("Error me  know mob  after mob go away!!");
        } else {
            SendDbgMsg("Knowns ok after mob go away !!");
        }
         SendDbgMsg("2.2] moving back to character see area !!");
         SendDbgMsg("2.3] moving to character and send atack animacion !!");
         SendDbgMsg("2.4] moving away from see area character, character moveinto movenent mob so then shuld get package meet with moving mob !!");
         
        return true;
    }

    @Override
    public String getCmdString() {
        return "MobTest";
    }

    @Override
    public String getHelpToCommand() {
        return "Mob spown near character move after some times \n try to locate nearest character  walk to it \n and try atack after die andmaby giveexperance";
    }

    @Override
    public String getShortDesc() {
        return "Tests for mobs";
    }
}
