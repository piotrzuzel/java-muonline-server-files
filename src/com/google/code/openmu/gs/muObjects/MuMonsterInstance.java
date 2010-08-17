/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu.gs.muObjects;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.google.code.openmu.gs.serverPackets.SGoneExp;
import com.google.code.openmu.gs.templates.MuNpc;


/**
 * Instance for Mobs
 * 
 * @author Miki i Linka
 */
public class MuMonsterInstance extends MuAtackableInstance {

	private static Logger _log = Logger.getLogger(MuMonsterInstance.class
			.getName());
	private MuMobWalkArea _walkArea = null;
	private boolean _walkActive = false;
	private final boolean _isAgresive = true;
	private boolean _onTargetScan = false;
	/**
	 * demon for Ai tasks
	 */
	private final Timer _aiTimer = new Timer("Ai TAsk Deamon", true);

	public void setWalkArea(MuMobWalkArea w) {
		_walkArea = w;
	}

	private void onWalkTimer() {
		final int nx = _walkArea.getRandX(getX());
		final int ny = _walkArea.getRandY(getY());
		moveTo(nx, ny);
	}

	class RandomWalkingTask extends TimerTask {

		MuMonsterInstance _instance;

		public RandomWalkingTask(MuMonsterInstance c) {
			_instance = c;
		}

		@Override
		public void run() {

			_instance.onWalkTimer();
			// System.out.println(_instance.getClass().getSimpleName() +
			// ".RandomWalk.Run(): DOne");
		}
	}

	private int getSQRDistance(MuPcInstance pc) {
		final int dx = getX() - pc.getX();
		final int dy = getY() - pc.getY();
		return dx * dx + dy * dy;

	}

	private void onTargetScanTask() {
//		int closeDist = 100000;
//		MuPcInstance target = null;
//		final MuPcInstance[] know = (MuPcInstance[]) getKnownPlayers()
//				.toArray();
//		for (final MuPcInstance muPcInstance : know) {
//			final int distance = getSQRDistance(muPcInstance);
//			if (distance < closeDist) {
//				closeDist = distance;
//				target = muPcInstance;
//			}
//		}
//		System.out.println(this + " Found target: " + target);
//		setTarget(target);
	}

	class TargetScanTask extends TimerTask {

		MuMonsterInstance _instance;

		public TargetScanTask(MuMonsterInstance c) {
			_instance = c;
		}

		@Override
		public void run() {
			System.out.println(_instance + " search 4 target");
			_instance.onTargetScanTask();

		}
	}

	public MuMonsterInstance(MuNpc temp) {
		super(temp);
		setMaxHp(temp.getMaxHp());
		setCurentHp(temp.getMaxHp());
		_objectType = 2;

	}

	private RandomWalkingTask _walkTask = null;
	private final Timer _walkTimer = new Timer(true);
	private TargetScanTask _targetScanTask = null;
	private final Timer _targetScanTimer = new Timer(true);

	public void startTargetScan() {
		if (_targetScanTask == null) {
			_targetScanTask = new TargetScanTask(this);
			_targetScanTimer.schedule(_targetScanTask, 10000); // run in next 10
																// sec
			_onTargetScan = true;
		}
	}

	@Override
	public void setTarget(MuObject t) {
		super.setTarget(t);

	}

	/**
	 * 
	 * @return tru is this mob is agresiv (start atack)
	 */
	public boolean isAggressive() {
		return _isAgresive;
	}

	/**
	 * @return true when is actoive scan target task
	 */
	public boolean isTargetScanActive() {
		return _onTargetScan;
	}

	@Override
	public void reduceCurrentHp(int i, MuCharacter c) {
		super.reduceCurrentHp(i, c);
		System.out.println(this + " HP:(" + getCurentHp() + "/" + getMaxHp()
				+ ").");
	}

