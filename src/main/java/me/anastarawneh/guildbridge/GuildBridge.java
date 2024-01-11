package me.anastarawneh.guildbridge;

import me.anastarawneh.guildbridge.event.ChatMessageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = GuildBridge.MODID, version = GuildBridge.VERSION, guiFactory = "me.anastarawneh.guildbridge.gui.ConfigGuiFactory")
public class GuildBridge {
    public static final String MODID = "guildbridge";
    public static final String VERSION = "1.2.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static Configuration CONFIG;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        config.get("general", "bridgeUsernames", new String[] {"BRIDGE_USERNAME"}, "Bridge account usernames").setLanguageKey("guildbridge.configgui.bridgeUsernames");
        config.get("general", "bridgeFormat", "$username: $message", "Bridge message format").setLanguageKey("guildbridge.configgui.bridgeFormat");
        if (config.hasChanged()) {
            config.save();
        }
        CONFIG = config;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChatMessageEvent());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (!event.modID.equals(MODID)) return;
        CONFIG.get("general", "bridgeUsernames", new String[] {"BRIDGE_USERNAME"});
        CONFIG.get("general", "bridgeFormat", "$username: $message");
        if (CONFIG.hasChanged()) CONFIG.save();
    }
}
