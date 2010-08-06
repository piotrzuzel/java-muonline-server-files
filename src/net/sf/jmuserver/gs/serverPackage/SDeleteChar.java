package net.sf.jmuserver.gs.serverPackage;

/**
 * 
 * @author Marcel
 */
public class SDeleteChar extends ServerBasePacket {
	private final int _result;

	public SDeleteChar(String name, int result) {
		super();
		_result = result;
	}

	@Override
	public byte[] getContent() {
		mC1Header(0xf3, 0x02, 0x05);
		// 0x00 - you can not delete your character since the guild can not be
		// removed
		// 0x01 - delete ok
		// 0x02 - invalid personal id number
		// 0x03 - the character is item blocked
		writeC(_result);
		return getBytes();
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public boolean testMe() {
		return false;
	}
}
