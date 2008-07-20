package net.sf.jmuserver.gs.muObjects;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Vector;
import net.sf.jmuserver.gs.serverPackage.SDMgOnScreen;
import net.sf.jmuserver.gs.serverPackage.SGoneExp;
import net.sf.jmuserver.gs.serverPackage.SIdGoneDie;
import net.sf.jmuserver.gs.serverPackage.SToMoveID;
import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;
import net.sf.jmuserver.gs.stats.MuClassStatsCalculate;
import net.sf.jmuserver.gs.templates.MuWeapon;

/**
 * this class is basicli interface for lice gameobicets on maps  <br>
 * like mmobs,npcs
 * @author Miki
 * 
 */
public abstract class MuCharacter extends MuObject {

    private MuAura _aura = new MuAura();
    private static final int ST_IDE = 0;
    private static final int ST_WALK = 1;
    private static final int ST_ROTA = 2;
    private static final int ST_EMOT = 3;
    private static final int ST_SPOw = 4;
    private static final int ST_DIE = 5;
    private static final int ST_ATAC = 6;
    private int _myStatus = ST_SPOw;

    public int getMyStatus() {
        return _myStatus;
    }

    public void setMyStatus(int st) {
        _myStatus = st;
    }

    class MyStatus extends TimerTask {

