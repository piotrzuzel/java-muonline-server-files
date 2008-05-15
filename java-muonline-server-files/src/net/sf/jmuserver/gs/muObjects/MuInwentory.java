package net.sf.jmuserver.gs.muObjects;

public class MuInwentory {

    public static int TradeWindow = 0x80;
    public static int inventory = 0x00;
    public static int vault = 0x02;
    
    private MuItemStore[] _inwent;

    public MuInwentory() {
        _inwent = new MuItemStore[76];
    }

    public void storeItem(int where, MuItemStore i) {
        _inwent[where] = i;
    }

    public MuItemStore getItem(int s) {
        return _inwent[s];
    }
}
