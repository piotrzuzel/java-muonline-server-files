package com.google.code.openmu.gs.muObjects;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.code.openmu.gs.templates.MuItemHex;


/**
 * Contains all item types and stats, which are loaded ONCE from file when
 * initiating the world environment. Any item in game must have a reference to
 * an instance of this class.
 * 
 * @see MuInventoryItem
 * @see MuItemOnGround
 * @author Marcel
 */
public class MuItemStats {

	static private Map _allItemStats = new HashMap<Integer, MuItemStats>();
	public static final int _SLOT_LHEND = 0x00;
	public static final int _SLOT_RHEND = 0x01;
	public final static int _SLOT_HELM = 0x02;
	public static final int _SLOT_ARMUR = 0x03;
	public static final int _SLOT_PANTS = 0x04;
	public static final int _SLOT_GLOVIES = 0x05;
	public static final int _SLOT_BOOTS = 0x06;
	public final static int _SLOT_WINGS = 0x07;
	public final static int _SLOT_IMP = 0x08;
	public static final int _SLOT_PEDANT = 0x09;
	public static final int _SLOT_LRING = 0x0a;
	public static final int _SLOT_RRING = 0x0b;
	public static final int _SLOT_INWENTORY = 0x00;
	public static final int _SLOT_STORE = 0x01;
	public static final int _SLOT_DEPO = 0x02;
	public static final int _SLOT_SEL = 0x03;
	public static final int _SLOT_PALKA = 0x04;
	public static final int _ITEM_TYPE_UNK = 0x00; // unknown
	public static final int _ITEM_TYPE_WEA = 0x01; // weapon
	public static final int _ITEM_TYPE_IMP = 0x02; // imp satan
	public static final int _ITEM_TYPE_HELM = 0x03; // helm
	public static final int _ITEM_TYPE_PEDA = 0x04; // pedant
	public static final int _ITEM_TYPE_WING = 0x05; // wings
	public static final int _ITEM_TYPE_ARMU = 0x06; // armurs
	public static final int _ITEM_TYPE_SHEL = 0x07; // shelds
	public static final int _ITEM_TYPE_GLOV = 0x08; // glovies
	public static final int _ITEM_TYPE_PANT = 0x09; // pants
	public static final int _ITEM_TYPE_RING = 0x0a; // rings
	public static final int _ITEM_TYPE_BOOT = 0x0b; // boots
	public static final int _ITEM_TYPE_GEM = 0x0c; // jawels
	public static final int _ITEM_TYPE_POTTION = 0x0d; // pottiony
	public static final int _ITEM_TYPE_PET = 0x0e; // pets : kruk ,
	// kon ,
	public static final int _ITEM_TYPE_ARROW = 0x0f; // arrows belts
	public static final int _ITEM_TYPE_QUEST = 0x10; // quest items
	public static final int _ITEM_TYPE_GIFTBOX = 0x11; // boxes hearts ...
	// fennir
	public static final int _ITEM_TYPE_SPELL_BOOK = 0x12;
	public static final int _ITEM_TYPE_SKILL_ORB = 0x13;
	public static final int _ITEM_TYPE_QUST = 0x14;
	public static final int _ITEM_NORMAL = 0x00; // normal item
	public static final int _ITEM_EXCELENT = 0x01; // excelent item
	public static final int _ITEM_ANCED = 0x02; // inced items
	public static final int _ITEM_LUCK = 0x00;

	private int _itemType; // 0 bron itp
	private byte _groupIndex;
	private byte _index;
	private byte _itemSlot;
	private String _itemName;
	private byte _skillType;
	private byte _xSize;
	private byte _ySize;
	private boolean _serial;
	private boolean _option;
	private boolean _drop;
	private int _level;
	private int _reqLevel;
	private int _defence;
	private int _magicDefence;
	private int _minDamage;
	private int _maxDamage;
	private int _speed;
	private int _dur;
	private int _magicDur;
	private int _magicPW;
	private int _ene;
	private int _str;
	private int _agi;
	private int _zen;
	private boolean _DWSM;
	private boolean _DKBK;
	private boolean _EME;
	private boolean _MG;

