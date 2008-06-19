package net.sf.jmuserver.packetBase.crypt;

public class crypDecryptC3C4Test {

	public crypDecryptC3C4Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		crypDecryptC3C4Test  pr= new crypDecryptC3C4Test(); 
		decC3test test = new  decC3test();
		byte [] crypt={(byte) 0xc3, 0x18, (byte) 0x87, 0x19, 0x2c, (byte) 0xf6, 0x63, (byte) 0xc5, 
				0x32, (byte) 0xa8, 0x4c, 0x0c, 0x39, 0x72, 0x0f, 0x54, 
				(byte) 0xe6, 0x07, 0x66, 0x62, (byte) 0xb7, 0x70, 0x3d, 0x03};
		byte [] dec = new byte [(crypt[1]-2)*8/11];
		test.DecryptC3asServer(dec, crypt, crypt[1]);
		System.out.println(pr.printData(dec, dec.length, ""));
		//=c3 10 e5 0e 87 99 73 64 14 37 9f 60 d6 02 00 00

	}

	public String printData(byte[] data, int len, String string) {
		StringBuffer result = new StringBuffer();

		int counter = 0;

		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(string+ " ");
				result.append(fillHex(i, 4) + ": ");
			}

			result.append(fillHex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");

				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					int t1 = data[charpoint++];
					if (t1 > 0x1f && t1 < 0x80) {
						result.append((char) t1);
					} else {
						result.append('.');
					}
				}

				result.append("\n");
				counter = 0;
			}
		}

		int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}

			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				int t1 = data[charpoint++];
				if (t1 > 0x1f && t1 < 0x80) {
					result.append((char) t1);
				} else {
					result.append('.');
				}
			}

			result.append("\n");
		}

		return result.toString();
	}

	private String fillHex(int data, int digits) {
		String number = Integer.toHexString(data);

		for (int i = number.length(); i < digits; i++) {
			number = "0" + number;
		}

		return number;
	}
}


