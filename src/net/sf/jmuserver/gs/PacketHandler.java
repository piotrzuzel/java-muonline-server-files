package net.sf.jmuserver.gs;

import java.io.IOException;

import net.sf.jmuserver.gs.clientPackage.CA0Request;
import net.sf.jmuserver.gs.clientPackage.CAddFrendRequest;
import net.sf.jmuserver.gs.clientPackage.CAddLvlPointsRequest;
import net.sf.jmuserver.gs.clientPackage.CAttackOnId;
import net.sf.jmuserver.gs.clientPackage.CBuyItemRequest;
import net.sf.jmuserver.gs.clientPackage.CChangeDirectoryOrStatus;
import net.sf.jmuserver.gs.clientPackage.CCharacterListRequest;
import net.sf.jmuserver.gs.clientPackage.CClientSettingsSaveRequest;
import net.sf.jmuserver.gs.clientPackage.CDeleteChar;
import net.sf.jmuserver.gs.clientPackage.CEnterInGateRequest;
import net.sf.jmuserver.gs.clientPackage.CItemDropFromInwentoryRequest;
import net.sf.jmuserver.gs.clientPackage.CItemPickUpRequest;
import net.sf.jmuserver.gs.clientPackage.CItemUseRequest;
import net.sf.jmuserver.gs.clientPackage.CMoveCharacter;
import net.sf.jmuserver.gs.clientPackage.CMoveItemRequest;
import net.sf.jmuserver.gs.clientPackage.CNewCharacterRequest;
import net.sf.jmuserver.gs.clientPackage.CNpcRunRequest;
import net.sf.jmuserver.gs.clientPackage.CPasVeryfcation;
import net.sf.jmuserver.gs.clientPackage.CPublicMsg;
import net.sf.jmuserver.gs.clientPackage.CSelectCharacterOrExitRequest;
import net.sf.jmuserver.gs.clientPackage.CSelectedCharacterEnterRequest;

/**
 * This class ...
 * 
 * @version $Revision: 1.18 $ $Date: 2004/10/26 20:43:03 $
 */
public class PacketHandler {
	// private static Logger _log = Logger
	// .getLogger(PacketHandler.class.getName());
	private final MuClientSession _client;

	public PacketHandler(MuClientSession client) {
		_client = client;
	}

	public void handlePacket(byte[] data) throws IOException, Throwable {
		// int pos = 0;
		// System.out.println("lenght="+data.length);
		final int id = data[0] & 0xff;
		int id2 = 0;

		if (data.length > 1) {
			id2 = data[1] & 0xff;
		}
		System.out.println(printData(data, data.length, "[C->S]"));
		switch (id) {
		case 0xa0:
			new CA0Request(data, _client);
			break;
		case 0x00:
			new CPublicMsg(data, _client);
			break;
		case 0x18:
			new CChangeDirectoryOrStatus(data, _client);
			break;
		case 0x1C:
			new CEnterInGateRequest(data, _client);
			break;
		case 0x22:
			new CItemPickUpRequest(data, _client);
			break;
		case 0x23:
			new CItemDropFromInwentoryRequest(data, _client);
			break;
		case 0x24:
			new CMoveItemRequest(data, _client);
			break;
		case 0x26:
			new CItemUseRequest(data, _client);
			break;
		case 0x30:
			new CNpcRunRequest(data, _client);
			break;
		case 0x32:
			new CBuyItemRequest(data, _client);
			break;
		case 0xd7:
			new CMoveCharacter(data, _client);
			break;
		case 0xd9:
			new CAttackOnId(data, _client);
			break;
		case 0xc1:
			new CAddFrendRequest(data, _client);
			break;
		case 0xf1: {
			switch (id2) {
			case 0x01:
				new CPasVeryfcation(data, _client);
				break;
			case 0x02:
				new CSelectCharacterOrExitRequest(data, _client);
				break;
			default:

				break;
			}
		}
			break;
		case 0xf3: {
			switch (id2) {
			case 0x00: {
				new CCharacterListRequest(data, _client);
			}
				break;
			case 0x01: {
				new CNewCharacterRequest(data, _client);
			}
				break;
			case 0x02: {
				new CDeleteChar(data, _client);
			}
				break;
			case 0x03: {
				new CSelectedCharacterEnterRequest(data, _client);
			}
				break;
			case 0x06: {
				new CAddLvlPointsRequest(data, _client);
			}
				break;
			case 0x30: {
				new CClientSettingsSaveRequest(data, _client);
			}
				break;
			default: {
				System.out.println("Unknown Packet or no implament: "
						+ Integer.toHexString(id));

			}
				break;
			}
		}
			break;
		// 24 00 0c e3 00 00 80 00 00 14
		default:
			System.out.println("Unknown Packet or no implament: "
					+ Integer.toHexString(id));

		}

	}

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
