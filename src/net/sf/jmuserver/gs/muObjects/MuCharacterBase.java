package net.sf.jmuserver.gs.muObjects;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.D4E5C350-9473-AD41-4A4A-609553286947]
// </editor-fold> 
public class MuCharacterBase {

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.350E21F5-6D43-07B2-7555-C2471967CE68]
	// </editor-fold>
	protected String _name;
	protected MuCharacterWear _wear = new MuCharacterWear();

	public MuCharacterWear getWear() {
		return _wear;
	}

	/**
	 * return true when is guild master
	 * 
	 * @return
	 * @ToDo fix to get that datafrom DB
	 */
	public boolean isInGuild() {
		return false;
	}

	public void setWear(MuCharacterWear _wear) {
		this._wear = _wear;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.61F82967-11F9-DB82-1CE7-DBC5260B3247]
	// </editor-fold>
	protected int _lvl;

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.5F3ABA74-9F32-4DF4-2A53-D860292FF8D1]
	// </editor-fold>
	protected int _class;

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.04976F84-6D1F-3888-574A-5ED6400E12EF]
	// </editor-fold>
	protected boolean _new_ch;

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.543C87D2-A2FB-0B32-2F94-7E3FB848B4CF]
	// </editor-fold>
	protected int _pos = 0;
	private MuCharacterWear _MyLook = new MuCharacterWear();

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.B55CE857-015C-653F-3061-5B882137C800]
	// </editor-fold>
	public MuCharacterBase() {

	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.E723E444-B5E1-5E80-CD73-312B62017098]
	// </editor-fold>
	public MuCharacterBase(String name, int lvl, int i, int pos,
			MuCharacterWear look) {
		_name = name;
		_lvl = lvl;
		_class = i;
		_pos = pos;
		_MyLook = look;

	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.DF51389B-A8DC-0AE5-783A-CE741B923BA2]
	// </editor-fold>
	public int getClas() {
		return _class;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.50928AFC-6CAD-FE7B-E32B-0310C493E613]
	// </editor-fold>
	protected void setClass(byte _class) {
		this._class = _class;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.38BE5F47-F2B5-A18D-9274-242D6A11E6F8]
	// </editor-fold>
	public int getLvl() {
		return _lvl;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.A1A95401-EEDC-08DC-5F1F-609D80B48377]
	// </editor-fold>
	protected void setLvl(int _lvl) {
		this._lvl = _lvl;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.E2DC1455-14B7-1CA9-A663-2E5B051E2C0B]
	// </editor-fold>
	public String getName() {
		return _name;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.F1FFD3D2-5FB6-62BF-143A-785DE9AB3B51]
	// </editor-fold>
	protected void setName(String _name) {
		this._name = _name;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.D914BC74-AADA-A0F8-B180-0FB62D009DCF]
	// </editor-fold>
	protected boolean is_new_ch() {
		return _new_ch;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.244EBFCC-B7B4-0356-46C9-036AD948A076]
	// </editor-fold>
	protected void set_new_ch(boolean _new_ch) {
		this._new_ch = _new_ch;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.E891B446-A997-56BB-6115-81C84C303EA0]
	// </editor-fold>
	protected int calculateHP(int lvl, int vit) {
		int rlive = 0;
		switch (getClas()) {
		case 0x00:
		case 0x10:
			rlive = 60 + (lvl * 1) + (vit * 1);
			break;
		case 0x20:
		case 0x30:
			rlive = 110 + (lvl * 2) + (vit * 3);
			break;
		case 0x40:
		case 0x50:
			rlive = 80 + (lvl * 1) + (vit * 1);
		case 0x60:
			rlive = 110 + lvl + (vit * 2);
			break;
		case (byte) 0x80:
			rlive = 110 + lvl + (vit * 2);
			break;
		default:
			rlive = 0;
			break;
		}
		return rlive;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.EB7B38F0-F7D4-399C-7DDD-A747B87A2DFE]
	// </editor-fold>
	protected int calculateMP(int lvl, int enr) {
		int rmp = 0;
		switch (getClas()) {
		case 0x00:
		case 0x10:
			rmp = 60 + (lvl * 2) + (enr * 2);
			break;
		case 0x20:
		case 0x30:
			rmp = (int) (20 + (lvl * 0.5) + (enr * 1));
			break;
		case 0x40:
		case 0x50:
			rmp = (int) (30 + (lvl * 1.5) + (enr * 1.5));
			break;
		case 0x60:
			rmp = 60 + (lvl * 1) + (enr * 2);
			break;
		case (byte) 0x80:
			rmp = 60 + (lvl * 1) + (enr * 2);
			break;
		}
		;
		return rmp;
	}

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.3F053649-E58B-D08F-6F62-65918B3AC8C2]
	// </editor-fold>
	protected int getBaseLP() {
		int a = 0;
		switch (getClas()) {
		case 0x00:
			a = 5;
			break;
		case 0x10:
			a = 6;
			break;
		case 0x20:
			a = 5;
			break;
		case 0x30:
			a = 6;
			break;
		case 0x40:
			a = 5;
			break;
		case 0x50:
			a = 6;
			break;
		case 0x60:
			a = 5;
			break;
		case (byte) 0x80:
			a = 7;
			break;

		}
		;
		return a;
	};

	// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
	// #[regen=yes,id=DCE.67E83F3F-F3ED-3204-1F4D-26B3B07E9F93]
	// </editor-fold>
	public int getPos() {

		return _pos;
	}

}
