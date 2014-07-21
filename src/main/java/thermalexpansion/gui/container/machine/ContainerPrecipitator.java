package thermalexpansion.gui.container.machine;

import cofh.gui.slot.SlotCustomInventory;
import cofh.gui.slot.SlotEnergy;
import cofh.gui.slot.SlotRemoveOnly;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

import thermalexpansion.block.machine.TilePrecipitator;
import thermalexpansion.gui.container.ContainerTEBase;

public class ContainerPrecipitator extends ContainerTEBase {

	TilePrecipitator myTile;

	public ContainerPrecipitator(InventoryPlayer inventory, TileEntity tile) {

		super(inventory, tile);

		myTile = (TilePrecipitator) tile;
		addSlotToContainer(new SlotRemoveOnly(myTile, 0, 80, 49));
		addSlotToContainer(new SlotEnergy(myTile, myTile.getChargeSlot(), 8, 53));

		/* Custom Inventory */
		addSlotToContainer(new SlotCustomInventory(myTile, 0, null, 0, 50, 19, false));
		addSlotToContainer(new SlotCustomInventory(myTile, 0, null, 1, 80, 19, false));
		addSlotToContainer(new SlotCustomInventory(myTile, 0, null, 2, 110, 19, false));

	}

}
