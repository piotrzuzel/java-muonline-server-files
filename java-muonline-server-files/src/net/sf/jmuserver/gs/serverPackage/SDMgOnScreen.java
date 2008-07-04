package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

public class SDMgOnScreen extends ServerBasePacket {
	private int _dmg;
	private int _id;
	private byte _f;
	public static final byte _DMG_MISS=0x00;
	public static final byte _DMG_NORM=0x02;
	public static final byte _DMG_REFL=0x00;
	public static final byte _DMG_EXC=0x00;
	public static final byte _DMB_CRI=0x01;
	public SDMgOnScreen(int id,int dmg,byte fdmg) {
	_dmg=dmg;
	_id=id;
	_f=fdmg;
	}

	public byte[] getContent() throws IOException {
	//  c1   08   d6   74   00   96   00   00 
	//  c1   08   d6   00   f0   00   96   00 00
	//  c1   08   d6   00   a0   00   96   00
	//  c1   08   d7   14   41   8c   56   70
	//  c1   08   d9   03   94   00   00   00
	//0xc1,0x08,0xd6,0xff,0xff,0xff,0xff,0xff
	//d7 poruszanie 
	//d6 ?
	//d5 ?
    //d1 ?
	//d2 ?
	
		mC1Header(0xd9, 0x08);
		writeI(_id);
		writeC(0x00);
		writeC(_dmg);
		writeC(_f);
		writeC(0);
		return _bao.toByteArray();
		
	}

	
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testMe() {
		
		return false;
	}

}