        @Override
        public void run() {

            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    private int _status = 0x00;

    public void IDie() {
        //broadcastPacket(new SIdGoneDie(getObjectId()));
        if (getTarget() instanceof MuPcInstance) {
            ((MuPcInstance) getTarget()).sendPacket(new SGoneExp(getObjectId(), 30));
        }
        System.out.println("I'm Die In MuCharacter");

    }

    class AttackTask extends TimerTask {

        MuCharacter _instance;

        public AttackTask(MuCharacter c) {
            _instance = c;
        }

        public void run() {
            _instance.onAttackTimer();
            System.out.println(MuCharacter.class + ".AttackTask.Run(): DOne");
        }
    }

    /**
     * class is to set back on map obiect after deth -respown taske<br><br>
     * 1. postac jest ustawiaa na pozycji xy zmminiamy jej ustawienia tj x,y,i f ewentualnie<br>
     *    i ustawiemy jej zycie oraz mane na wartoc maxhp oraz maxMp  <br>
     * 2. odputujemy mape o widoczne obikty w zasiegu wzroku w szczegulnosci szukamy graczy<br>
     * 3. dodajemy ie na mape a widocznym nam gracza dajemy paczke ze nas widza<br>
     * 4. uruchamiamy ai taski jesli takie sa<br>
     * 
     * @see getMaxHp() 
     * @see getMaxMp()
     */
    class RespownTask extends TimerTask {

        private MuCharacter _instance;
        private int _resX = 0;
        private int _resY = 0;

        public RespownTask(MuCharacter _instance, int x, int y) {
            System.out.println("Respown setup  start");
            this._resX = x;
            this._resY = y;
            System.out.println("Saving old wsp");
            this._instance = _instance;

        //this._instance.removeAllKnownObjects();
        //System.out.println("remove allknown object");
        //_instance.getCurrentWorldRegion().removeVisibleObject(_instance);
        //System.out.println("remove object from map");
        }

        @Override
        public void run() {
            synchronized (_respownLock) {
                System.out.println("-=-=-=-=--=-=-=respown starting=-=-=-=-=-=-=-=-=");
                //sets back live stats
                _instance.setCurentHp(_instance.getMaxHp());
                System.out.println("actuualie live...done");
                _instance.setX(_resX);
                _instance.setY(_resY);
                System.out.println("actualize  wsp...done");
                ISpown();
                System.out.println("added to map ... done");
                System.out.println("-=-=-=-=-=-=-=-=-=-respown end=-=-=-=-=-=-=-=-=-=-");

            //obiect must be respown onmap
            }

        }
    }

    /**
     * hp regenerationtask...
     * 
     */
    class HpRegenTask extends TimerTask {

        private MuCharacter _instance;

        public HpRegenTask(MuCharacter c) {
            _instance = c;
        }

        public void run() {
            synchronized (_hpLock) {
                int curHp = _instance.getCurentHp();
                if (curHp < _instance.getMaxHp()) {
                    curHp += (int) (_instance.getMaxHp() * 0.018);
                    if (curHp > _instance.getMaxHp()) {
                        curHp = _instance.getMaxHp();
                    }
                }
                _instance.setCurentHp(curHp);
            }
        }
    }

    class MpRegenTask extends TimerTask {

        public MpRegenTask(MuCharacter c) {
            _instance = c;
        }
        private MuCharacter _instance;

        public void run() {
            synchronized (_mpLock) {
                double curMp = _instance.getCurentMp();
                if (curMp < _instance.getMaxMp()) {
                    curMp += _instance.getMaxMp() * 0.018;
                    if (curMp > _instance.getMaxMp()) {
                        curMp = _instance.getMaxMp();
                    }
                    _instance.setCurentMP((int) curMp);
                }

            }

        }
    }

    class HitTask extends TimerTask {

        MuCharacter _instance;
        MuCharacter _target;
        int _dmg;
        int _f;

        public HitTask(MuCharacter in, MuCharacter t, int dmg, int f) {
            _instance = in;
            _target = t;
            _dmg = dmg;
            _f = f;
        }

        public void run() {

            _instance.onHitTimer(_target, _dmg, _f);
        }
    }
    private int _agi;
    private int _agiMod = 0;
    private Timer _atackTimer = new Timer(true);
    private Timer _respownTimer = new Timer(true);
    private RespownTask _respown = null;
    private Object _respownLock = new Object();
    private byte _clas;
    private int _com;
    private int _comMod = 0;
    private AttackTask _currentAttackTask = null;
    private int _ene;
    private Timer _regenTimer = new Timer(true);
    private int _curentHP;
    private int _maxHp;
    private Object _hpLock = new Object();
    private HpRegenTask _hpRegTask = null;
    private int _curentMP;
    private int _maxMp;
    private Object _mpLock = new Object();
    private MpRegenTask _mpRegTask = null;
    private int _maxSP;
    private int _curentSP;
    private Object _spLock = new Object();
    private boolean _inCombat;
    private int _lvl;
    private String _name;
    private int _newX;
    private int _newY;
    private int _str;
    private int _strMod = 0;
    private MuObject _target = null;
    private int _vit;
    private int _vitMod = 0;
    private boolean _hpRegenActive;
    private boolean _mpRegenActive;
    private boolean _spRegenActive;
    private byte _direction = 0x02; // look south
    private byte _murderStatus = 0x02;

    /**
     * default constructor
     */
    public MuCharacter() {
        super();
        _myType = 0;
    }

    /**
     * 
     * @return actualhead direction
     */
    public byte getDirection() {
        return _direction;
    }

    /**
     * set head direction
     * @param newDirection
     */
    public void setDirection(byte newDirection) {
        _direction = newDirection;
    }

    public byte getMurderStatus() {
        return _murderStatus;
    }

    public void setMurderStatus(byte NewStatus) {
        _murderStatus = NewStatus;
    }
// staf 4 hits
    public void onHitTimer(MuCharacter target, int dmg, int f) {
        if (isDead() || target.isDead() || !target.knownsObject(this) || !knownsObject(target)) {


            setInCombat(false);
            setTarget(null);
        } else {
            //if(_currentlyAttacking==true)
            {
                //_atackTimer.schedule(new AttackTask(this), calculateAttackSpeed(getActiveWeapon()));
                displayHitMessage(target, dmg, f);
                target.reduceCurrentHp(dmg, this);
            }
        }

    }

    /**
     * send package hit on id
     * @param target ID atacked
     * @param dmg  how musch get dmg
     * @param f dmg flag // todo set tye of flags like 00= normal atak itc
     */
    private void displayHitMessage(MuCharacter target, int dmg, int f) {
        System.out.println("Hit :dmg[" + dmg + "] on id[" + target.getObjectId() + "] who has live[" + target.getCurentHp());
        SDMgOnScreen dmg1 = new SDMgOnScreen(getObjectId(), dmg, SDMgOnScreen._DMG_NORM);
        target.sendPacket(dmg1);

    }

    /**
     * counting speed of atack 
     * @param weaponItem weapon
     * @return attack speed
     */
    public int calculateAttackSpeed(MuWeapon weaponItem) {
        return 3;//weaponItem.getAttackSpeed(); // actualy we dont have weapon so sets const
    }

    /**
     * weturn actualweapon 
     * @return weapon
     */
    public abstract MuWeapon getActiveWeapon();
    private boolean _currentlyAttacking = false;

    /**
     * @param object 
     * @return knows  thisobiects
     */
    public boolean knownsObject(MuObject object) {
        return _knownObjects.containsKey(object.getObjectId());
    }

    /**
     * Set actual value of MP <br>
     * if value isbehaind max then run  {@link #startMpRegeneration()}
     * if value ismax and task is runed thenstop id {@link #_mpRegenActive}  <br>
     * {@link #stopMpRegeneration()}
     * @param i value to set
     * @see  #startMpRegeneration() uruchamianie uzupelnianie mp
     * @see #stopMpRegeneration() zatrzymanie uzupelniania mp
     * 
     */
    public void setCurentMP(int i) {
        _curentMP = i;
        if (_curentMP >= getMaxMp()) {
            stopMpRegeneration();
            _curentMP = getMaxMp();
        } else if (!_mpRegenActive && !isDead()) {
            startMpRegeneration();
        }

    }

    /**
     * Started mp task 
     * @see #stopMpRegeneration() stops mp task
     */
    private void startMpRegeneration() {
        _mpRegTask = new MpRegenTask(this);
        System.out.println("start regeneracj many");
        _regenTimer.scheduleAtFixedRate(_mpRegTask, 3000, 3000);
        _mpRegenActive = true;

    }

    /**
     * @return maxymalna value of MP
     */
    public int getMaxMp() {
        return _maxMp;
    }

    /**
     * @return actual value of MP
     */
    public double getCurentMp() {

        return _curentMP;
    }

    /**
     * set curent hp valuw
     * @param curHp
     */
    public void setCurentHp(int curHp) {
        System.out.print("HpReg ");
        System.out.print(" O:(" + getObjectId() + ")");
        System.out.println("HP:(" + getCurentHp() + "/" + getMaxHp() + ").");
        _curentHP = curHp;
        if (_curentHP >= getMaxHp()) {
            stopHpRegeneration();
            _curentHP = getMaxHp();
        } else if (!_hpRegenActive && !isDead()) {

            startHpRegeneration();
        }
    }

    /**
     * stoping hp regerations task
     */
    private void startHpRegeneration() {
        System.out.println("Start Hp regeneration");
        _hpRegTask = new HpRegenTask(this);
        _regenTimer.scheduleAtFixedRate(_hpRegTask, 3000, 3000);
        _hpRegenActive = true;

    }

    /**
     * starting respown taski
     */
    public void startRespownTask() {
        System.out.println("starting respown task");
        _respown = new RespownTask(this, getX(), getY());
        _respownTimer.schedule(_respown, 5000);

    }

    /**
     * 
     * @return if i ded
     */
    private boolean isDead() {
        return _curentHP <= 0;
    }

    /**
     * stoping hp regeration task
     */
    private void stopHpRegeneration() {
        if (_hpRegenActive) {
            _hpRegTask.cancel();
            _hpRegTask = null;
            _hpRegenActive = false;
        }
        System.out.println("Hp Regeneration task stopped");
    }

    /**
     * retune max hp value for this obiect
     * @return hp max
     */
    public int getMaxHp() {
        return _maxHp;
    }

    /**
     * 
     * @return actual value of Hp
     */
    public int getCurentHp() {
        return _curentHP;
    }

    /**
     * Consructor
     * @param obiectId theid to bind this object unical
     * @param _x the x pos
     * @param _y the y pos 
     * @param _m the opcode map
     */
    public MuCharacter(short obiectId, byte _x, byte _y, byte _m) {
        super(obiectId, _x, _y, _m);

    }

    /**
     * Method to send package to all knows obiects 
     * @param mov package to send
     * @return array of obiect who recive sends package
     */
    public MuCharacter[] broadcastPacket(ServerBasePacket mov) {
        Set list = getKnownPlayers();
        MuCharacter[] players = (MuCharacter[]) list.toArray(new MuCharacter[list.size()]);
        System.out.println("players to notify:" + players.length + " packet:" + mov.getType());

        for (int i = 0; i < players.length; i++) {
            players[i].sendPacket(mov);
        }

        // send to self
        //sendPacket(mov);

        return players;
    }

    public void reduceCurrentMp(int i) {
        synchronized (_mpLock) {
            _curentMP -= i;
            if (!_mpRegenActive && !isDead()) {
                startMpRegeneration();
            }
        }
    }

    public void reduceCurrentHp(int i, MuCharacter c) {
        synchronized (_hpLock) {
            _curentHP -= i;
            SDMgOnScreen dmg = new SDMgOnScreen(getObjectId(), i, SDMgOnScreen._DMG_NORM);
            c.sendPacket(dmg);
            //sendPacket( dmg);
            if (_curentHP <= 0) {
                System.out.println("Postac zginela");
                IDie();
                _curentHP = 0;
                stopHpRegeneration();
                stopMpRegeneration();
                stopSPRegeneration();

            } else if (!_hpRegenActive) {
                startHpRegeneration();
            }
        }
    }

    private void stopSPRegeneration() {
    }

    /**
     * stopping MPregenertion task 
     */
    private void stopMpRegeneration() {
        if (_mpRegenActive) {
            _mpRegTask.cancel();
            _mpRegTask = null;
            System.out.println("rex MP stopped");
            _mpRegenActive = false;
        }

    }

    public int getAgi() {
        return _agi;
    }

    public int getEvectiveAgi() {
        return _agi + _agiMod;
    }

    public int getCom() {
        return _com;
    }

    public int getEvectiveCom() {
        return _com + _comMod;
    }

    public int getEne() {
        return _ene;
    }

    public String getName() {
        return _name;

    }

    public short getNewX() {
        return (short) _newX;
    }

    public short getNewY() {
        return (short) _newY;
    }

    public int getStr() {
        return _str;
    }

    public MuObject getTarget() {
        return _target;
    }

    public int getTargetID() {
        if (_target != null) {
            return _target.getObjectId();
        }
        return -1;
    }

    public int getVit() {
        return _vit;

    }

    /**
     * base movable method giving info abou moving to all knowns object
     */
    private void IMove() {
        broadcastPacket(new SToMoveID((short) getObjectId(), getNewX(), getNewY(), getDirection()));
    }

    public void incAgi() {
        _agi++;
    }

    public void incCom() {
        _com++;
    }

    public void incEne() {
        _ene++;
        updateMaxMpSp();
    }

    public void updateMaxMpSp() {
        _maxMp = MuClassStatsCalculate.getMaxMp(_clas, _lvl, _ene);
        _maxSP = MuClassStatsCalculate.getMaxSP(_clas, _lvl, _str, _ene);
        if (!_mpRegenActive) {
            _curentMP = _maxMp;
        }
        if (!_spRegenActive) {
            _curentSP = _maxSP;
        }


    }

    public void updateMaxHp() {
        if (_hpRegenActive) {
            _maxHp = MuClassStatsCalculate.getMaxHp(_clas, _lvl, _vit);
        } else {
            _curentHP = _maxHp;
        }

    }

    protected void incraseLvl() {
        System.out.println("Lvl Up");
        _lvl++;
        if (_hpRegenActive) {
            stopHpRegeneration();
        }
        if (_mpRegenActive) {
            stopMpRegeneration();
        }
        if (_spRegenActive) {
            stopSPRegeneration();
        }
        updateMaxHp();
        updateMaxMpSp();
    }

    public void incStr() {
        _str++;
        updateMaxMpSp();
    }

    public void incVit() {
        _vit++;
        updateMaxHp();
    }

    public boolean isInCombat() {
        return _inCombat;
    }

    /**
     * move obiect to new position and send to all knowns players it
     * @param x new x Pos
     * @param y new Y Pos
     */
    public void moveTo(int x, int y) {
        System.out.println(this + " Moving to ->[" + x + "][" + y + "].");
        //first we must chceck we can move
        if (getCurrentWorldRegion().MoveTo(this, x, y)) { // if we moved
            _newX = x;
            _newY = y;
            setX(_newX);
            setY(_newY);
            IMove(); // send we moved
        }
    }

    public void onAttackTimer() {
        _currentAttackTask = null;
        MuCharacter target = (MuCharacter) _attackTarget;
        if (isDead() || target == null || target.isDead() || target.knownsObject(this) || knownsObject(target)) {
            setInCombat(false);
        //  return;
        }
        //double distance = getDistance(target.getX(), target.getY());
        //  if (distance > 2) {
        //      return;
        //    }

        //if(_currentlyAttacking==false)
        {
            MuWeapon weaponitem = getActiveWeapon();
            //if(_currentlyAttacking == false)
            {
                //_currentlyAttacking=true;
                int minDmg = weaponitem.getMinDmg();
                int maxDmg = weaponitem.getMaxDmg();
                int dmg = 0;
                boolean miss = false;

                int hitTarget = getRnd().nextInt(maxDmg - minDmg) + minDmg;
                boolean critic = hitTarget == maxDmg;
                if (critic) {
                    hitTarget *= 2;
                }

                int pDef = target.getPhysicalDefense();
                dmg = hitTarget - pDef;
                if (dmg < 0) {
                    dmg = 0;
                } // miss

                if (!isInCombat() && !miss) {
                    setInCombat(true);
                }


                _hitTimer.schedule(new HitTask(this, target, (int) dmg, 0X00), calculateHitSpeed(weaponitem, 1));
                onHitTimer(target, dmg, 0x00);

            //SIdAttackId = new SIdAttackId(getObjectId()),_attackTarget.getObjectId());
            //broadcastPacket(attack);

            }

        }


    }

    private long calculateHitSpeed(MuWeapon weaponitem, int i) {

        return 50;
    }
    private static Timer _hitTimer = new Timer(true);

    public int getPhysicalDefense() {
        return _physicalDefense;
    }
    private int _physicalDefense;

    public static Random getRnd() {
        return _rnd;
    }
    private static Random _rnd = new Random();
    private MuObject _attackTarget;

    public void sendPacket(ServerBasePacket mov) {
        // default implementation
    }

    public void setAgi(int agi) {
        _agi = agi;
    }

    public void setClas(int cl) {
        _clas = (byte) cl;
    }

    public void setCom(int com) {
        _com = com;
    }

    public void setEne(int ene) {
        _ene = ene;
    }

    private void setInCombat(boolean b) {
        _inCombat = b;

    }

    public void setLvl(int lvl) {
        _lvl = lvl;
    }

    public void setName(String string) {
        _name = string;

    }

    public void setStr(int str) {
        _str = str;
    }

    public void setTarget(MuObject t) {
        if (t == null) {
            System.out.println("void setTarget(MuObject t) lostpointer");
        }
        if (_target == null && isInCombat()) {
            setInCombat(false);

        }
        _target = t;
    }

    public void setVit(int vit) {
        _vit = vit;
    }

    protected void stopAttackTask() {
        if (_currentAttackTask != null) {
            _currentAttackTask.cancel();
            _currentAttackTask = null;
        }
    }

    public void StartCombat() {

        onAttackTimer();
    // if (_currentAttackTask == null) {
    //  	_currentAttackTask = new AttackTask(this);
    //   	_atackTimer.schedule(_currentAttackTask, 0);
    //   } else {
    //  	System.out.println("pruba wielokrotnego ataku");
    //  }
    }

    public void startAttack(MuCharacter target) {
        if (target == null) {
            setInCombat(false);
        }
        setTarget(target);
        _attackTarget = target;
    //moveTo(target.getX(), target.getY());
    }

    public void setMaxHp(int maxHp) {
        _maxHp = maxHp;
    }

    public int getCurentSP() {
        return _curentSP;
    }

    public void setCurentSp(int curentSP) {
        _curentSP = curentSP;
    }

    public int getMaxSp() {
        return _maxSP;
    }

    public void setMaxSp(int maxSP) {
        _maxSP = maxSP;
    }

    public byte getClas() {
        return _clas;
    }

    public int getDistance(int x, int y) {
        long dx = x - getX();
        long dy = y - getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return (int) distance;
    }

    public int getLvl() {
        return _lvl;
    }

    public void setMaxMp(int maxMp) {
        _maxMp = maxMp;
    }

    public MuAura getAura() {
        return _aura;
    }

    /**
     * basic to string return "
     * [mapid][id][xpos][upos][name][name of class]
     * @return
     */
    @Override
    public String toString() {
        return "[" + getM() + "][" + getObjectId() + "][" + getX() + "," + getY() + "][ " + getName() + "][" + getClass().getSimpleName() + "]";

    }

    @Override
    public void SetPos(int x, int y, int f) {
        super.SetPos(x, y, f);
        _newX = x;
        _newY = y;
    }

    /**
     * spown absic method added this to map
     */
    @Override
    public void ISpown() {
        super.ISpown();
    }

    public void updateKnownsLists() {
    }
}