	@Override
	public String toString() {
		return "[" + _groupIndex + "][" + _index + "][" + _itemName + "]";
	}

	/**
	 * Retrieves the item stats, if existent, based on the group identifier and
	 * the index within the group.
	 * 
	 * @param GroupIndex
	 * @param Index
	 * @return Base stats of the item, or null if item does not exist
	 */
	static public MuItemStats getItemStats(byte GroupIndex, byte Index) {
		return (MuItemStats) _allItemStats.get(Integer
				.valueOf(((GroupIndex << 4) + (Index & 0x00FF))));
	}

	/**
	 * Retrieves the item stats, if existent, based on HexItem instance.
	 * 
	 * @param hex
	 * @return Base stats of the item, or null if item does not exist
	 */
	static public MuItemStats getItemStats(MuItemHex hex) {
		final byte GroupIndex = hex.getGroup();
		final byte Index = hex.getIndex();
		return getItemStats(GroupIndex, Index);
	}

	/**
	 * Adds a MuItemStats instance to the global list.<br>
	 * Only used when loading items.
	 * 
	 * @param ItemStats
	 *            MuItemStats)
	 * @return True is succeeded, false otherwise.
	 */
	static private boolean addItemStats(MuItemStats ItemStats) {
		if (_allItemStats.containsValue(ItemStats)) {
			return false;
		} else {
			_allItemStats
					.put(Integer.valueOf(((ItemStats.get_groupIndex() << 4) + (ItemStats
							.get_index() & 0x00FF))), ItemStats);
			return true;
		}
	}

