/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

/**
 *
 * @author Miki
 */

import java.io.IOException;
import java.util.StringTokenizer;

import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;

public class HexLinePackage extends ServerBasePacket {

	private final String _lineToParse;

	public HexLinePackage(String _lineToParse) {
		this._lineToParse = _lineToParse;
	}

	private void LineParser(String line) throws WrongCharacter {
		final StringTokenizer t = new StringTokenizer(line, " ");
		addHexContetn(t);

	}

	@Override
	public byte[] getContent() throws IOException, WrongCharacter {

		FixSize();
		LineParser(_lineToParse);
		System.out.println(printData(_bao.toByteArray(),
				_bao.toByteArray().length, ""));
		return _bao.toByteArray();
	}

	private void FixSize() {
		final int s = _bao.toByteArray().length;

		System.out.println("size= " + s);

	}

	@Override
	public String getType() {

		return null;
	}

	void addHexContetn(StringTokenizer line) throws WrongCharacter {
		while (line.hasMoreTokens()) {
			writeC(hex2int(line.nextToken().trim()));
		}

	}

	public int hex2int(String n) throws WrongCharacter {
		final char[] t = n.trim().toLowerCase().toCharArray();
		final int a1 = char2intHex(t[1]);
		if (a1 == -1) {
			throw new WrongCharacter(t[1]);
		}
		final int a2 = char2intHex(t[0]);
		if (a2 == -1) {
			throw new WrongCharacter(t[0]);
		}
		final int ret = a1 + a2 * 16;
		return ret;
	}

	int char2intHex(char a) {
		switch (a) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		}
		return -1;
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
