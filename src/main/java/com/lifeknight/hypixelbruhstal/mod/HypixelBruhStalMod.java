package com.lifeknight.hypixelbruhstal.mod;

import com.lifeknight.hypixelbruhstal.utilities.Chat;
import com.lifeknight.hypixelbruhstal.utilities.Text;
import com.lifeknight.hypixelbruhstal.utilities.User;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.logging.log4j.Level;

import java.util.Timer;
import java.util.TimerTask;

import static com.lifeknight.hypixelbruhstal.utilities.Config.*;


@Mod(modid = "hypixelbruhstal", name = "Hypixel Bruh-Stal Mod", version = "1.1", clientSideOnly = true)
public class HypixelBruhStalMod {
    public static boolean onHypixel = false;
    public static boolean bruhModRun = true, bruhModKill = true, bruhModDie = true, stalModRun = true;
    public static String nick = "";
    public static boolean debug = false;

    @EventHandler
    public void init(FMLInitializationEvent initEvent) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new BruhCommand());
        ClientCommandHandler.instance.registerCommand(new StalCommand());

        if (!configExists("hypixelbruhstal")) {
            writeInConfig("bruhModRun = true" + System.getProperty("line.separator") +
                    "bruhModKill = true" + System.getProperty("line.separator") +
                    "bruhModDie = true" + System.getProperty("line.separator") +
                    "stalModRun = true" + System.getProperty("line.separator") +
                    "debug = false" + System.getProperty("line.separator") +
                    "nick = ", "hypixelbruhstal");
        } else {
            bruhModRun = extractBooleanFromConfig("bruhModRun", "hypixelbruhstal");
            bruhModKill = extractBooleanFromConfig("bruhModKill", "hypixelbruhstal");
            bruhModDie = extractBooleanFromConfig("bruhModDie", "hypixelbruhstal");
            stalModRun = extractBooleanFromConfig("stalModRun", "hypixelbruhstal");
            debug = extractBooleanFromConfig("debug", "hypixelbruhstal");
            nick = extractStringFromConfig("nick", "hypixelbruhstal");
        }
    }

    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    onHypixel = FMLClientHandler.instance().getClient().getCurrentServerData().serverIP.contains("hypixel.net");
                    for (String queuedMessage : Chat.queuedMessages) {
                        Chat.sendChatMessage(queuedMessage);
                    }
                    Chat.queuedMessages.clear();
                } catch (Exception ignored) {

                }
            }
        }, 5000L);
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        String msg = Text.removeFormattingCodes(event.message.getUnformattedText());
        if (onHypixel) {
            if (bruhModRun) {
                if (bruhModDie && messageSuitableForDeath(msg)) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Minecraft.getMinecraft().thePlayer.playSound("lifeknight:bruh", 5.0f, 1.0f);
                            if (debug) {
                                log("Playing bruh for message: %s", msg);
                            }
                        }
                    }, 100L);
                } else if (bruhModKill && messageSuitableForKill(msg)) {
                    Minecraft.getMinecraft().thePlayer.playSound("lifeknight:bruh", 5.0f, 1.0f);
                    if (debug) {
                        log("Playing bruh for message: %s", msg);
                    }
                }

                if (stalModRun) {
                    if (msg.startsWith("+") && msg.contains("coins") && (msg.contains(" Win") || msg.contains("(Win)"))) {
                        Minecraft.getMinecraft().thePlayer.playSound("lifeknight:stal", 5.0f, 1.0f);
                        if (debug) {
                            log("Playing stal for message: %s", msg);
                        }
                    }
                }
            }
        }
    }

    public static void log(String format, Object... data) {
        FMLLog.log(Level.INFO, format, data);
    }

    public static boolean messageSuitableForDeath(String message) {
        return (message.startsWith(User.getUsername() + " ") || messageStartsWithNick(message))
                && (message.endsWith(".") || message.endsWith(". FINAL KILL!")) &&
                messageDoesNotContainBadTerms(message);
    }

    public static boolean messageSuitableForKill(String message) {
        return (message.endsWith(" " + User.getUsername() + ".") ||
                message.endsWith(" " + User.getUsername() + ". FINAL KILL!") ||
                messageEndsWithNick(message)) &&
                messageDoesNotContainBadTerms(message);
    }

    public static boolean messageDoesNotContainBadTerms(String message) {
        return !(message.contains(":") ||
                message.contains("the world") ||
                message.contains("the lobby") ||
                message.contains("party"));
    }

    public static boolean messageStartsWithNick(String input) {
        if (!(nick == null || nick.isEmpty() || nick.equalsIgnoreCase("you"))) {
            return input.startsWith(nick + " ");
        }
        return false;
    }

    public static boolean messageEndsWithNick(String input) {
        if (!(nick == null || nick.isEmpty() || nick.equalsIgnoreCase("you"))) {
            return input.endsWith(" " + nick + ".") || input.endsWith(" " + nick + ". FINAL KILL!");
        }
        return false;
    }
}