package com.lifeknight.hypixelbruhstal.utilities;

import net.minecraft.client.Minecraft;

public class User {
	
	public static String getUsername() {
		return Minecraft.getMinecraft().thePlayer.getName();
	}
	
}
