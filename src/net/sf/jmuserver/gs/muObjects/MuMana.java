package net.sf.jmuserver.gs.muObjects;

public class MuMana {
	private final int _MMana;
	private int _mana;
	private int _mOnSec;
	private long _lastCheck;

	public MuMana(int mmana, int monsec) {
		_lastCheck = System.currentTimeMillis() / 1000;
		_MMana = _mana = mmana;
	}

	public boolean useMana(int pm) {
		checkMana();
		if (pm <= _mana) {
			_mana -= pm;
			return true;
		} else {
			return false;
		}
	}

	private void checkMana() {
		final long time = System.currentTimeMillis() / 1000;
		final long ntime = time - _lastCheck;
		_lastCheck = time;
		final int npm = (int) (ntime * _mOnSec);
		if ((npm + _mana) >= _MMana) {
			_mana = _MMana;
		} else {
			_mana += npm;
		}

	}

	public int getManaValue() {
		checkMana();
		return _mana;
	}

	public void incraseMana(int h) {
		if ((_mana + h) >= _MMana) {
			_mana = _MMana;
		} else {
			_mana += h;
		}
	}

}
