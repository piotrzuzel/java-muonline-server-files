package net.sf.jmuserver.gs.muObiects;

//~--- non-JDK imports --------------------------------------------------------
import net.sf.jmuserver.gs.serverPackage.SLvlUp;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jmuserver.gs.serverPackage.SIdGoneDie;
import net.sf.jmuserver.gs.serverPackage.SMeetItemOnGround;

/**
 *
 * @author Miki
 */
public class MuPcInstance extends MuCharacter {

    protected MuCharacterWear _look = null;
    /**
     * inwentory 
     */
    protected MuCharacterInventory _inventory = null;

    public void set_inventory(MuCharacterInventory Inventory) {
        _inventory = Inventory;
    }

    /**
     * 
     * @return inwentory 
     */
    public MuCharacterInventory getInventory() {
        return _inventory;
    }

    public void SetWearLook(MuCharacterWear w) {
        _look = w;
    }

    public MuCharacterWear GetWearLook() {
        return _look;
    }
    private int _dbId;
    /**
     * Experance staff
     */
    private long _exp; // actua exp
    private long _expOnNewLvl; // exp needed to new lvl
    private int _lp; // lvl up points
    /**
     * connection to client
     */
    private MuConnection _netConnection;

    /**
     *
     */
    public MuPcInstance() {
        super();
    }

    /**
     * @param obiectId id obiject in game
     * @param _x wsp
     * @param _y Wsp y
     * @param _m Byte codeof map
     */
    public MuPcInstance(short obiectId, byte _x, byte _y, byte _m) {
        super(obiectId, _x, _y, _m);

    // TODO Auto-generated constructor stub
    }

    /**
     * Set id to database
     * @param id
     */
    public void setDbId(int id) {
        _dbId = id;
    }

    public int getDbId() {
        return _dbId;
    }

    /**
     * I'm got exp
     * @param i ilosc doswiadczenia
     */
    public void goneExp(int i) {
        _exp += i;
        System.out.println(this + " Got Exp " + i + "/" + _expOnNewLvl);
        if (_exp >= _expOnNewLvl) {
            System.out.println(this + "LVL UP!!");
            _exp = _expOnNewLvl; // like in  clijent
            incraseLvl();
        }

    }
//
//    /**
//     * dodaje experance
//     * @param hm doswiadczenei do ododania
//     */
//    public void addEpx(int hm) {
//        _exp += hm;
//
//        if (_exp > _expOnNewLvl) {
//            System.out.println("Gone nex lvl");
//        }
//    }
//
    /**
     * remove me from everyehere
     */
    public void deleteMe() {

        // stop all scheduled events
        MuWorld world = MuWorld.getInstance();

        world.removeObject(this);
        removeAllKnownObjects();
        setNetConnection(null);
        world.removeObject(this);
    }

    /**
     * @return connection to client
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
     * Set lvl up points
     * @param lp
     */
    public void setLP(int lp) {
        _lp = lp;
    }

