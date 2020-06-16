package com.lifeknight.hypixelbruhstal.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;

public class Chat {
	public static ArrayList<String> queuedMessages = new ArrayList<>();
	
	public static void sendChatMessage(String msg) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + "" + Variables.modColor + Variables.modName + " > " + EnumChatFormatting.RESET + msg));
	}

	public static void queueChatMessageForConnection(String msg) {
		queuedMessages.add(msg);
	}
}
