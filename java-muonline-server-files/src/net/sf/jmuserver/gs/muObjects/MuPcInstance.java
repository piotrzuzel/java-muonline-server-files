package net.sf.jmuserver.gs.muObjects;

//~--- non-JDK imports --------------------------------------------------------
import net.sf.jmuserver.gs.MuConnection;
import net.sf.jmuserver.gs.serverPackage.SForgetId;
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
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.serverPackage.SMeetItemOnGround;

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
        System.out.println("To PcInstance ID: " + this.getObjectId());
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

        super.setCurentHp(curHp);

        SLiveStats hp = new SLiveStats(SLiveStats._UPDATE_CUR);

        hp.setLive(getCurentHp());
        if (_netConnection == null) {
            System.out.println("mamy problem");
        }
        sendPacket(hp);

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
        if (object != (MuObject) this) {
            super.addKnownObject(object);
        }
    }

    @Override
    public void ISpown() {
        super.ISpown();
        System.out.println("ISpown in MuPcInstance;");
        ArrayList<MuObject> _playets = new ArrayList<MuObject>();
        ArrayList<MuObject> _mobs = new ArrayList<MuObject>();
        ArrayList<MuObject> _items = new ArrayList<MuObject>();
        for (MuObject muObject : _knownObjects.values()) {

            if (muObject instanceof MuPcInstance) {
                _playets.add((MuPcInstance) muObject);
            }
            if (muObject instanceof MuMonsterInstance) {
                _mobs.add((MuMonsterInstance) muObject);
            }
            if (muObject instanceof MuItemOnGround) {
                _items.add((MuItemOnGround) muObject);
            }
        }
        sendPacket(new SNpcMiting(_mobs));
        sendPacket(new SPlayersMeeting(_playets));
        sendPacket(new SMeetItemOnGround(_items));
        // Notify other players of my spawn
        ArrayList<MuObject> _thisPlayer = new ArrayList<MuObject>();
        _thisPlayer.add(this);
        SPlayersMeeting newSPM = new SPlayersMeeting(_thisPlayer);
        for (int i = 0; i < _playets.size(); i++) {
            if (!(_playets.get(i) instanceof MuPcActorInstance)) {
                ((MuPcInstance) _playets.get(i)).sendPacket(newSPM);
            }
        }
    }

    @Override
    /**
     * remove knownoiect andalso send oclientforget id package
     */
    public void removeKnownObject(MuObject object) {
        super.removeKnownObject(object);
        sendPacket(new SForgetId(object));
    }

    @Override
    public void moveTo(int newx, int newy) {

        super.moveTo(newx, newy);
        updateKnownsLists();
    }

    /**
     * chceck in range object
     * UPDATE: Not used. Use the visible objects list instead.
     * @param t
     * @return true when object is in visitable area
     */
    public boolean checkInRage(MuObject t) {
        int chx = t.getX() / 5;
        int chy = t.getY() / 5;
        int myx = getX() / 5;
        int myy = getY() / 5;
        int rangeX1 = myx - 3;
        int rangeX2 = myx + 3;
        int rangeY1 = myy - 3;
        int rangeY2 = myy + 3;
        if ((rangeX1 <= chx) && (chx <= rangeX2) && (rangeY1 <= chy) && (chy <= rangeY2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * method to update knowns list with sending all meeting packages
     */
    public void updateKnownsLists() {
        //new lists
        ArrayList<MuObject> players = new ArrayList<MuObject>();
        ArrayList<MuObject> _mobs = new ArrayList<MuObject>();
        ArrayList<MuObject> _items = new ArrayList<MuObject>();
        ArrayList<MuObject> _toForget = new ArrayList<MuObject>();

        Collection oldlist = oldgetKnownObjects().values();
        //secend look for new object and swich it to lists and add also to known list
        Vector visitable = getCurrentWorldRegion().getVisibleObjects(this);
        for (Iterator it = visitable.iterator(); it.hasNext();) {
            MuObject checked = (MuObject) it.next();
            if (checked.getObjectId() == getObjectId()) {
                continue; // if we are next
            }

            if (oldlist.contains(checked)) {
                continue; // allready kow him
            }
            //object isnt myself and its new for as soe we check his type
            //so we can added it to knowns
            addKnownObject(checked);
            //and update there objects
            checked.addKnownObject(this);

            //if is player
            if (checked instanceof MuPcInstance) {
                players.add(checked);
                System.out.println("player meet " + checked);
            } else //if is  mob or npc
            if (checked instanceof MuMonsterInstance && checked instanceof MuNpcInstance) {
                _mobs.add(checked);
                System.out.println("monster meet " + checked);
            } else //if is item
            if (checked instanceof MuItemOnGround) {
                _items.add(checked);
                System.out.println("Item meet " + checked);
            } else {
                System.out.println("Error chcecked  type to miting " + checked);
            }
        }
        //check old list of known objects for obj that are no longer visible and remove them
        for (@SuppressWarnings("unchecked") Iterator<MuObject> it = oldlist.iterator(); it.hasNext();) {
            MuObject muObject = it.next();
            if (!visitable.contains(muObject)) {
                _toForget.add(muObject);
                removeKnownObject(muObject);
                muObject.removeKnownObject(this);
            }
        }        
        //now wi have all knowns, and to forget objects
        //so send packages
        if (!players.isEmpty()) {
            System.out.println("send Pc meet " + players.size());
            sendPacket(new SPlayersMeeting(players));
        }
        if (!_mobs.isEmpty()) {
            System.out.println("send mobs  and npc meet  " + _mobs.size());
            sendPacket(new SNpcMiting(_mobs));
        }
        if (!_items.isEmpty()) {
            System.out.println("Send Items meet" + _items.size());
            sendPacket(new SMeetItemOnGround(_items));
        }
        if (!_toForget.isEmpty()) {
            System.out.println("send to forget ids" + _toForget.size());
     //       sendPacket(new SForgetId(_toForget));
        }
        //notivy onother player about my 
        ArrayList<MuObject> _thisPlayer = new ArrayList<MuObject>();
        _thisPlayer.add(this);
        SPlayersMeeting newSPM = new SPlayersMeeting(_thisPlayer);
        for (int i = 0; i < players.size(); i++) {
            ((MuPcInstance) players.get(i)).sendPacket(newSPM);
        }
    }
}
