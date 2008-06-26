package net.sf.jmuserver.gs.muObjects;

//~--- non-JDK imports --------------------------------------------------------
import net.sf.jmuserver.gs.MuConnection;
import net.sf.jmuserver.gs.serverPackage.SPlayersMeeting;
import net.sf.jmuserver.gs.serverPackage.SLiveStats;
import net.sf.jmuserver.gs.serverPackage.SManaStaminaStats;
import net.sf.jmuserver.gs.serverPackage.SNpcMiting;
import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;
import net.sf.jmuserver.gs.stats.MuClassStatsCalculate;
import net.sf.jmuserver.gs.templates.MuWeapon;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miki
 */
public class MuPcInstance extends MuCharacter {

    private MuCharacterWear _look = null;

    public void SetWearLook(MuCharacterWear w) {
        _look = w;
    }

    public MuCharacterWear GetWearLook() {
        return _look;
    }
    private int _dbId;
    /**
     * iloc dowiadczenia postaci
     */
    private long _exp;
    private long _expOnNewLvl;
    private int _lp;
    /**
     * polaczenie sieciowe z sklijetem
     */
    private MuConnection _netConnection;

    /**
     *
     */
    public MuPcInstance() {
        super();
    }

    /**
     * @param obiectId id obiektu
     * @param _x wsp
     * @param _y wsp
     * @param _m mapa
     */
    public MuPcInstance(short obiectId, byte _x, byte _y, byte _m) {
        super(obiectId, _x, _y, _m);

    // TODO Auto-generated constructor stub
    }

    /**
     * ustawia id do bazy dabych
     * @param id
     */
    public void setDbId(int id) {
        _dbId = id;
    }

    public int getDbId() {
        return _dbId;
    }

    /**
     * otrzymano doswiadczenie
     * @param i ilosc doswiadczenia
     */
    public void goneExp(int i) {
        System.out.println("Character: " + getName() + " gone " + i + "experance");
    }

    /**
     * dodaje experance
     * @param hm doswiadczenei do ododania
     */
    public void addEpx(int hm) {
        _exp += hm;

        if (_exp > _expOnNewLvl) {
            System.out.println("Gone nex lvl");
        }
    }

    /**
     * usuwa mnie ze wszelkich koligacji
     */
    public void deleteMe() {

        // stop all scheduled events
        MuWorld world = MuWorld.getInstance();

        world.removeVisibleObject(this);
        removeAllKnownObjects();
        setNetConnection(null);
        world.removeObject(this);
    }

    /**
     * @return zwraca polaczenie z klijentem
     */
    public MuConnection getNetConnection() {
        if (_netConnection == null) {
            throw new NullPointerException("lost pointer to net conection");
        }

        return _netConnection;
    }

