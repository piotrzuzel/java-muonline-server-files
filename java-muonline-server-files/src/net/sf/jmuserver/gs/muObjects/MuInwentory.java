package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.templates.MuItemT;

public class MuInwentory {

    public static int TradeWindow = 0x80;
    public static int inventory = 0x00;
    public static int vault = 0x02;
    private MuItemT[] _inwentory = new MuItemT[76];

    public MuInwentory() {
    }

    /**
     * put item in inwentory
     * @param where wich slot
     * @param i item
     * @return true when done
     */
    public boolean storeItem(int where, MuItemT i) {

        _inwentory[where] = i;
        return true;
    }

    /**
     * @param s
     * @return return item from slot s
     */
    public MuItemT getItem(int s) {
        return _inwentory[s];
    }
}
