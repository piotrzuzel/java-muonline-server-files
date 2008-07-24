package net.sf.jmuserver.gs.muObjects;

import java.util.HashMap;
import java.util.Map;

/**
 * @ToDo Load all types of items, from file, in MuItem instances.<br>
 * @author Marcel
 */
public class MuItem {

    static private Map _allItemStats = new HashMap<Integer, MuItem>();
    
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
    
    private int _itemType; // 0 bron itp    
    
    private byte _groupIndex;
    private byte _index;
    private char[] _itemName;
    private byte _skillType;
    private byte _xSize;
    private byte _ySize;
    private byte _serial;
    private byte _option;
    private byte _drop;
    private byte _level;
    private byte _minDamage;
    private byte _maxDamage;
    private byte _speed;
    private byte _dur;
    private byte _magicDur;
    private byte _magicPW;
    private byte _str;
    private byte _agi;
    private byte _DWSM;
    private byte _DKBK;
    private byte _EME;
    private byte _MG;

    static public MuItem getItemStats(byte GroupIndex, byte Index) {
        return (MuItem) _allItemStats.get(
                Integer.valueOf((int)((GroupIndex << 4) * (Index & 0x00FF))));
    }
    
    static public boolean addItemStats(MuItem ItemStats) {
        if (_allItemStats.containsValue(ItemStats))
            return false;
        else {
            _allItemStats.put(Integer.valueOf(
                    (int)((ItemStats.get_groupIndex() << 4) * (ItemStats.get_index() & 0x00FF))),
                    ItemStats);
            return true;
        }             
    }
    
    public void set_DKBK(byte _DKBK) {
        this._DKBK = _DKBK;
    }

    public void set_DWSM(byte _DWSM) {
        this._DWSM = _DWSM;
    }

    public void set_EME(byte _EME) {
        this._EME = _EME;
    }

    public void set_MG(byte _MG) {
        this._MG = _MG;
    }

    public void set_agi(byte _agi) {
        this._agi = _agi;
    }

    public void set_drop(byte _drop) {
        this._drop = _drop;
    }

    public void set_dur(byte _dur) {
        this._dur = _dur;
    }

    public void set_groupIndex(byte _groupIndex) {
        this._groupIndex = _groupIndex;
    }

    public void set_index(byte _index) {
        this._index = _index;
    }

    public void set_itemName(char[] _itemName) {
        this._itemName = _itemName;
    }

    public void set_itemType(int _itemType) {
        this._itemType = _itemType;
    }

    public void set_level(byte _level) {
        this._level = _level;
    }

    public void set_magicDur(byte _magicDur) {
        this._magicDur = _magicDur;
    }

    public void set_magicPW(byte _magicPW) {
        this._magicPW = _magicPW;
    }

    public void set_maxDamage(byte _maxDamage) {
        this._maxDamage = _maxDamage;
    }

    public void set_minDamage(byte _minDamage) {
        this._minDamage = _minDamage;
    }

    public void set_option(byte _option) {
        this._option = _option;
    }

    public void set_serial(byte _serial) {
        this._serial = _serial;
    }

    public void set_skillType(byte _skillType) {
        this._skillType = _skillType;
    }

    public void set_speed(byte _speed) {
        this._speed = _speed;
    }

    public void set_str(byte _str) {
        this._str = _str;
    }

    public void set_xSize(byte _xSize) {
        this._xSize = _xSize;
    }

    public void set_ySize(byte _ySize) {
        this._ySize = _ySize;
    }

    public byte get_DKBK() {
        return _DKBK;
    }

    public byte get_DWSM() {
        return _DWSM;
    }

    public byte get_EME() {
        return _EME;
    }

    public byte get_MG() {
        return _MG;
    }

    public byte get_agi() {
        return _agi;
    }

    public byte get_drop() {
        return _drop;
    }

    public byte get_dur() {
        return _dur;
    }

    public byte get_groupIndex() {
        return _groupIndex;
    }

    public byte get_index() {
        return _index;
    }

    public char[] get_itemName() {
        return _itemName;
    }

    public int get_itemType() {
        return _itemType;
    }

    public byte get_level() {
        return _level;
    }

    public byte get_magicDur() {
        return _magicDur;
    }

    public byte get_magicPW() {
        return _magicPW;
    }

    public byte get_maxDamage() {
        return _maxDamage;
    }

    public byte get_minDamage() {
        return _minDamage;
    }

    public byte get_option() {
        return _option;
    }

    public byte get_serial() {
        return _serial;
    }

    public byte get_skillType() {
        return _skillType;
    }

    public byte get_speed() {
        return _speed;
    }

    public byte get_str() {
        return _str;
    }

    public byte get_xSize() {
        return _xSize;
    }

    public byte get_ySize() {
        return _ySize;
    }

}
