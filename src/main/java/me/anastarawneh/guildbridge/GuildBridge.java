package me.anastarawneh.guildbridge;

import me.anastarawneh.guildbridge.event.ChatMessageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = GuildBridge.MODID, version = GuildBridge.VERSION)
public class GuildBridge {
    public static final String MODID = "guildbridge";
    public static final String VERSION = "1.0.0";
    public static Configuration CONFIG;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        File configFile = new File(Loader.instance().getConfigDir(), "guildbridge.cfg");
        Configuration config = new Configuration(configFile);
        config.load();
        config.getString("bridgeUsername", "general", "BRIDGE_USERNAME", "The Bridge account username.");
        if (config.hasChanged()) config.save();
        CONFIG = config;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChatMessageEvent());
    }
}
