/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.openmu.gs.muObjects;

/**
 * Inherited from the MuInventory, it represents the player's inventory.<br>
 * Additionally, it provides means to handle the equipment slots transparent to
 * the outside.
 * 
 * @see MuInventory
 * @see MuTradeInventory
 * @see MuVaultInventory
 * @author Marcel
 */
public class MuCharacterInventory extends MuInventory {

	private final MuStoreableItem[] _equipment = { null, null, null, null,
			null, null, null, null, null, null, null, null };

	public MuCharacterInventory() {
		super();
		_slots = new boolean[MuInventory.InventoryWindowSize][MuInventory.InventoryWindowSize];
	}

	@Override
	public boolean storeItem(MuStoreableItem Item, int Position) {
		if (Position < MuInventory.OffsetInventoryWindow) {
			return storeEquipment(Item, Position);
		} else {
			return super.storeItem(Item, Position);
		}
	}

	@Override
	public boolean removeItem(int Position) {
		if (Position < MuInventory.OffsetInventoryWindow) {
			return removeEquipment(Position);
		} else {
			return super.removeItem(Position);
		}
	}

	@Override
	public boolean removeItem(MuStoreableItem Item) {
		if (Item.getPosition() < MuInventory.OffsetInventoryWindow) {
			return removeEquipment(Item.getPosition());
		} else {
			return super.removeItem(Item);
		}
	}

	/**
	 * Attempts to equip the item.<br>
	 * TODO: Validate equipping through checking item stats and position
	 * 
	 * @param Item
	 * @param Position
	 * @return
	 */
	private boolean storeEquipment(MuStoreableItem Item, int Position) {
		_equipment[Position + 1] = Item;
		return true;
	}

	private boolean removeEquipment(int Position) {
		_equipment[Position + 1] = null;
		return true;
	}
}
