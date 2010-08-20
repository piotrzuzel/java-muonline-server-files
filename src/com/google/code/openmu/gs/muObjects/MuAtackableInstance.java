package com.google.code.openmu.gs.muObjects;

import java.util.HashMap;

import com.google.code.openmu.gs.templates.MuNpc;


public class MuAtackableInstance extends MuNpcInstance {

	private boolean _active;
	private final HashMap _aggroList = new HashMap();

	public MuAtackableInstance(MuNpc tem) {
		super(tem);
		// _live=new MuHeal(50,3);
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean b) {
		_active = b;
	}

	/**
	 * @hmm
	 */
	@Override
	public void IDie() {
		super.IDie();
		System.out.println("i'm day in atackable instance");
	}
}