    /*
     *  (non-Javadoc)
     * @see net.sf.jmuserver.gs.muObjects.MuCharacter#sendPacket(net.sf.jmuserver.gs.serverPackage.ServerBasePacket)
     */
    @Override
    public void sendPacket(ServerBasePacket packet) {
        try {
            getNetConnection().sendPacket(packet);
        } catch (IOException ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, "Null POint w get NetConecton", ex);
        } catch (Throwable ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, packet.getClass().toString(), ex);
        }
    }

    public void setExp(long exp) {
        _exp = exp;
    }

    public void setExpOnNewLvl(long exp) {
        _expOnNewLvl = exp;
    }

    /**
     * ustawia lp - lvl u pointsy
     * @param lp
     */
    public void setLP(int lp) {
        _lp = lp;
    }

    @Override
    public void updateMaxMpSp() {
        try {
            super.updateMaxMpSp();

            SManaStaminaStats sm = new SManaStaminaStats(SManaStaminaStats._UPDATE_MAX);

            sm.setMana(getMaxMp());
            sm.setStamina(getMaxSp());
            _netConnection.sendPacket(sm);
        } catch (IOException ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCurentHp(int curHp) {
        try {
            super.setCurentHp(curHp);

            SLiveStats hp = new SLiveStats(SLiveStats._UPDATE_CUR);

            hp.setLive(curHp);
            _netConnection.sendPacket(hp);
        } catch (IOException ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * wysyla do klijeta aczke z nowym hp i uaktualnia go
     */
    @Override
    public void updateMaxHp() {
        try {
            super.updateMaxHp();

            SLiveStats ml = new SLiveStats(SLiveStats._UPDATE_MAX);

            ml.setLive(getMaxHp());
            _netConnection.sendPacket(ml);
        } catch (IOException ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNetConnection(MuConnection connection) {
        _netConnection = connection;
    }

    public void changeStatus(int s) {

        // broadcastPacket()
        setStatus(s);
    }

    /**
     * iosc expa potrzebna na nowy lvl
     * @return jak wyzej
     */
    public long getExpOnNewLvl() {
        return _expOnNewLvl;
    }

    /**
     *
     * @return lp
     */
    public int getLp() {
        return _lp;
    }

    public long getExp() {
        return _exp;
    }

    public long getZen() {
        return 2000;
    }

    public void decLP() {
        _lp--;
    }

    public int getRealDmg() {
        return 150;
    }

    public int getMaxSp() {
        return 0;
    }

    public void SetHpMpSp() {
        setMaxHp(MuClassStatsCalculate.getMaxHp(getClas(), getLvl(), getVit()));
        setMaxMp(MuClassStatsCalculate.getMaxMp(getClas(), getLvl(), getEne()));
        setMaxSp(MuClassStatsCalculate.getMaxSP(getClas(), getLvl(), getStr(), getEne()));
        setCurentHp(getMaxHp());
        setCurentMP(getMaxMp());
        setCurentSp(getMaxSp());
    }

    @Override
    public MuWeapon getActiveWeapon() {
        MuWeapon w = new MuWeapon();

        w.setAttackSpeed(10);
        w.setMaxDmg(150);
        w.setMinDmg(100);
        System.out.println("returned wapon from user");

        return w;
    }

    // @Override
    public void spownMe() {

        // super.spownMe();
        Vector temp = MuWorld.getInstance().getVisibleObjects(this);
        ArrayList monstersTemp = new ArrayList();
        ArrayList pcTemp = new ArrayList();

        for (int i = 0; i < temp.size(); i++) {
            ((MuObject) temp.get(i)).addKnownObject(this);

            if (temp.get(i) instanceof MuMonsterInstance) {
                monstersTemp.add(temp.add(i));
            } else if (temp.get(i) instanceof MuPcInstance) {
                pcTemp.add(temp.get(i));
            }
        }

        sendPacket(new SNpcMiting(monstersTemp));

    // sendPacket(new );
    }

    @Override
    public void UseeMe(MuObject o) {
        super.UseeMe(o);

    // juz mam obiekt w znanych wienc tyle
    }

    @Override
    public void addKnownObject(MuObject object) {
        super.addKnownObject(object);

        // tu powinie wysylac paczke
        if (object instanceof MuMonsterInstance) {
            ArrayList t = new ArrayList();

            t.add(object);
            sendPacket(new SNpcMiting(t));
        }
    }

    @Override
    public void ISpown() {
        super.ISpown();
        System.out.println("ISpown in MuPcInstance;");
        MuObject[] knowns = (MuObject[]) getKnownObjects().toArray();
        ArrayList<MuObject> _playets = new ArrayList<MuObject>();
        ArrayList<MuObject> _mobs = new ArrayList<MuObject>();
        for (MuObject muObject : knowns) {
            if (muObject instanceof MuPcInstance) {
                _playets.add((MuPcInstance) muObject);
            }
            if (muObject instanceof MuMonsterInstance) {
                _mobs.add((MuMonsterInstance) muObject);
            }
        }
        sendPacket(new SNpcMiting(_mobs));
        sendPacket(new SPlayersMeeting(_playets));

    }
}
