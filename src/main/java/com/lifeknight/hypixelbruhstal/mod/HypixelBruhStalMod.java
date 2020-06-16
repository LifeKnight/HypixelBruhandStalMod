package com.lifeknight.hypixelbruhstal.mod;

import com.lifeknight.hypixelbruhstal.utilities.Chat;
import com.lifeknight.hypixelbruhstal.utilities.Text;
import com.lifeknight.hypixelbruhstal.utilities.User;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.Timer;
import java.util.TimerTask;

import static com.lifeknight.hypixelbruhstal.utilities.Config.*;


@Mod(modid = "hypixelbruhstal", name = "Hypixel Bruh-Stal Mod", version = "1.0", clientSideOnly = true)
public class HypixelBruhStalMod {
	public static boolean onHypixel = false;
	public static boolean bruhModRun = true, bruhModKill = true, bruhModDie = true, stalModRun = true;
	public static String username = "", nick = "";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) {
		
	}
	
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
					"nick = ", "hypixelbruhstal");
		} else {
			bruhModRun = extractBooleanFromConfig("bruhModRun", "hypixelbruhstal");
			bruhModKill = extractBooleanFromConfig("bruhModKill", "hypixelbruhstal");
			bruhModDie = extractBooleanFromConfig("bruhModDie", "hypixelbruhstal");
			stalModRun = extractBooleanFromConfig("stalModRun", "hypixelbruhstal");
			nick = extractStringFromConfig("nick", "hypixelbruhstal");
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		
	}
	
	@SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Minecraft.getMinecraft().theWorld != null) {
					username = User.getUsername();
                	for (String msg: Chat.queuedMessages) {
                	Chat.sendChatMessage(msg);
                	}
                }
            }
        }, 5000L);
		try {
			onHypixel = FMLClientHandler.instance().getClient().getCurrentServerData().serverIP.contains("hypixel.net");
		} catch (Exception ignored) {

		}
    }

    @SubscribeEvent
	public void onClientChatReceived(ClientChatReceivedEvent event) {
		String msg = Text.removeFormattingCodes(event.message.getUnformattedText());
		if (onHypixel) {
			if (bruhModRun) {
				if (bruhModDie && (msg.startsWith(username + " ") || msgStartsWithNick(msg)) && (msg.endsWith(".") || msg.endsWith(". FINAL KILL!")) && !msg.contains(":") && !msg.contains("entered the world")) {
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							Minecraft.getMinecraft().thePlayer.playSound("lifeknight:bruh", 5.0f, 1.0f);
						}
					}, 100L);
				} else if (bruhModKill && (msg.endsWith(" " + username + ".") || msg.endsWith(" " + username + ". FINAL KILL!") || msgEndsWithNick(msg)) && !msg.contains(":") && !msg.contains("entered the world")) {
					Minecraft.getMinecraft().thePlayer.playSound("lifeknight:bruh", 5.0f, 1.0f);
				}

				if (stalModRun) {
					if (msg.startsWith("+") && msg.contains("coins") && (msg.contains(" Win") || msg.contains("(Win)"))) {
						Minecraft.getMinecraft().thePlayer.playSound("lifeknight:stal", 5.0f, 1.0f);
					}
				}
			}
		}
	}

	public static boolean msgStartsWithNick(String input) {
		if (nick != null && !nick.isEmpty()) {
			return input.startsWith(nick + " ");
		}
		return false;
	}

	public static boolean msgEndsWithNick(String input) {
		if (nick != null && !nick.isEmpty()) {
			return input.endsWith(" " + nick + ".") || input.endsWith(" " + nick + ". FINAL KILL!");
		}
		return false;
	}
}