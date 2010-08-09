package net.sf.jmuserver.gs.clientPackage;

import net.sf.jmuserver.gs.MuClientSession;
import net.sf.jmuserver.gs.muObjects.MuInventory;
import net.sf.jmuserver.gs.muObjects.MuStoreableItem;
import net.sf.jmuserver.gs.serverPackage.SMoveItemResult;

/**
 * 
 * @author Marcel, Miki
 */
public class CMoveItemRequest extends ClientBasePacket {
	// 23 ad 7f 0c
	/**
	 * @param decrypt
	 * @param _client
	 * @example 24 00 01 c0 00 16 00 00 00 0c
	 */
	public CMoveItemRequest(byte[] decrypt, MuClientSession _client) {
		super(decrypt);
		// Invalid values
		int _slotFrom = -1;
		int _slotTo = -1;
		int _windowFrom = -1;
		int _windowTo = -1;
		boolean _result = true;
		MuInventory _fromInventory = null;
		MuInventory _toInventory = null;
		byte[] _itemHex = { 0, 0, 0, 0, 0 };
		// 24 00 01 c0 00 16 00 00 00 0c
		// 24 00 0c e3 00 00 80 00 00 14
		_windowFrom = decrypt[1];
		_windowTo = decrypt[8];
		_slotFrom = decrypt[2];
		_slotTo = decrypt[9];

		System.out.println("Move Item Request : from wid[" + _windowFrom
				+ "] slot[" + _slotFrom + "] to " + "wid[" + _windowTo
				+ "] slot[" + _slotTo + "]");

		switch (_windowFrom) {
		case MuInventory.InventoryWindow:
			_fromInventory = _client.getActiveChar().getInventory();
			break;
		case MuInventory.TradeWindow:
			// _fromInventory = _client.getActiveChar().getTradeInventory();
			_result = false;
			break;
		case MuInventory.VaultWindow:
			// _fromInventory = _client.getWarehouse();
			_result = false;
			break;
		default:
			_result = false;
			break;
		}
		if (_result) {
			System.out.println("source inventory found");
			switch (_windowTo) {
			case MuInventory.InventoryWindow:
				_toInventory = _client.getActiveChar().getInventory();
				break;
			case MuInventory.TradeWindow:
				// _toInventory = _client.getActiveChar().getTradeInventory();
				_result = false;
				break;
			case MuInventory.VaultWindow:
				// _toInventory = _client.getWarehouse();
				_result = false;
				break;
			default:
				_result = false;
				break;
			}
			if (_result) {
				final MuStoreableItem _item = _fromInventory.getItem(_slotFrom);
				_result = _item != null;
				if (_result) {
					_result = _fromInventory.removeItem(_item);
					if (_result) {
						_result = _toInventory.storeItem(_item, _slotTo);
						if (!_result) {
							_toInventory.storeItem(_item, _slotFrom);
						}
						_itemHex = _item.getItemHex().toByteArray();
					}
				}
			}
		}
		System.out.println("result: " + _result);
		if (!_result) {
			_client.getActiveChar().sendPacket(
					new SMoveItemResult(_windowFrom, _slotFrom, _itemHex));
		} else {
			_client.getActiveChar().sendPacket(
					new SMoveItemResult(_windowTo, _slotTo, _itemHex));
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
