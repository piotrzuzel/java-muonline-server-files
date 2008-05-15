package net.sf.jmuserver.gs.serverPackage;
import java.io.IOException;

public class SSelectedCharacterEnterAnsfer extends ServerBasePacket {

//wsp,map,status
private short _x;
private short _y;
private short _m;
private short _s;
//statiscic
private int _str;
private int _agi;
private int _vit;
private int _ene;
@SuppressWarnings("unused")
private int _com;
//experance
private long _exp;
private long _expOnNewLvl;
//hp,mp,sp
private int _lp;
private int _hp;
private int _mhp;
private int _mp;
private int _mmp;
private int _sp;
private int _msp;
//zen
private long _zen;
//spare
private int _spar;
private int _mspare;

public SSelectedCharacterEnterAnsfer() 
{
_x=170;
_y=127;
_m=0;
_s=0;

_str=1;
_agi=1;
_vit=1;
_ene=1;
_com=1;

_hp=10;
_mhp=10;
_mp=10;
_mmp=10;
_sp=10;
_msp=10;

_exp=100;
_expOnNewLvl=200;
_lp=10;
_spar=0;
_mspare=0;
_zen=1;
}

public void setHpMpSp(int hp,int mhp,int mp,int mmp,int sp,int msp)
{
	_hp=hp;
	_mhp=mhp;
	_mmp=mmp;
	_mp=mp;
	_sp=sp;
	_msp=sp;
}

public void setZen(long zen)
{
	_zen=zen;
}

public void setExperance(long exp,long exon,int lp)
{
	_exp=exp;
	_expOnNewLvl=exon;
	_lp=lp;
}

public void setPosicion(int i,int j,int k,short s)
{
	_m=(short) i;
	_x=(short) j;
	_y=(short) k;
	_s=s;
}
public void setStatistic(int str,int agi ,int vit ,int ene,int com)
{
	_str=str;
	_agi=agi;
	_vit=vit;
	_ene=ene;
	_com=com;

}
public void setSpare(int spare,int mspare)
{
_spar=spare;
_mspare=mspare;
}

@Override
	public byte[] getContent() throws IOException {
	//map,pos,status
	mC3Header(0xf3, 0x03, 0x33);
	writeC(_x);
	writeC(_y);
	writeC(_m);
	writeC(_s);
	//exp
	writeL(_exp);
	writeL(_expOnNewLvl);
	writeI(_lp);
	//stat
	writeI(_str);
	writeI(_agi);
	writeI(_vit);
	writeI(_ene);
	//hp
	writeI(_hp);
	writeI(_mhp);
	writeI(_mp);
	writeI(_mmp);
	writeI(_sp);
	writeI(_msp);
	//unk
	writeC(0x0f);
	writeC(0x0f);
	//zen
	writeL(_zen);
	writeC(0x03); //color nicka -0-3 ok 4-6 red [44]
    
    writeC(0x02); 
    writeI(_spar);  //spare points max [46]
    writeI(_mspare); // spare point [48]
    
    writeC(0x0f); writeC(0xff) ; // unk
     
    writeC(0x00);
		return _bao.toByteArray();
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
