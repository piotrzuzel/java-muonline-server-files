package net.sf.jmuserver.gs.serverPackage;

import java.io.IOException;

/**
 * @author Miki
 * @opis
 * paczka z lista id'ow do zapomnienia
 * @example 
 *  c1 16 14 01 00 01 00 <br>
 *  xx xx xx x1 x2 x2 x3
 *  <br>
 *  x1 - ilosc idow do zaponienia
 *  <br> x2-id do zapomnienia
 *  <br> x3 --bit 00 stopu
 */
public class SForgetId extends ServerBasePacket {

	private int _id;
	public SForgetId(int i) {
		_id=i;
	}

	@Override
	public byte[] getContent() throws IOException {
	mC1Header(0xc1, 0x14,0x06);
	writeC(0x01); // najprawdopodobndoiej ilosc idowdo zapomnienia 
	writeI(_id);
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
