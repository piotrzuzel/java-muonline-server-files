package net.sf.jmuserver.gs.muObjects;

import net.sf.jmuserver.gs.templates.MuItemHex;

public class MuItemStore  {

int _windowId;
int _pos;
MuItemHex _itemHex;



public MuItemStore(int windowId,int pos,MuItemHex hex) {
_windowId=windowId;
_pos=pos;
_itemHex=hex;
}

public void storeItem(int wid,int pos,MuItemHex i)
{
_windowId=wid;
_pos=pos;
_itemHex=i;
}

public byte[] toByteArray()
{
	byte ret[]={(byte) _pos,0x00,0x00,0x00,0x00,0x00};
	byte reth[]=_itemHex.toByteArray();
	ret[1]=reth[0];
	ret[2]=reth[1];
	ret[3]=reth[2];
	ret[4]=reth[3];
	ret[5]=reth[4];
	return ret;
	
}
public void moveTo(int w,int pos)
{
	_windowId=w;
	_pos=pos;	
}

public MuItemHex toMuItemHex()
{
	return _itemHex;
}
}
