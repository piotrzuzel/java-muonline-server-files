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

	ClientThread _th = null;
        
public MuCharacterList() {

}
    
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
