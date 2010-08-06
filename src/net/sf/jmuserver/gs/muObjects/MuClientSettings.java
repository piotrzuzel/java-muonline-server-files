package net.sf.jmuserver.gs.muObjects;

public class MuClientSettings {
	private byte[] _skillKeys = { 00, 00, 00, 00, 00, 00, 00, 00, 00, 00 };;
	private byte _gameOptions;
	private byte _q_key;
	private byte _w_key;
	private byte _e_key;
	private byte _chatwindowOptions;

	public byte getChatwindowOptions() {
		return _chatwindowOptions;
	}

	public void setChatwindowOptions(byte options) {
		_chatwindowOptions = options;
	}

	public byte getE_key() {
		return _e_key;
	}

	public void setE_key(byte _e_key) {
		this._e_key = _e_key;
	}

	public byte getGameOptions() {
		return _gameOptions;
	}

	public void setGameOptions(byte options) {
		_gameOptions = options;
	}

	public byte getQ_key() {
		return _q_key;
	}

	public void setQ_key(byte _q_key) {
		this._q_key = _q_key;
	}

	public byte[] getSkillKeys() {
		return _skillKeys;
	}

	public void set_SkillKeys(byte[] keys) {
		_skillKeys = keys;
	}

	public byte getW_key() {
		return _w_key;
	}

	public void setW_key(byte _w_key) {
		this._w_key = _w_key;
	}

	public void LoadDefault() {
		_gameOptions = 0x09;
		_q_key = 0x00;
		_w_key = 0x04;
		_e_key = 0x08;
		_chatwindowOptions = 0x04;
	}

	public byte[] toByteArray() {
		final byte[] t = new byte[15];
		for (int i = 0; i < 10; i++) {
			t[i] = _skillKeys[i];
		}
		t[11] = _gameOptions;
		t[12] = _q_key;
		t[13] = _w_key;
		t[14] = _e_key;
		t[15] = _chatwindowOptions;
		return t;
	}

	public void fromByteArray(byte[] a, int t) {
		for (int i = 0; i < 10; i++) {
			_skillKeys[i] = a[i + t];
		}
		_gameOptions = a[11 + t];
		_q_key = a[12 + t];
		_w_key = a[13 + t];
		_e_key = a[14 + t];
		_chatwindowOptions = a[15 + t];
	}

}