	/**
	 * Calculate rewards after deth for PcInstane who kill me
	 */
	public void calculateReward() {
		final int _who = getTargetID(); // get id
		final long _exp = getExpReward(); // geting exp reward value
		// Item _item = getItemReward(); // geting item reward
		final MuObject t = MuWorld.getInstance().getObject(_who);
		System.out.println(this + " Calculate Rerawds for :" + t);
		if (t instanceof MuPcInstance) {
			((MuPcInstance) t).sendPacket(new SGoneExp(_who, (int) _exp));
			((MuPcInstance) t).goneExp((int) _exp);
		}
	}

	@Override
	public void IDie() {

		super.IDie();
		calculateReward();
		System.out.println("Iday w MuMonster");
		// broadcastPacket(new SIdGoneDie(getObjectId()));
		// TODO: broadcast to region
		System.out.println("Starting Respown Task");
		startRespownTask();

	}

	public void startRandomWalking() {

		if (_walkTask == null) {
			_walkTask = new RandomWalkingTask(this);
			System.out.println(this + " Startt Random Walk");
			_walkTimer.scheduleAtFixedRate(_walkTask, 6000, 6000);
			_walkActive = true;
		}

	}

	public void stopRandomWalking() {
		if (_walkActive) {
			_walkTask.cancel();
			_walkTask = null;
			System.out.println(this + "Auto walking stoped !!!");
			_walkActive = false;
		}
	}

	// @Override
	// /**
	// * after added to map we get all characters near and send to them its see
	// as
	// */
	// public void ISpown() {
	// // super.ISpown();
	//
	// MuWorld.getInstance().storeObject(this);
	// Vector v = getCurrentWorldRegion().getVisibleObjects(this);
	//
	// updateKnownsLists();
	// // // System.out.println("Spown in MoMonsterInstance");
	// // Object[] players = getKnownPlayers().toArray();
	// // for (Object muPcInstance : players) {
	// // if(muPcInstance instanceof MuPcInstance)
	// // ((MuPcInstance)muPcInstance).UseeMe(this);
	// // }
	//
	// }

	/**
	 * moving object to new posicion problem is the movin from old pos to new
	 * one take in client soeme time if we use automat to move mob then they
	 * must wait for this time before send new moveto also when we move and
	 * check for target that must be after end movement so we need to sleep
	 * theard for this time
	 * 
	 * @param x
	 * @param y
	 */
	@Override
	public void moveTo(int x, int y) {
		super.moveTo(x, y);

	}

	// @Override
	// public void updateKnownsLists() {
	//
	// System.out.println("update knowns in mmonster instance");
	// //new lists
	// ArrayList<MuObject> Mob = new ArrayList<MuObject>();
	// Mob.add(this);
	// ArrayList<MuObject> _toForget = new ArrayList<MuObject>();
	//
	// Collection oldlist = oldgetKnownObjects().values();
	// //secend look for new object and swich it to lists and add also to known
	// list
	// Vector visitable = getCurrentWorldRegion().getVisiblePlayers(this);
	// for (Iterator it = visitable.iterator(); it.hasNext();) {
	// MuObject checked = (MuObject) it.next();
	// if (checked.getObjectId() == getObjectId()) {
	// continue; // if we are next
	// }
	//
	// if (oldlist.contains(checked)) {
	// continue; // allready kow him
	// }
	// //if is player
	// if (checked instanceof MuPcInstance) {
	//
	// System.out.println("monster found player meet " + checked);
	// ((MuPcInstance) checked).sendPacket(new SNpcMiting(Mob));
	// addKnownObject(checked);
	// checked.addKnownObject(this);
	//
	// }
	// }
	// //check old list of known objects for obj that are no longer visible and
	// remove them
	// for (Iterator<MuObject> it = oldlist.iterator(); it.hasNext();) {
	// MuObject muObject = it.next();
	// if (!visitable.contains(muObject)) {
	// //_toForget.add(muObject);
	// removeKnownObject(muObject,RemKnow_ForgetID);
	// muObject.removeKnownObject(this,RemKnow_ForgetID);
	// }
	// }
	// //now wi have all knowns, and to forget objects
	// //so send packages
	//
	//
	// }
}
