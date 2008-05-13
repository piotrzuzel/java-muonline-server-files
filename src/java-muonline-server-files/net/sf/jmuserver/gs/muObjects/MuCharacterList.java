package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.ClientThread;

/**
 * 
 */

/**
 * @author MikiOne
 * 
 */
public class MuCharacterList {
	/**
	 * 
	 */
	
	private boolean _needRead=true;
	private MuCharacterBase[] _chars = { null, null, null, null, null };

	private int ch_c = 0; // iosc postai;

	ClientThread _th = null;
public MuCharacterList() {

}
	public boolean addNew(MuCharacterBase c) {
		if (ch_c >= 5)
			return false;
		System.out.println("dodaje nowa postac do ch_list :"+c.getName()+" na pozycji "+ch_c);
		_chars[ch_c] = c;
		ch_c++;
		return true;
	}

	public MuCharacterBase getChar(int nr) {
		return _chars[nr];
	}

	public int getCharsCount() {
		return ch_c;
	}
	public boolean needRead() {
		return _needRead;
	}
    public void noNeedRead()
    {
    	_needRead=false;
    }
}