    @Override
    public void updateMaxMpSp(int why) {
        super.updateMaxMpSp(why);
        if (why == UpdateStatsLPAdd) {
            try {
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

    }

    @Override
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
     * Updating Max Hp with send to client informacion about new Max hp value
     */
    @Override
    public void updateMaxHp(int why) {
        super.updateMaxHp(why);

        if (why == UpdateStatsLPAdd) {
            try {


                SLiveStats ml = new SLiveStats(SLiveStats._UPDATE_MAX);

                ml.setLive(getMaxHp());
                _netConnection.sendPacket(ml);
            } catch (IOException ex) {
                Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Set network onnection
     * @param connection
     */
    public void setNetConnection(MuConnection connection) {
        _netConnection = connection;
    }

    /**
     * change my status 
     * propablu neweruse
     * @param s
     */
    public void changeStatus(int s) {

        // broadcastPacket()
        setStatus(s);
    }

    /**
     * Return Exp needed to gion new lvl
     * @return Long
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

    /**
     * return actualy experance value
     * @return
     */
    public long getExp() {
        return _exp;
    }

    /**
     * return zen value
     * @return
     */
    public long getZen() {
        return 2000;
    }

    /**
     * decrace lvl up points
     */
    public void decLP() {
        _lp--;
    }

    /**
     * return real dmg
     * @ISUASE getvalues dependsof weapon,  skill etc
     * @return
     */
    public int getRealDmg() {
        return 150;
    }

    @Override
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
//    public void spownMe() {
//
//        // super.spownMe();
//        Vector temp = MuWorld.getInstance().getVisibleObjects(this);
//        ArrayList monstersTemp = new ArrayList();
//        ArrayList pcTemp = new ArrayList();
//
//        for (int i = 0; i < temp.size(); i++) {
//            ((MuObiect) temp.get(i)).addKnownObject(this);
//
//            if (temp.get(i) instanceof MuMonsterInstance) {
//                monstersTemp.add(temp.add(i));
//            } else if (temp.get(i) instanceof MuPcInstance) {
//                pcTemp.add(temp.get(i));
//            }
//        }
//
//        sendPacket(new SNpcMiting(monstersTemp));
//
//    // sendPacket(new );
//    }

    @Override
    public void addKnownObject(MuObiect object) {
        if (object != (MuObiect) this) {
            super.addKnownObject(object);
        }
    }

//    @Override
//    public void ISpown() {
//        super.ISpown();
//        System.out.println("ISpown in MuPcInstance;");
//        ArrayList<MuObiect> _playets = new ArrayList<MuObiect>();
//        ArrayList<MuObiect> _mobs = new ArrayList<MuObiect>();
//        ArrayList<MuObiect> _items = new ArrayList<MuObiect>();
//        for (MuObiect muObject : _knownObjects.values()) {
//
//            if (muObject instanceof MuPcInstance) {
//                _playets.add((MuPcInstance) muObject);
//            }
//            if (muObject instanceof MuMonsterInstance) {
//                _mobs.add((MuMonsterInstance) muObject);
//            }
//            if (muObject instanceof MuItemOnGround) {
//                _items.add((MuItemOnGround) muObject);
//            }
//        }
//        sendPacket(new SNpcMiting(_mobs));
//        sendPacket(new SPlayersMeeting(_playets));
//        sendPacket(new SMeetItemOnGround(_items));
//        // Notify other players of my spawn
//        ArrayList<MuObiect> _thisPlayer = new ArrayList<MuObiect>();
//        _thisPlayer.add(this);
//        SPlayersMeeting newSPM = new SPlayersMeeting(_thisPlayer);
//        for (int i = 0; i < _playets.size(); i++) {
//            if (!(_playets.get(i) instanceof MuPcActorInstance)) {
//                ((MuPcInstance) _playets.get(i)).sendPacket(newSPM);
//            }
//        }
//    }

    @Override
    /**
     * remove knownoiect andalso send oclientforget id package
     * 
     */
    public void removeKnownObject(MuObiect object, int why) {
        super.removeKnownObject(object, why);
        switch (why) {
            case 1://RemKnow_ForgetID
                sendPacket(new SForgetId(object));
                break;
            case 2://RemKnow_DieId
                sendPacket(new SIdGoneDie(object.getObjectId()));
                break;
        }

    }

//    @Override
//    public void moveTo(int newx, int newy) {
//
//        super.moveTo(newx, newy);
//        updateKnownsLists();
//    }

    /**
     * chceck in range object
     * UPDATE: Not used. Use the visible objects list instead.
     * @param t
     * @return true when object is in visitable area
     */
    public boolean checkInRage(MuObiect t) {
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
//    public void updateKnownsLists() {
//        //new lists
//        ArrayList<MuObiect> players = new ArrayList<MuObiect>();
//        ArrayList<MuObiect> _mobs = new ArrayList<MuObiect>();
//        ArrayList<MuObiect> _items = new ArrayList<MuObiect>();
//        ArrayList<MuObiect> _toForget = new ArrayList<MuObiect>();
//
//        Collection oldlist = oldgetKnownObjects().values();
//        //secend look for new object and swich it to lists and add also to known list
//        Vector visitable = getCurrentWorldRegion().getVisibleObjects(this);
//        for (Iterator it = visitable.iterator(); it.hasNext();) {
//            MuObiect checked = (MuObiect) it.next();
//            if (checked.getObjectId() == getObjectId()) {
//                continue; // if we are next
//            }
//
//            if (oldlist.contains(checked)) {
//                continue; // allready kow him
//            }
//            //object isnt myself and its new for as soe we check his type
//            //so we can added it to knowns
//            addKnownObject(checked);
//            //and update there objects
//            checked.addKnownObject(this);
//
//            //if is player
//            if (checked instanceof MuPcInstance) {
//                players.add(checked);
//                System.out.println("player meet " + checked);
//            } else //if is  mob or npc
//            if (checked instanceof MuMonsterInstance && checked instanceof MuNpcInstance) {
//                _mobs.add(checked);
//                System.out.println("monster meet " + checked);
//            } else //if is item
//            if (checked instanceof MuItemOnGround) {
//                _items.add(checked);
//                System.out.println("Item meet " + checked);
//            } else {
//                System.out.println("Error chcecked  type to miting " + checked);
//            }
//        }
//        //check old list of known objects for obj that are no longer visible and remove them
//        for (@SuppressWarnings("unchecked") Iterator<MuObiect> it = oldlist.iterator(); it.hasNext();) {
//            MuObiect muObject = it.next();
//            if (!visitable.contains(muObject)) {
//                _toForget.add(muObject);
//                removeKnownObject(muObject,RemKnow_ForgetID);
//                muObject.removeKnownObject(this,RemKnow_ForgetID);
//            }
//        }        
//        //now wi have all knowns, and to forget objects
//        //so send packages
//        if (!players.isEmpty()) {
//            System.out.println("send Pc meet " + players.size());
//            sendPacket(new SPlayersMeeting(players));
//        }
//        if (!_mobs.isEmpty()) {
//            System.out.println("send mobs  and npc meet  " + _mobs.size());
//            sendPacket(new SNpcMiting(_mobs));
//        }
//        if (!_items.isEmpty()) {
//            System.out.println("Send Items meet" + _items.size());
//            sendPacket(new SMeetItemOnGround(_items));
//        }
//        if (!_toForget.isEmpty()) {
//            System.out.println("send to forget ids" + _toForget.size());
//     //       sendPacket(new SForgetId(_toForget));
//        }
//        //notivy onother player about my 
//        ArrayList<MuObiect> _thisPlayer = new ArrayList<MuObiect>();
//        _thisPlayer.add(this);
//        SPlayersMeeting newSPM = new SPlayersMeeting(_thisPlayer);
//        for (int i = 0; i < players.size(); i++) {
//            ((MuPcInstance) players.get(i)).sendPacket(newSPM);
//        }
//    }

    private void ILvlUp() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void incraseLvl() {
        super.incraseLvl();
        _expOnNewLvl = MuClassStatsCalculate.CalculateExpOnNewLvl(getLvl());
        setLP(getLp() + MuClassStatsCalculate.getSPOnNewLvl(getClas()));
        sendPacket(new SLvlUp(this));

    }
}
