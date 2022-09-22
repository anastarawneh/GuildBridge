package me.anastarawneh.guildbridge.gui;

import me.anastarawneh.guildbridge.GuildBridge;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(GuildBridge.CONFIG.getCategory("general")).getChildElements(), GuildBridge.MODID, "guildbridge", false, false, "GuildBridge Config", GuildBridge.CONFIG.getConfigFile().getPath());
    }
}
