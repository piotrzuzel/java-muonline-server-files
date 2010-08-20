package com.google.code.openmu.gs;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

import com.google.code.openmu.gs.serverPackets.ServerBasePacket;


/**
 * This class Represent connection to client
 * 
 * 
 */
public class MuConnection {

	private final byte[] _cryptkey;
	JTextArea _packArea;
	private final boolean f = false;
	private final Socket _csocket;
	private final InputStream _in;
	private final OutputStream _out;
	private final byte[] key = { (byte) 0xe7, (byte) 0x6D, (byte) 0x3a,
			(byte) 0x89, (byte) 0xbc, (byte) 0xB2, (byte) 0x9f, (byte) 0x73,
			(byte) 0x23, (byte) 0xa8, (byte) 0xfe, (byte) 0xb6, (byte) 0x49,
			(byte) 0x5d, (byte) 0x39, (byte) 0x5d, (byte) 0x8a, (byte) 0xcb,
			(byte) 0x63, (byte) 0x8d, (byte) 0xea, (byte) 0x7d, (byte) 0x2b,
			(byte) 0x5f, (byte) 0xc3, (byte) 0xb1, (byte) 0xe9, (byte) 0x83,
			(byte) 0x29, (byte) 0x51, (byte) 0xe8, (byte) 0x56 };

	/**
	 * Construtor
	 * 
	 * @param client
	 *            Socket to client
	 * @param cryptKey
	 *            Kay to uncrypt
	 * @throws java.io.IOException
	 */
	public MuConnection(Socket client, byte[] cryptKey) throws IOException {
		_csocket = client;
		_in = client.getInputStream();
		_out = new BufferedOutputStream(client.getOutputStream());
		_cryptkey = cryptKey; // this is defined here but it is not used
	}

	/**
	 * get data packets and parse it to single packages if wos sended more then
	 * 1
	 * 
	 * @return Decrypted xor algh package
	 * @throws java.io.IOException
	 */
	public byte[] getPacket() throws IOException {
		int lengthHi = 0;
		int typ = 0;
		int lengthLo = 0;
		int length = 0;
		int pos = 0;

		typ = _in.read();
		// System.out.println(typ);
		switch (typ) {
		case 0xc1:
		case 0xc3: {

			length = _in.read();
			pos = 2;
			// System.out.println("c1c3:S"+length);
		}
			break;
		case 0xc2:
		case 0xc4: {

			lengthLo = _in.read();
			lengthHi = _in.read();
			pos = 3;
			length = lengthHi * 256 + lengthLo;
			// System.out.println("c2c4:S"+length);
		}
			break;
		default: {
			System.out.println("Unknown Header type");
		}
		}

		if (length < 0) {
			System.out.println("client terminated connection");
			throw new IOException("EOF");
		}

		final byte[] incoming = new byte[length - pos];
		int receivedBytes = pos;
		int newBytes = 0;
		while (newBytes != -1 && receivedBytes < length) {
			newBytes = _in.read(incoming, 0, length - pos);
			receivedBytes = receivedBytes + newBytes;

		}

		if (incoming.length <= 0) {
			return null;
		}
		byte[] decr = new byte[incoming.length];
		decr = dec(incoming, pos);
		if (f) {
			_packArea.append("\n" + printData(decr, decr.length, "[C->S]"));
			// System.out.println("\n" + printData(decr, decr.length
			// ,"[C->S]"));
		}
		return decr;
	}

	/**
	 * Decrypt whole array
	 * 
	 * @param buf
	 *            to decrypt
	 * @param pos
	 *            offset in arary
	 * @return uncrypted arry baits
	 */
	public byte[] dec(byte[] buf, int pos) {
		final byte[] a = new byte[buf.length];

		a[0] = buf[0];
		byte t = 0;

		int b = pos + 1;
		for (int i = 0; i < (buf.length - 1); i++, b++) {

			if (b >= 32) {
				b = 0;
			}
			t = dec1(buf[i], buf[i + 1], b);
			a[i + 1] = t;

		}

		return a;

	}

	/**
	 * Decrypt xor algorytm one bajt
	 * 
	 * @param a
	 *            actual bait to decrypt
	 * @param n
	 *            nextbait
	 * @param pos
	 *            position in array
	 * @return decrypted bajt
	 */
	public byte dec1(byte a, byte n, int pos) {
		final byte t = (byte) (a ^ key[pos]);
		final byte t2 = (byte) (n ^ t);
		return t2;
	}

	/**
	 * This method will be called indirectly by several threads, to notify one
	 * client about all parallel events in the world. it has to be either
	 * synchronized like this, or it might be changed to stack packets in a
	 * outbound queue. advantage would be that the calling thread is independent
	 * of the amount of events that the target gets. if one target receives
	 * hundreds of events in parallel, all event sources will have to wait until
	 * the packets are send... for now, we use the direct communication
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void sendPacket(byte[] data) throws IOException {
		synchronized (this) {
			// this is time consuming.. only enable for debugging

			{
				System.out.println("\n"
						+ printData(data, data.length, "[S->C]"));
			}

			_out.write(data, 0, data.length);
			_out.flush();
		}
	}

	/**
	 * Send packet bp
	 * 
	 * @see ServerBasePacket
	 * @param bp
	 * @throws java.io.IOException
	 * @throws java.lang.Throwable
	 */
	public void sendPacket(ServerBasePacket bp) throws IOException, Throwable {
		final byte[] data = bp.getContent();
		sendPacket(data);
	}

	public void activateCryptKey() {
		// _inCrypt.setKey(_cryptkey);
		// _outCrypt.setKey(_cryptkey);
	}

	/**
	 * this only gives the correct result if the cryptkey is not yet activated
	 */
	public byte[] getCryptKey() {
		return _cryptkey;
	}

	/**
	 * close socket
	 */
	public void close() throws IOException {
		_csocket.close();
	}

	/**
	 * print data
	 * 
	 * @param data
	 *            data to print
	 * @param len
	 *            lenght of data
	 * @param string
	 *            string
	 * @return string of printed hexdata
	 */
	private String printData(byte[] data, int len, String string) {
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

	private String fillHex(int data, int digits) {
		String number = Integer.toHexString(data);

		for (int i = number.length(); i < digits; i++) {
			number = "0" + number;
		}

		return number;
	}
}
