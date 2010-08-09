package net.sf.jmuserver.gs.serverPackage;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * This class ...
 * 
 * @version $Revision: 1.3 $ $Date: 2004/07/04 11:14:53 $
 * @param <T>
 */
public abstract class ServerBasePacket<T> implements ServerPacketModel , MessageEncoder<T> {

	
	public abstract IoBuffer getContent(int SesionID);
	public void encode(IoSession session, Object what, ProtocolEncoderOutput out)
	throws Exception {
		out.write(getContent((Integer) (session.getAttribute("SessionID"))));
		

}
	protected ServerBasePacket() {
	}
	
	/**
	 * writes fixed nick full by 0x00 to 10 bytes
	 * 
	 * @param nick
	 */
	protected void writeNick(IoBuffer out, String nick) {
		final int nul = 0;
		try {

			if (nick != null) {
				final int l = nick.length(); // lenght of nnick
				out.put(nick.getBytes("ISO-8859-1"));
				for (int i = 0; i < 10 - l; i++) {
					out.put((byte)nul);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}


	
	/**
	 * make c1 header template with 1'st class protocol
	 * 
	 * @param typ
	 * @param s
	 */
	public void mC1Header(IoBuffer out,int typ, int s) {
		out.put((byte) 0xc1);
		out.put((byte) s);
		out.put((byte) typ);
	}

	/**
	 * make c1 header with 2 clases of protool
	 * 
	 * @param typ
	 * @param typ2
	 * @param s
	 */
	public void mC1Header(IoBuffer out,int typ, int typ2, int s) {
		out.put((byte) 0xc1);
		out.put((byte) s);
		out.put((byte) typ);
		out.put((byte) typ2);
		// System.out.println("mc1headrec done2");

	}

	/**
	 * make c3 template header with 2 types
	 * 
	 * @param typ
	 * @param typ2
	 * @param s
	 */
	public void mC3Header(IoBuffer out,int typ, int typ2, int s) {
		out.put((byte) 0xc3);
		out.put((byte) s);
		out.put((byte) typ);
		out.put((byte) typ2);
		// System.out.println("mc1headrec done2");
	}

	public void mC3Header(IoBuffer out,int typ, int s) {
		out.put((byte) 0xc3);
		out.put((byte) s);
		out.put((byte) typ);

		// System.out.println("mc1headrec done2");
	}

	/**
	 * make c2 header with 2 types
	 * 
	 * @param typ
	 * @param typ2
	 * @param s
	 */
	public void mC2Header(IoBuffer out,int typ, int typ2, int s) {
		out.put((byte) 0xc2);
		out.putShort((short) s);
			out.put((byte) typ);
			out.put((byte) typ2);
	}

	/**
	 * Make C2 header with one type
	 * 
	 * @param typ
	 * @param s
	 */
	public void mC2Header(IoBuffer out,int typ, int s) {
		out.put((byte) 0xc2);
		out.putShort((short) s);
		out.put((byte) typ );

	}

	/**
	 * make c4 header with 2 typs
	 * 
	 * @param typ
	 * @param typ2
	 * @param s
	 */
	public void mC4Header(IoBuffer out,int typ, int typ2, int s) {
		out.put((byte)0xc4);
		out.putShort((short) s);
		out.put((byte)typ );
		out.put((byte)typ2 );
	}

	/**
	 * make c4 header with 1 typ
	 * 
	 * @param typ
	 * @param s
	 */
	public void mC4Header(IoBuffer out,int typ, int s) {
		out.put((byte)0xc4);
		out.putShort((short) s );
		out.put((byte)typ);

	}

	/**
	 * compare two bitarrays
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	protected boolean CompareBits(byte[] a, byte[] b) {

		if (a.length != b.length) {
			return false;
		} else {
			for (int i = 0; i < a.length; i++) {
				if (a[i] != b[i]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * printe data to String as Hex look edytor
	 * 
	 * @param data
	 * @param len
	 * @param string
	 * @return
	 */
	public String printData(byte[] data, int len, String string) {
		final StringBuffer result = new StringBuffer();

		int counter = 0;

		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(string + " ");
				result.append(fillHex(i, 4) + ": ");
			}

			result.append(fillHex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");

				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					final int t1 = data[charpoint++];
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

		final int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}

			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				final int t1 = data[charpoint++];
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

	/**
	 * fill hex helped function 4 print hex
	 * 
	 * @param data
	 * @param digits
	 * @return
	 */
	private String fillHex(int data, int digits) {
		String number = Integer.toHexString(data);

		for (int i = number.length(); i < digits; i++) {
			number = "0" + number;
		}

		return number;
	}
}
