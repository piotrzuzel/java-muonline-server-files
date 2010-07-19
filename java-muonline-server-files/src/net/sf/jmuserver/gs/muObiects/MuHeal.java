package net.sf.jmuserver.gs.muObiects;


public class MuHeal {
private int _mHeal=0;
private int _heal=0;
private int _hOnSec=0;
private long _lastCheck=0;

public MuHeal(int mheal,int hOnSec) {
	_mHeal=mheal;
	_heal=mheal;
	_hOnSec=hOnSec;
	_lastCheck=System.currentTimeMillis()/1000;
}

public boolean isLive()
{
	return (_heal<=0);
}

public int getLive()
{
	checkLive();
	return _heal;
}

private void checkLive() {
	if (_heal==_mHeal) 
	{
		_lastCheck=System.currentTimeMillis();
		return;
	}
	long time=System.currentTimeMillis()/1000;
	time=time-_lastCheck;
	int HtoAd=(int) (time * _hOnSec);
	int heal=_heal+HtoAd;
	if (heal>_mHeal)heal=_mHeal;
	_heal=heal;	
	_lastCheck=System.currentTimeMillis()/1000;
}

public int Hit(int howmany) {
	checkLive();
	_heal-=howmany;
	return _heal;
}

public int heal(int hmany) {
	checkLive();
	_heal+=hmany;
	if (_heal>_mHeal) _heal=_mHeal;
	return _heal;
}

public void restart()
{
	_heal=_mHeal;
	_lastCheck=System.currentTimeMillis();
}

protected int get_heal() {
	return _heal;
}

protected void set_heal(int _heal) {
	this._heal = _heal;
}

protected int get_hOnSec() {
	return _hOnSec;
}

protected void set_hOnSec(int onSec) {
	_hOnSec = onSec;
}

protected long get_lastCheck() {
	return _lastCheck;
}

protected void set_lastCheck(long check) {
	_lastCheck = check;
}

protected int get_mHeal() {
	return _mHeal;
}

protected void set_mHeal(int heal) {
	_mHeal = heal;
}


}