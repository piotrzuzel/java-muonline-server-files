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
        System.out.println("ustawienie targetu w mumonster");
    }

    @Override
    public void addKnownObject(MuObject object) {
        super.addKnownObject(object);
        System.out.println("Monster widzi nowego usera");
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
        _log.finest("monster juz nie widzi usera");


    }

    public void reduceCurrentHp(int i, MuCharacter c) {
        super.reduceCurrentHp(i, c);
        System.out.println("test");
        System.out.println("HP:(" + getCurentHp() + "/" + getMaxHp() + ").");

    }

    public void calculateReward()
    {
      int _who = getTargetID(); // pobieramy id
      long _exp = getExpReward(); // pobieramty exp reward
      //Item _item = getItemReward(); // pobiramy item  na dropie
        System.out.println("kalkulacja nagrody dlA :"+getTargetID() + " otrzyma exp :"+_exp);
        
      
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
        System.out.println("respown niby");
        startRespownTask();

        }

       
    }
    

