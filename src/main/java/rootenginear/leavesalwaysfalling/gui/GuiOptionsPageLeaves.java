package rootenginear.leavesalwaysfalling.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptionsPageOptionBase;
import net.minecraft.client.option.GameSettings;
import rootenginear.leavesalwaysfalling.interfaces.ILeavesSettings;

public class GuiOptionsPageLeaves extends GuiOptionsPageOptionBase {
	public GuiOptionsPageLeaves(GuiScreen parent, GameSettings settings){
		super(parent, settings);
		ILeavesSettings iLeavesSettings = (ILeavesSettings) settings;
		this.addOptionsCategory("options.category.leavesalwaysfalling", iLeavesSettings.bta_rootenginear_mods$getFrequency());
	}
}
