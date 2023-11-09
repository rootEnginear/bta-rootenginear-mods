package rootenginear.leavesalwaysfalling;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptionsPageOptionBase;
import net.minecraft.client.option.GameSettings;

public class GuiOptionsPageLeaves extends GuiOptionsPageOptionBase {
	public GuiOptionsPageLeaves(GuiScreen parent, GameSettings settings){
		super(parent, settings);
		ILeavesSettings iLeavesSettings = (ILeavesSettings) settings;
		this.addOptionsCategory(LeavesAlwaysFalling.MOD_ID, iLeavesSettings.bta_rootenginear_mods$getLeaveFrequency());
	}
}
