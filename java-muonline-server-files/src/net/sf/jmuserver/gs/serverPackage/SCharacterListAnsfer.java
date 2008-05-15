package net.sf.jmuserver.gs.serverPackage;
import java.io.IOException;

import net.sf.jmuserver.gs.muObjects.MuCharacterBase;
import net.sf.jmuserver.gs.muObjects.MuCharacterList;

/**
 * 
 */

/**
 * @author MikiOne
 * 
 */
public class SCharacterListAnsfer extends ServerBasePacket {

	/**
	 * 
	 */
	MuCharacterList _list;

	public SCharacterListAnsfer(MuCharacterList list) {
		_list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ServerBasePacket#getContent()
	 */

	// private byte[] mSubCharacter(int s) {
	// byte t[];
	// return t;
	// }

	public byte[] getContent() throws IOException {
		int ilec = _list.getCharsCount();
		System.out.println("ilosc postaci od ch_list = "+ ilec);
		CharHea head = new CharHea(ilec);
		_bao.write(head.getContent());
		if (ilec != 0)
			for (int s = 0; s < ilec; s++) {
				CharSub t = new CharSub(s+1, _list.getChar(s));
				_bao.write(t.getContent());
			}
		;

		return _bao.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ServerBasePacket#getType()
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	class CharSub extends ServerBasePacket {

		private MuCharacterBase _c;

		private int _nr;

		public CharSub(int nr, MuCharacterBase base) {
			_c = base;
			_nr = nr;
		}

		public byte[] getContent() throws IOException {
            if(_c==null)System.out.println("error");
			_bao.write((byte) _nr);
			writeS(_c.getName());
			for (int a = 0; a <= 10 - _c.getName().length(); a++)
				_bao.write(0x00);
			_bao.write(0x00);
			
			writeI(_c.getLvl());
			_bao.write(0x00);
			_bao.write((byte) _c.getClas());
			
			byte[] item = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
					(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0xf8, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			_bao.write(item);
			
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

	class CharHea extends ServerBasePacket {
		private int ile_c;

		public CharHea(int ile) {
			ile_c = ile;

		}

		@Override
		public byte[] getContent() throws IOException {
			byte t[] = { (byte) 0xc1, (byte) 0xff, (byte) 0xf3, (byte) 0x00,
					(byte) 0x04, (byte) 0x00, (byte) 0xff };
			if (ile_c == 0) {
				t[1] = 7;
				t[6] = 0x00;
				byte[] t1 = new byte[8];
				System.arraycopy(t, 0, t1, 0, 6);
				t1[7] = 0x00;

				return t1;
			}
			t[6] = (byte) ile_c; // how many haracters we have
			t[1] = (byte) (7 + ((ile_c ) * 28)); // wielkosc przyszlej paczki
			return t;
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

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
