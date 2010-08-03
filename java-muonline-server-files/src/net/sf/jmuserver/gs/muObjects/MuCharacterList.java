package net.sf.jmuserver.gs.muObjects;

import java.util.HashMap;
import java.util.Map;
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

	ClientThread _th = null;
        
public MuCharacterList() {

}
/**
 * get character base fron name
 * @param _name to get
 * @return character base 
 */
    public MuCharacterBase getChar(String _name) {
        for (int i = 0; i < _chars.length; i++) {
            MuCharacterBase muCharacterBase = _chars[i];
            if(muCharacterBase!=null)
            {
                if(muCharacterBase.getName().compareTo(_name)==0)
                    return muCharacterBase;
            }
        }
        return null;
    }
    /**
     * 
     * @return firs free slot
     */
        public byte getFirstFreeSlot() {
                byte i;
		for (i=0; i<5; i++)
                    if (_chars[i]==null)
                        break;
                return i;
        }

	public boolean addNew(MuCharacterBase c) {
                int count = getCharsCount();
		if (count >= 5)
			return false;
		byte i = getFirstFreeSlot();
                System.out.println("Added new character to chlist :"+c.getName()+" on pos "+i);
		_chars[i] = c;		
		return true;
	}

        public boolean removeChar(String name) {
            byte i;
            for (i=0; i<5; i++)
                if (name.equalsIgnoreCase(_chars[i].getName()))
                    break;
            if (i<5) {
                _chars[i] = null;
                return true;
            } else
                return false;
        }
        
	public MuCharacterBase getChar(int nr) {	
            return _chars[nr];
	}

	public int getCharsCount() {
            int count = 0;
            for (byte i=0; i<5; i++)
                if (_chars[i]!=null)
                    count++;
            return count;
	}
	public boolean needRead() {
		return _needRead;
	}
    public void noNeedRead()
    {
    	_needRead=false;
    }
}
