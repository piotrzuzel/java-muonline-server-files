/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.openmu.gs.serverPackets;

import java.io.IOException;

/**
 * 
 * @author Miki i Linka
 */
public class SServerMsg extends ServerBasePacket {

	public static final byte YellowMsg = 0x00;
	public static final byte BlueMsg = 0x01;
	public static final byte GuildNotice = 0x02;
	public static final byte ScrollMsg = 0x0A;
	// msg base infos
	private final String _msg;
	private final byte _msgType;
	// scrool msg infos
	private byte _cout;// how much times to repeat this message
	private byte _opacity; // ?
	private byte _scrollSpeed;
	// color
	private byte _r;
	private byte _b;
	private byte _g;

	/**
	 * constructor for non scrool msg
	 * 
	 * @param msg
	 *            msg to send
	 * @param msgType
	 *            type of Msg
	 */
	public SServerMsg(String msg, byte msgType) {
		_msg = msg;
		_msgType = msgType;
	}

	/**
	 * send scroll Msg to client
	 * 
	 * @param msg
	 *            Msg to send
	 * @param r
	 *            REd color RBG
	 * @param b
	 *            Blue color RBG
	 * @param g
	 *            Green color RBG
	 * @param cout
	 *            count of repeat msg
	 * @param opacity
	 *            how long on screan
	 * @param scroolspeed
	 *            how fast scroll
	 */
	public SServerMsg(String msg, byte r, byte b, byte g, byte cout,
			byte opacity, byte scroolspeed) {
		_msg = msg;
		_msgType = ScrollMsg;
		_r = r;
		_b = b;
		_g = g;
		_cout = cout;
		_opacity = opacity;
		_scrollSpeed = scroolspeed;
	}

	@Override
	public byte[] getContent() throws IOException, Throwable {
		final int sizePAcket = _msg.length() + 13 + 1; // lenght of package
		mC1Header(0x0D, _msgType, sizePAcket);
		if (_msgType == ScrollMsg) {
			writeC(_cout);
			writeC(0); // unk
			writeC(0); // unk
			writeC(0); // unk
			writeC(_r); // red RBG
			writeC(_b);// RBG
			writeC(_g);// RBG
			writeC(_opacity);
			writeC(_scrollSpeed);
			// 9bytes
		} else {
			writeB(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 }); // 9 bytes of
																// scrool id
																// fill 0x00
		}
		writeS(_msg); // msg
		writeC(0x00); // for care or end

		return getBytes();
	}

	@Override
	public String getType() {
		return "Server Msg";
	}

	@Override
	public boolean testMe() {
		return true;
	}
}
