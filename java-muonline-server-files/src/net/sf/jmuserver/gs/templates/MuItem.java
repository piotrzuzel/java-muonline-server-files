package net.sf.jmuserver.gs.templates;


/**
 * @author MikiOne
 * @since First Try to implements item instancce class
 */
public abstract class MuItem {
    
    public static final int _SLOT_LHEND	   = 0x00;
    public static final int _SLOT_RHEND	   = 0x01;
    public final static int _SLOT_HELM	    = 0x02;
    public static final int _SLOT_ARMUR	   = 0x03;
    public static final int _SLOT_PANTS	   = 0x04;
    public static final int _SLOT_GLOVIES	 = 0x05;
    public static final int _SLOT_BOOTS	   = 0x06;
    public final static int _SLOT_WINGS	   = 0x07;
    public final static int _SLOT_IMP	     = 0x08;
    public static final int _SLOT_PEDANT	  = 0x09;
    public static final int _SLOT_LRING	   = 0x0a;
    public static final int _SLOT_RRING	   = 0x0b;

    public static final int _SLOT_INWENTORY       = 0x00;
    public static final int _SLOT_STORE	   = 0x01;
    public static final int _SLOT_DEPO	    = 0x02;
    public static final int _SLOT_SEL	     = 0x03;
    public static final int _SLOT_PALKA     =0x04;

    public static final int _ITEM_TYPE_UNK	= 0x00; // unknown
    public static final int _ITEM_TYPE_WEA	= 0x01; // weapon
    public static final int _ITEM_TYPE_IMP	= 0x02; // imp satan
    public static final int _ITEM_TYPE_HELM       = 0x03; // helm
    public static final int _ITEM_TYPE_PEDA       = 0x04; // pedant
    public static final int _ITEM_TYPE_WING       = 0x05; // wings
    public static final int _ITEM_TYPE_ARMU       = 0x06; // armurs
    public static final int _ITEM_TYPE_SHEL       = 0x07; // shelds
    public static final int _ITEM_TYPE_GLOV       = 0x08; // glovies
    public static final int _ITEM_TYPE_PANT       = 0x09; // pants
    public static final int _ITEM_TYPE_RING       = 0x0a; // rings
    public static final int _ITEM_TYPE_BOOT       = 0x0b; // boots
    public static final int _ITEM_TYPE_GEM	= 0x0c; // jawels
    public static final int _ITEM_TYPE_POTTION    = 0x0d; // pottiony
    public static final int _ITEM_TYPE_PET	= 0x0e; // pets : kruk ,
							// kon ,
    public static final int _ITEM_TYPE_ARROW    = 0x0f; // arrows belts
    public static final int _ITEM_TYPE_QUEST    =0x10; //  quest items
    public static final int _ITEM_TYPE_GIFTBOX   =0x11; // boxes hearts ...
    
    // fennir
    public static final int _ITEM_TYPE_SPELL_BOOK = 0x12;
    public static final int _ITEM_TYPE_SKILL_ORB  = 0x13;
    public static final int _ITEM_TYPE_QUST       = 0x14;

    public static final int _ITEM_NORMAL	  = 0x00; //normal item
    public static final int _ITEM_EXCELENT	= 0x01; // excelent item
    public static final int _ITEM_ANCED	   = 0x02; // inced items

    public static final int _ITEM_LUCK	    = 0x00;

    /**
     * slot where is put item 
     */
    private int	     _slot;		       // slot itemu
    
    /**
     * window id 
     */
    private int	     _winId;		      // where
    
    /**
     * first type of item
     */
    private int	     _type1;		      // type
    /**
     * enum type of item tj excelent , normal ,etc 
     */
    private int	     _type2;		      // exe norm ainced
    
    /**
     * index
     */
    private int	     _index;		      // index itemu
    
    /**
     * grup of item 
     */
    private int	     _grup;		       // grupa itemu

    /**
     * max durabilaty
     */
    private int	     _maxDura;		    // max durabilaty
    /**
     *Actual durabilaty 
     */
    private int	     _curDura;		    // actual dura

    /**
     * lvl of item
     */
    private int	     _lvl;			// lvl itemu

    /**
     * @return Curent durablilaty (int)
     */
    public int getCurDura() {
	return _curDura;
    }

    /**
     * set actual durabilaty
     * @param curDura value to set
     */
    public void setCurDura(int curDura) {
	_curDura = curDura;
    }

    /**
     * @return Grup Of item
     */
    public int getGrup() {
	return _grup;
    }

    /**
     * ustawia grupe itemu
     * @param grup value to set
     */
    public void setGrup(int grup) {
	_grup = grup;
    }

    public int getIndex() {
	return _index;
    }

    public void setIndex(int index) {
	_index = index;
    }

    public int getLvl() {
	return _lvl;
    }

    public void setLvl(int lvl) {
	_lvl = lvl;
    }

    public int getMaxDura() {
	return _maxDura;
    }

    public void setMaxDura(int maxDura) {
	_maxDura = maxDura;
    }

    public int getSlot() {
	return _slot;
    }

    public void setSlot(int slot) {
	_slot = slot;
    }

    public int getType1() {
	return _type1;
    }

    public void setType1(int type1) {
	_type1 = type1;
    }

    public int getType2() {
	return _type2;
    }

    public void setType2(int type2) {
	_type2 = type2;
    }

    public int getWinId() {
	return _winId;
    }

    public void setWinId(int winId) {
	_winId = winId;
    }

}
