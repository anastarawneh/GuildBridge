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
        String messageFormat = config.getCategory("general").get("bridgeFormat").getString();
        String regex = "^Guild > (\\[(?:VI|MV)P\\+{0,2}] )?([^ ]*)( \\[\\w*])?: (.*)";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        String[] usernames = config.getCategory("general").get("bridgeUsernames").getStringList();
        for (String bridgeUsername : usernames) {
            if (matcher.matches() && matcher.group(2).equalsIgnoreCase(bridgeUsername)) {
                String bridgeMessage = matcher.group(4);
                regex = messageFormat.replaceAll("\\$username", "(.*)").replaceAll("\\$message", ".*");
                matcher = Pattern.compile(regex).matcher(bridgeMessage);
                if (matcher.matches()) {
                    String username = matcher.group(1);
                    regex = messageFormat.replaceAll("\\$username", ".*").replaceAll("\\$message", "(.*)");
                    matcher = Pattern.compile(regex).matcher(bridgeMessage);
                    if (matcher.matches()) {
                        String message = matcher.group(1);
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                                ChatFormatting.BLUE + "Discord > " + ChatFormatting.GOLD + username + ChatFormatting.RESET + ": " + message
                        ));
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        }
    }
}
