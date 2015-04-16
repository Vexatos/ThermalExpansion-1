package cofh.thermalexpansion.gui.client.plate;

import cofh.core.gui.GuiBaseAdv;
import cofh.core.gui.element.TabInfo;
import cofh.core.gui.element.TabSecurity;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementListBox;
import cofh.lib.gui.element.ElementSlider;
import cofh.lib.gui.element.ElementTextField;
import cofh.lib.gui.element.ElementTextFieldLimited;
import cofh.lib.gui.element.listbox.IListBoxElement;
import cofh.lib.gui.element.listbox.SliderVertical;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermalexpansion.block.plate.TilePlateTeleporter;
import cofh.thermalexpansion.core.TEProps;
import cofh.thermalexpansion.gui.container.ContainerTEBase;

import java.util.UUID;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;


public class GuiPlateTeleport extends GuiBaseAdv {

	static final String TEX_PATH = TEProps.PATH_GUI + "plate/Teleport.png";
	static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

	TilePlateTeleporter myTile;
	UUID playerName;

	ElementListBox frequencies;
	ElementSlider slider;
	ElementTextField freq;
	ElementTextField name;

	public GuiPlateTeleport(InventoryPlayer inventory, TileEntity theTile) {

		super(new ContainerTEBase(inventory, theTile, false, false), new ResourceLocation(TEX_PATH));
		myTile = (TilePlateTeleporter) theTile;
		super.name = myTile.getInventoryName();
		playerName = SecurityHelper.getID(inventory.player);
		drawInventory = false;

		// generateInfo("tab.thermalexpansion.plate.translocate", 2);
	}

	@Override
	public void initGui() {

		super.initGui();
		Keyboard.enableRepeatEvents(true);

		if (!myInfo.isEmpty()) {
			addTab(new TabInfo(this, myInfo));
		}
		if (myTile.enableSecurity() && myTile.isSecured()) {
			addTab(new TabSecurity(this, myTile, playerName));
		}

		addElement(new ElementEnergyStored(this, 8, 8, myTile.getEnergyStorage()));
		addElement(freq = new ElementTextFieldLimited(this, 102, 40, 26, 11, (short) 3).setFilter("0123456789", false)
			.setBackgroundColor(0, 0, 0).setText(String.valueOf(myTile.getFrequency())));
		addElement(name = new ElementTextField(this, 28, 56, 108, 11, (short) 15).setBackgroundColor(0, 0, 0));

		addElement(new ElementButton(this, 131, 32, 20, 20, 176, 0, 176, 20, 176, 40, TEX_PATH) {

			@Override
			public void onClick() {

				// TODO: prompt for name? alternate tab? popup?
				myTile.setFrequency(Integer.parseInt(freq.getText()));
			}
		});
		addElement(new ElementButton(this, 151, 32, 20, 20, 196, 0, 196, 20, 196, 40, TEX_PATH) {

			@Override
			public void onClick() {

				myTile.clearFrequency();
			}
		});

		addElement(new ElementButton(this, 139, 54, 16, 16, 176, 60, 176, 76, 176, 92, TEX_PATH) {

			@Override
			public void onClick() {

				@SuppressWarnings("unused")
				String name = null; // (String) frequencies.getSelectedElement().getValue();
				// TODO: name<->freq lookup
				myTile.setDestination(0);
			}
		});
		addElement(new ElementButton(this, 155, 54, 16, 16, 192, 60, 192, 76, 192, 92, TEX_PATH) {

			@Override
			public void onClick() {

				myTile.clearDestination();
			}
		});

		addElement(frequencies = new ElementListBox(this, 6, 73, 130, 87) {

			@Override
			protected void onElementClicked(IListBoxElement element) {

			}

			@Override
			protected void onScrollV(int newStartIndex) {

				slider.setValue(newStartIndex);
			}

		}.setBackgroundColor(0, 0).setSelectionColor(1));
		frequencies.setSelectedIndex(-1);
		addElement(slider = new SliderVertical(this, 140, 73, 14, 87, 0) {

			@Override
			public void onValueChanged(int value) {

				frequencies.setSelectedIndex(value);
			}

		}.setColor(0, 0));
	}

	@Override
	public void onGuiClosed() {

		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}

	@Override
	public void updateScreen() {

		super.updateScreen();

		if (!myTile.canAccess()) {
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {

	}

	@Override
	protected void updateElementInformation() {

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		super.drawGuiContainerForegroundLayer(x, y);
	}

}