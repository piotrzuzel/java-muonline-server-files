package net.sf.jmuserver.gs.muObjects;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.serverPackage.SIdGoneDie;
import net.sf.jmuserver.gs.templates.MuNpc;

public class MuMonsterInstance extends MuAtackableInstance {

    private static Logger _log = Logger.getLogger(MuMonsterInstance.class.getName());
    private MuMobWalkArea _walkArea = null;
    private boolean _walkActive = false;

    public void setWalkArea(MuMobWalkArea w) {
        _walkArea = w;
    }

    private void onWalkTimer() {
       int nx= _walkArea.getRandX(getX());
       int ny =_walkArea.getRandY(getY());
       moveTo(nx, ny);
    }

    class RandomWalkingTask extends TimerTask {

        MuMonsterInstance _instance;

        public RandomWalkingTask(MuMonsterInstance c) {
            _instance = c;
        }

        public void run() {

            _instance.onWalkTimer();
            System.out.println(_instance.getClass().getSimpleName() + ".RandomWalk.Run(): DOne");
        }
    }

    public MuMonsterInstance(MuNpc temp) {
        super(temp);
        setMaxHp(temp.getMaxHp());
        setCurentHp(temp.getMaxHp());
        _myType = 2;
    }
    private RandomWalkingTask _walkTask = null;
    private Timer _walkTimer = new Timer(true);

    @Override
    public void setTarget(MuObject t) {
        super.setTarget(t);
        System.out.println("Set target in mu monster");
    }

    @Override
    public void addKnownObject(MuObject object) {
        super.addKnownObject(object);
        System.out.println("mmonster see new user");
        if (object instanceof MuPcInstance && !isActive()) {
            setActive(true);
            startRandomWalking();
        //		if (isAggressive() && !isTargetScanActive())
        //	{
        //			startTargetScan();
        //		}
        }
    }

    public void removeKnownObject(MuObject object) {
        super.removeKnownObject(object);
        _log.finest("monster dont see obiects");
    }

    public void reduceCurrentHp(int i, MuCharacter c) {
        super.reduceCurrentHp(i, c);
        System.out.println("test");
        System.out.println("HP:(" + getCurentHp() + "/" + getMaxHp() + ").");
    }

    public void calculateReward() {
        int _who = getTargetID(); // get id
        long _exp = getExpReward(); // geting exp reward value
        //Item _item = getItemReward(); // geting item reward
        System.out.println("calculate reward to :" + getTargetID() + " getting exp  :" + _exp);
    }

    @Override
    public void IDie() {

        super.IDie();
        calculateReward();
        System.out.println("Iday w MuMonster");
        Iterator it = getKnownObjects().iterator();
        while (it.hasNext()) {

            MuPcInstance object = (MuPcInstance) it.next();
            object.sendPacket(new SIdGoneDie(getObjectId()));

        }
        System.out.println("propaby respown");
        startRespownTask();

    }

    public void startRandomWalking() {
        
    if(_walkTask==null){
        _walkTask = new RandomWalkingTask(this);
        System.out.println("start Random Walk");
        _walkTimer.scheduleAtFixedRate(_walkTask, 6000, 6000);
        _walkActive = true;
    }
    
    }

    public void stopRandomWalking() {
        if (_walkActive) {
            _walkTask.cancel();
            _walkTask = null;
            System.out.println("rex MP stopped");
            _walkActive = false;
        }
    }
}

