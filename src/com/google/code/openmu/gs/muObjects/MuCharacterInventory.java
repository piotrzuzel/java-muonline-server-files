/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
