package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;

import net.sf.jmuserver.gs.serverPackage.ServerBasePacket;

public class PacFile extends ServerBasePacket {

	private static final int _UNK = -1;
	private static final int _DEC = 1;
	private static final int _DES = 2;
	private static final int _STR = 3;
	private static final int _HEX = 4;
	private static final int _P_Head = 10;
	private static final int _P_Typ1 = 11;
	private static final int _P_Typ2 = 12;

	private LineNumberReader file;
	private String _nameFile;

	public PacFile(String fil2e) throws FileNotFoundException {
		super();
		_nameFile = fil2e;
		//

	}

	public void ReadFile() throws IOException {
		file = new LineNumberReader(new BufferedReader(new FileReader(new File(
				_nameFile))));
		for (;;) {
			String t = file.readLine();
			if (t == null)
				break;
			if (t.trim().length() == 0)
				continue;
			if (t.trim().startsWith("#"))
				continue;
			LineParser(t);

		}
	}

	private void LineParser(String line) {
		StringTokenizer t = new StringTokenizer(line,",");
		switch (getType(t.nextToken())) {
		case _DEC:
			addDecContent(t);
			break;
		case _HEX:
			addHexContetn(t);
			break;
		case _STR:
			addStrContent(t);
			break;
		case _P_Head:
			addHead(t);
			break;
		}
	};

	private void addHead(StringTokenizer t) {
		int pt = 99999999;
		int t1 = 99999999;
		int t2 = 99999999;
		String ProtocolT = t.nextToken();
		if (ProtocolT.compareToIgnoreCase("C1") == 0)
			pt = 0xc1;
		else if (ProtocolT.compareToIgnoreCase("C2") == 0)
			pt = 0xc2;
		else if (ProtocolT.compareToIgnoreCase("c3") == 0)
			pt = 0xc3;
		else if (ProtocolT.compareToIgnoreCase("c4") == 0)
			pt = 0xc4;

		if (t.hasMoreTokens()) {
			String Type = t.nextToken();
			t1=hex2int(Type);
		}
		else 
		{
			writeC(pt);
		} 
		if (t.hasMoreTokens()) {
			String type2 = t.nextToken();
			t2=hex2int(type2);
			
		}
	if (pt==99999999)return;
	else if(t1!=99999999)
	{
	writeC(pt);
	writeC(00); // size
	if(pt==0xc2&&pt==0xc4)writeC(00);
	writeC(t1);
	}
    if(t2!=99999999)
    {
    	writeC(t2);
    }
	}

	public int getType(String token) {
		int ret = _UNK;
		if (token.compareToIgnoreCase("dec") == 0)
			ret = _DEC;
		else if (token.compareToIgnoreCase("des") == 0)
			ret = _DES;
		else if (token.compareToIgnoreCase("str") == 0)
			ret = _STR;
		else if (token.compareToIgnoreCase("hex") == 0)
			ret = _HEX;
		else if (token.compareToIgnoreCase("head") == 0)
			ret = _P_Head;
		else if (token.compareToIgnoreCase("t1") == 0)
			ret = _P_Typ1;
		else if (token.compareToIgnoreCase("t2") == 0)
			ret = _P_Typ2;
		return ret;
	}

	public byte[] getContent() throws IOException {

		ReadFile();
		FixSize();
		System.out.println(printData(_bao.toByteArray(),
				_bao.toByteArray().length, ""));
		return _bao.toByteArray();
	}

	private void FixSize() {
		int s=_bao.toByteArray().length;
		
		System.out.println("size= "+s);
		
	}

	public String getType() {

		return null;
	}

	void addHexContetn(StringTokenizer line) {
		while (line.hasMoreTokens()) {
			writeC(hex2int(line.nextToken().trim()));
		}

	}

	public int hex2int(String n) {
		char[] t = n.trim().toLowerCase().toCharArray();
		int ret = char2intHex(t[1]) + char2intHex(t[0]) * 16;
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

	void addDecContent(StringTokenizer line) {
		while (line.hasMoreTokens()) {
			writeI(Integer.parseInt(line.nextToken().trim()));
		}
	}

	void addStrContent(StringTokenizer line) {
		while (line.hasMoreTokens()) {
			writeS(line.nextToken());
		}
	}

	@Override
	public boolean testMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
