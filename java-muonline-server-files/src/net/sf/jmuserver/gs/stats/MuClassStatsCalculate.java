package net.sf.jmuserver.gs.stats;

public class MuClassStatsCalculate {
public static int getMaxHp(int clas,int lvl,int vit)
{
	switch (clas)
	{
	case 0x00: 
	case 0x10: return 60+(lvl*1)+(vit*1);
	case 0x20: 
	case 0x30: return 110+(lvl*2)+(vit*3);
	case 0x40: 
	case 0x50: return 80+(lvl*1)+(vit*1);
	case 0x60: return 110+lvl+(vit*2);
	case 0x80: return 110+lvl+(vit*2);
	}
	return -1;
}
public static int getMaxMp(int clas,int lvl,int enr){
	switch (clas){
	case 0x00: 
	case 0x10: return 60+(lvl*2)+(enr*2);
	case 0x20: 
	case 0x30: return (int)(20+(lvl*0.5)+(enr*1));
	case 0x40: 
	case 0x50: return (int)(30+(lvl*1.5)+(enr*1.5));
	case 0x60: return 60+(lvl*1)+(enr*2);
	case 0x80: return 60+(lvl*1)+(enr*2);	
	}
	return -1;
}

public static int  getExpOnNewLvl(int lvl)
{
	return 500 * lvl;
}
public static int getSPOnNewLvl(int clas)
{
	switch (clas)
	{
	case 0x00:return 5;
	case 0x10:return 6;
	case 0x20:return 5;
	case 0x30:return 6;
	case 0x40:return 5;
	case 0x50:return 6;
	case 0x60:return 7;
	case 0x80:return 7;
	default :return 5;
	}
}
public static int getMaxSP(byte _clas, int _lvl, int _str, int _ene) {
	return _str+_ene +(_lvl * 10);
}
}
