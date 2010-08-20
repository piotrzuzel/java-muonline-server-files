package com.google.code.openmu.gs.muObjects;

//~--- non-JDK imports --------------------------------------------------------
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.code.openmu.gs.MuConnection;
import com.google.code.openmu.gs.serverPackets.SForgetId;
import com.google.code.openmu.gs.serverPackets.SIdGoneDie;
import com.google.code.openmu.gs.serverPackets.SLiveStats;
import com.google.code.openmu.gs.serverPackets.SLvlUp;
import com.google.code.openmu.gs.serverPackets.SManaStaminaStats;
import com.google.code.openmu.gs.serverPackets.ServerBasePacket;
import com.google.code.openmu.gs.stats.MuClassStatsCalculate;
import com.google.code.openmu.gs.templates.MuWeapon;


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
	 * @param obiectId
	 *            id obiject in game
	 * @param _x
	 *            wsp
	 * @param _y
	 *            Wsp y
	 * @param _m
	 *            Byte codeof map
	 */
	public MuPcInstance(short obiectId, byte _x, byte _y, byte _m) {
		super(obiectId, _x, _y, _m);

		// TODO Auto-generated constructor stub
	}

	/**
	 * Set id to database
	 * 
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
	 * 
	 * @param i
	 *            ilosc doswiadczenia
	 */
	public void goneExp(int i) {
		_exp += i;
		System.out.println(this + " Got Exp " + i + "/" + _expOnNewLvl);
		if (_exp >= _expOnNewLvl) {
			System.out.println(this + "LVL UP!!");
			_exp = _expOnNewLvl; // like in clijent
			incraseLvl();
		}

	}

	//
	// /**
	// * dodaje experance
	// * @param hm doswiadczenei do ododania
	// */
	// public void addEpx(int hm) {
	// _exp += hm;
	//
	// if (_exp > _expOnNewLvl) {
	// System.out.println("Gone nex lvl");
	// }
	// }
	//
	/**
	 * remove me from everyehere
	 */
	public void deleteMe() {

		// stop all scheduled events
		final MuWorld world = MuWorld.getInstance();

		world.removeObject(this);		
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
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.code.openmu.gs.muObjects.MuCharacter#sendPacket(com.google.code.openmu
	 * .gs.serverPackage.ServerBasePacket)
	 */
	@Override
	public void sendPacket(ServerBasePacket packet) {
		System.out.println("To PcInstance ID: " + this.getObjectId());
		try {
			getNetConnection().sendPacket(packet);
		} catch (final IOException ex) {
			Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE,
					"Null POint w get NetConecton", ex);
		} catch (final Throwable ex) {
			Logger.getLogger(MuPcInstance.class.getName()).log(Level.SEVERE,
					packet.getClass().toString(), ex);
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
	 * 
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
				final SManaStaminaStats sm = new SManaStaminaStats(
						SManaStaminaStats._UPDATE_MAX);
				sm.setMana(getMaxMp());
				sm.setStamina(getMaxSp());
				_netConnection.sendPacket(sm);
			} catch (final IOException ex) {
				Logger.getLogger(MuPcInstance.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (final Throwable ex) {
				Logger.getLogger(MuPcInstance.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

	}

	@Override
	public void setCurentHp(int curHp) {

		super.setCurentHp(curHp);

		final SLiveStats hp = new SLiveStats(SLiveStats._UPDATE_CUR);

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

				final SLiveStats ml = new SLiveStats(SLiveStats._UPDATE_MAX);

				ml.setLive(getMaxHp());
				_netConnection.sendPacket(ml);
			} catch (final IOException ex) {
				Logger.getLogger(MuPcInstance.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (final Throwable ex) {
				Logger.getLogger(MuPcInstance.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Set network onnection
	 * 
	 * @param connection
	 */
	public void setNetConnection(MuConnection connection) {
		_netConnection = connection;
	}

	/**
	 * change my status propablu neweruse
	 * 
	 * @param s
	 */
	@Override
	public void changeStatus(int s) {

		// broadcastPacket()
		setStatus(s);
	}

	/**
	 * Return Exp needed to gion new lvl
	 * 
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
	 * 
	 * @return
	 */
	public long getExp() {
		return _exp;
	}

	/**
	 * return zen value
	 * 
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
	 * 
	 * @ISUASE getvalues dependsof weapon, skill etc
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
		setMaxSp(MuClassStatsCalculate.getMaxSP(getClas(), getLvl(), getStr(),
				getEne()));
		setCurentHp(getMaxHp());
		setCurentMP(getMaxMp());
		setCurentSp(getMaxSp());
	}

	@Override
	public MuWeapon getActiveWeapon() {
		final MuWeapon w = new MuWeapon();

		w.setAttackSpeed(10);
		w.setMaxDmg(150);
		w.setMinDmg(100);
		System.out.println("returned wapon from user");

		return w;
	}

	// @Override
	// public void spownMe() {
	//
	// // super.spownMe();
	// Vector temp = MuWorld.getInstance().getVisibleObjects(this);
	// ArrayList monstersTemp = new ArrayList();
	// ArrayList pcTemp = new ArrayList();
	//
	// for (int i = 0; i < temp.size(); i++) {
	// ((MuObject) temp.get(i)).addKnownObject(this);
	//
	// if (temp.get(i) instanceof MuMonsterInstance) {
	// monstersTemp.add(temp.add(i));
	// } else if (temp.get(i) instanceof MuPcInstance) {
	// pcTemp.add(temp.get(i));
	// }
	// }
	//
	// sendPacket(new SNpcMiting(monstersTemp));
	//
	// // sendPacket(new );
	// }


	// @Override
	// public void ISpown() {
	// super.ISpown();
	// System.out.println("ISpown in MuPcInstance;");
	// ArrayList<MuObject> _playets = new ArrayList<MuObject>();
	// ArrayList<MuObject> _mobs = new ArrayList<MuObject>();
	// ArrayList<MuObject> _items = new ArrayList<MuObject>();
	// for (MuObject muObject : _knownObjects.values()) {
	//
	// if (muObject instanceof MuPcInstance) {
	// _playets.add((MuPcInstance) muObject);
	// }
	// if (muObject instanceof MuMonsterInstance) {
	// _mobs.add((MuMonsterInstance) muObject);
	// }
	// if (muObject instanceof MuItemOnGround) {
	// _items.add((MuItemOnGround) muObject);
	// }
	// }
	// sendPacket(new SNpcMiting(_mobs));
	// sendPacket(new SPlayersMeeting(_playets));
	// sendPacket(new SMeetItemOnGround(_items));
	// // Notify other players of my spawn
	// ArrayList<MuObject> _thisPlayer = new ArrayList<MuObject>();
	// _thisPlayer.add(this);
	// SPlayersMeeting newSPM = new SPlayersMeeting(_thisPlayer);
	// for (int i = 0; i < _playets.size(); i++) {
	// if (!(_playets.get(i) instanceof MuPcActorInstance)) {
	// ((MuPcInstance) _playets.get(i)).sendPacket(newSPM);
	// }
	// }
	// }

	// @Override
	// public void moveTo(int newx, int newy) {
	//
	// super.moveTo(newx, newy);
	// updateKnownsLists();
	// }

	/**
	 * chceck in range object UPDATE: Not used. Use the visible objects list
	 * instead.
	 * 
	 * @param t
	 * @return true when object is in visitable area
	 */
	public boolean checkInRage(MuObject t) {
		final int chx = t.getX() / 5;
		final int chy = t.getY() / 5;
		final int myx = getX() / 5;
		final int myy = getY() / 5;
		final int rangeX1 = myx - 3;
		final int rangeX2 = myx + 3;
		final int rangeY1 = myy - 3;
		final int rangeY2 = myy + 3;
		if ((rangeX1 <= chx) && (chx <= rangeX2) && (rangeY1 <= chy)
				&& (chy <= rangeY2)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void incraseLvl() {
		super.incraseLvl();
		_expOnNewLvl = MuClassStatsCalculate.CalculateExpOnNewLvl(getLvl());
		setLP(getLp() + MuClassStatsCalculate.getSPOnNewLvl(getClas()));
		sendPacket(new SLvlUp(this));

	}
}
