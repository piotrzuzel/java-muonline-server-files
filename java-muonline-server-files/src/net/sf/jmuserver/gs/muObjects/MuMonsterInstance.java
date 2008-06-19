package net.sf.jmuserver.gs.muObjects;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.serverPackage.SIdGoneDie;
import net.sf.jmuserver.gs.templates.MuNpc;

public class MuMonsterInstance extends MuAtackableInstance {

    private static Logger _log = Logger.getLogger(MuMonsterInstance.class.getName());

    public MuMonsterInstance(MuNpc temp) {
        super(temp);
        setMaxHp(temp.getMaxHp());
        setCurentHp(temp.getMaxHp());
        _myType = 2;
    }

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
        //	startRandomWalking();
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

    public void calculateReward()
    {
      int _who = getTargetID(); // get id
      long _exp = getExpReward(); // geting exp reward value
      //Item _item = getItemReward(); // geting item reward
        System.out.println("calculate reward to :"+getTargetID() + " getting exp  :"+_exp);
        
      
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

       
    }
    

