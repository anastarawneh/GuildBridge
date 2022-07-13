package me.anastarawneh.guildbridge.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.anastarawneh.guildbridge.GuildBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMessageEvent {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onChatMessage(ClientChatReceivedEvent event) {
        Configuration config = GuildBridge.CONFIG;
        String text = event.message.getUnformattedText().replaceAll("\u00A7.", "");
        String regex = "^Guild > (\\[(?:VI|MV)P\\+{0,2}] )?([^ ]*)( \\[[\\w\\d]*])?: (.*): (.*)";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        if (matcher.matches() && matcher.group(2).equals(config.getCategory("general").get("bridgeUsername").getString())) {
            String username = matcher.group(4);
            String message = matcher.group(5);

            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    ChatFormatting.BLUE + "Discord > " + ChatFormatting.GOLD + username + ChatFormatting.RESET + ": " + message
            ));
            event.setCanceled(true);
        }
    }
}