	/**
	 * Loads the items information from the WebZen item file.<br>
	 * Only used once, upon world initialization.
	 * 
	 * @param FileName
	 * @return True if succeeded, false otherwise.
	 */
	static public boolean loadItems(String FileName) {
		boolean result = false;
		try {
			final FileInputStream fstream = new FileInputStream(FileName);
			final DataInputStream in = new DataInputStream(fstream);
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					in));
			String strLine;
			byte group = -1;
			boolean newGroup = true;
			result = true;
			while (((strLine = br.readLine()) != null) && result) {
				if (!strLine.isEmpty()) {
					if (strLine.charAt(0) != '/') {
						final String[] line = getTokens(new StringTokenizer(
								strLine, "\t"));
						if (newGroup) {
							group = Byte.valueOf(line[0]);
							newGroup = false;
							continue;
						}
						if (line[0].equalsIgnoreCase("end")) {
							if (newGroup) {
								result = false;
								break;
							} else {
								newGroup = true;
								continue;
							}
						}
						final MuItemStats newItem = new MuItemStats();
						newItem.set_groupIndex(group);
						newItem.set_index(Byte.valueOf(line[0]));
						newItem.set_xSize(Byte.valueOf(line[1]));
						newItem.set_ySize(Byte.valueOf(line[2]));
						newItem.set_serial(Boolean.valueOf(line[3]));
						// line[4] ??
						newItem.set_drop(Boolean.valueOf(line[5]));
						newItem.set_itemName(line[6]);
						if ((group >= 0) && (group < 6)) { // weapons
							if (line.length != 19) {
								result = false;
								break;
							}
							newItem.set_level(Integer.valueOf(line[7]));
							newItem.set_minDamage(Integer.valueOf(line[8]));
							newItem.set_maxDamage(Integer.valueOf(line[9]));
							newItem.set_speed(Integer.valueOf(line[10]));
							newItem.set_dur(Integer.valueOf(line[11]));
							newItem.set_magicDur(Integer.valueOf(line[12]));
							newItem.set_str(Integer.valueOf(line[13]));
							newItem.set_agi(Integer.valueOf(line[14]));
							newItem.set_DWSM(Boolean.valueOf(line[15]));
							newItem.set_DKBK(Boolean.valueOf(line[16]));
							newItem.set_EME(Boolean.valueOf(line[17]));
							newItem.set_MG(Boolean.valueOf(line[18]));
						} else if (group == 6) { // shields
							if (line.length != 16) {
								result = false;
								break;
							}
							newItem.set_level(Integer.valueOf(line[7]));
							newItem.set_defence(Integer.valueOf(line[8]));
							newItem.set_dur(Integer.valueOf(line[9]));
							newItem.set_str(Integer.valueOf(line[10]));
							newItem.set_agi(Integer.valueOf(line[11]));
							newItem.set_DWSM(Boolean.valueOf(line[12]));
							newItem.set_DKBK(Boolean.valueOf(line[13]));
							newItem.set_EME(Boolean.valueOf(line[14]));
							newItem.set_MG(Boolean.valueOf(line[15]));
						} else if ((group > 6) && (group < 12)) { // set items
							if (line.length != 17) {
								result = false;
								break;
							}
							newItem.set_level(Integer.valueOf(line[7]));
							newItem.set_defence(Integer.valueOf(line[8]));
							newItem.set_magicDefence(Integer.valueOf(line[9]));
							newItem.set_dur(Integer.valueOf(line[10]));
							newItem.set_str(Integer.valueOf(line[11]));
							newItem.set_agi(Integer.valueOf(line[12]));
							newItem.set_DWSM(Boolean.valueOf(line[13]));
							newItem.set_DKBK(Boolean.valueOf(line[14]));
							newItem.set_EME(Boolean.valueOf(line[15]));
							newItem.set_MG(Boolean.valueOf(line[16]));
						} else if (group == 12) { // wings, orbs, dl scrolls
							if (line.length != 19) {
								result = false;
								break;
							}
							newItem.set_level(Integer.valueOf(line[7]));
							newItem.set_defence(Integer.valueOf(line[8]));
							newItem.set_dur(Integer.valueOf(line[9]));
							newItem.set_reqLevel(Integer.valueOf(line[10]));
							newItem.set_ene(Integer.valueOf(line[11]));
							newItem.set_str(Integer.valueOf(line[12]));
							newItem.set_agi(Integer.valueOf(line[13]));
							newItem.set_zen(Integer.valueOf(line[14]));
							newItem.set_DWSM(Boolean.valueOf(line[15]));
							newItem.set_DKBK(Boolean.valueOf(line[16]));
							newItem.set_EME(Boolean.valueOf(line[17]));
							newItem.set_MG(Boolean.valueOf(line[18]));
						} else if (group == 13) { // rings, pendants, misc equip
													// items
							if (line.length != 17) {
								result = false;
								break;
							}
							newItem.set_level(Integer.valueOf(line[7]));
							newItem.set_dur(Integer.valueOf(line[8]));
							newItem.set_reqLevel(Integer.valueOf(line[9]));
							newItem.set_DWSM(Boolean.valueOf(line[13]));
							newItem.set_DKBK(Boolean.valueOf(line[14]));
							newItem.set_EME(Boolean.valueOf(line[15]));
							newItem.set_MG(Boolean.valueOf(line[16]));
						} else if (group == 14) { // potions, jewels, boxes,
													// quest items
							if (line.length != 9) {
								result = false;
								break;
							}
							// newItem.set_value(Integer.valueOf(line[7]));
							newItem.set_level(Integer.valueOf(line[8]));
						} else if (group == 15) { // TODO scrolls

						} else {
							result = false;
							break;
						}
						if (!addItemStats(newItem)) {
							result = false;
							break;
						}
					}
				}
			}
			in.close();
		} catch (final Exception e) {
			System.err.println("Error: " + e.getMessage());
			result = false;
		}
		return result;
	}

	/**
	 * Splits a StringTokenizer into an array of Strings, in order to get easier
	 * access to tokens. <br>
	 * Used when loading item stats.
	 * 
	 * @param StringTokenizer
	 * @return Array of Strings
	 */
	static private String[] getTokens(StringTokenizer st) {
		final String[] result = new String[st.countTokens()];
		byte i = 0;
		while (st.hasMoreTokens()) {
			result[i] = st.nextToken();
			i++;
		}
		return result;
	}

	public void set_DKBK(boolean _DKBK) {
		this._DKBK = _DKBK;
	}

	public void set_DWSM(boolean _DWSM) {
		this._DWSM = _DWSM;
	}

	public void set_EME(boolean _EME) {
		this._EME = _EME;
	}

	public void set_MG(boolean _MG) {
		this._MG = _MG;
	}

	public void set_agi(int _agi) {
		this._agi = _agi;
	}

	public void set_drop(boolean _drop) {
		this._drop = _drop;
	}

	public void set_dur(int _dur) {
		this._dur = _dur;
	}

	public void set_groupIndex(byte _groupIndex) {
		this._groupIndex = _groupIndex;
	}

	public void set_index(byte _index) {
		this._index = _index;
	}

	public void set_itemSlot(byte _itemSlot) {
		this._itemSlot = _itemSlot;
	}

	public void set_itemName(String _itemName) {
		this._itemName = _itemName;
	}

	public void set_itemType(int _itemType) {
		this._itemType = _itemType;
	}

	public void set_level(int _level) {
		this._level = _level;
	}

	public void set_defence(int _defence) {
		this._defence = _defence;
	}

	public void set_magicDefence(int _magicDefence) {
		this._magicDefence = _magicDefence;
	}

	public void set_magicDur(int _magicDur) {
		this._magicDur = _magicDur;
	}

	public void set_magicPW(int _magicPW) {
		this._magicPW = _magicPW;
	}

	public void set_maxDamage(int _maxDamage) {
		this._maxDamage = _maxDamage;
	}

	public void set_minDamage(int _minDamage) {
		this._minDamage = _minDamage;
	}

	public void set_option(boolean _option) {
		this._option = _option;
	}

	public void set_serial(boolean _serial) {
		this._serial = _serial;
	}

	public void set_skillType(byte _skillType) {
		this._skillType = _skillType;
	}

	public void set_speed(int _speed) {
		this._speed = _speed;
	}

	public void set_str(int _str) {
		this._str = _str;
	}

	public void set_ene(int _ene) {
		this._ene = _ene;
	}

	public void set_reqLevel(int _reqLevel) {
		this._reqLevel = _reqLevel;
	}

	public void set_zen(int _zen) {
		this._zen = _zen;
	}

	public void set_xSize(byte _xSize) {
		this._xSize = _xSize;
	}

	public void set_ySize(byte _ySize) {
		this._ySize = _ySize;
	}

	public boolean get_DKBK() {
		return _DKBK;
	}

	public boolean get_DWSM() {
		return _DWSM;
	}

	public boolean get_EME() {
		return _EME;
	}

	public boolean get_MG() {
		return _MG;
	}

	public int get_agi() {
		return _agi;
	}

	public boolean get_drop() {
		return _drop;
	}

	public int get_dur() {
		return _dur;
	}

	public byte get_groupIndex() {
		return _groupIndex;
	}

	public byte get_index() {
		return _index;
	}

	public byte get_itemSlot() {
		return _itemSlot;
	}

	public String get_itemName() {
		return _itemName;
	}

	public int get_itemType() {
		return _itemType;
	}

	public int get_level() {
		return _level;
	}

	public int get_defence() {
		return _defence;
	}

	public int get_magicDefence() {
		return _magicDefence;
	}

	public int get_ene() {
		return _ene;
	}

	public int get_reqLevel() {
		return _reqLevel;
	}

	public int get_zen() {
		return _zen;
	}

	public int get_magicDur() {
		return _magicDur;
	}

	public int get_magicPW() {
		return _magicPW;
	}

	public int get_maxDamage() {
		return _maxDamage;
	}

	public int get_minDamage() {
		return _minDamage;
	}

	public boolean get_option() {
		return _option;
	}

	public boolean get_serial() {
		return _serial;
	}

	public byte get_skillType() {
		return _skillType;
	}

	public int get_speed() {
		return _speed;
	}

	public int get_str() {
		return _str;
	}

	public byte get_xSize() {
		return _xSize;
	}

	public byte get_ySize() {
		return _ySize;
	}
}
