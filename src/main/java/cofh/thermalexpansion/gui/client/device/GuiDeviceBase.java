package cofh.thermalexpansion.gui.client.device;

import cofh.core.gui.GuiCore;
import cofh.core.gui.element.*;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.device.TileDeviceBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class GuiDeviceBase extends GuiCore {

	protected TileDeviceBase baseTile;
	protected UUID playerName;

	protected String myTutorial = "";

	protected TabBase redstoneTab;
	protected TabBase configTab;
	protected TabBase securityTab;

	public GuiDeviceBase(Container container, TileEntity tile, EntityPlayer player, ResourceLocation texture) {

		super(container, texture);

		baseTile = (TileDeviceBase) tile;
		name = baseTile.getName();
		playerName = SecurityHelper.getID(player);

		if (baseTile.enableSecurity() && baseTile.isSecured()) {
			myTutorial += StringHelper.tutorialTabSecurity() + "\n\n";
		}
		myTutorial += StringHelper.tutorialTabRedstone() + "\n\n";
		myTutorial += StringHelper.tutorialTabConfiguration();
	}

	@Override
	public void initGui() {

		super.initGui();

		// Right Side
		redstoneTab = addTab(new TabRedstoneControl(this, baseTile));
		configTab = addTab(new TabConfiguration(this, baseTile));

		// Left Side
		securityTab = addTab(new TabSecurity(this, baseTile, playerName));
		securityTab.setVisible(baseTile.enableSecurity() && baseTile.isSecured());

		if (!myInfo.isEmpty()) {
			addTab(new TabInfo(this, myInfo));
		}
		addTab(new TabTutorial(this, myTutorial));
	}

	@Override
	public void updateScreen() {

		super.updateScreen();

		if (!baseTile.canAccess()) {
			this.mc.thePlayer.closeScreen();
		}
		redstoneTab.setVisible(baseTile.hasRedstoneControl());

		securityTab.setVisible(baseTile.enableSecurity() && baseTile.isSecured());
	}

}
