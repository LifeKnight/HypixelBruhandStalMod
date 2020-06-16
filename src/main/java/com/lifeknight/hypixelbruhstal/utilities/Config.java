package com.lifeknight.hypixelbruhstal.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import net.minecraft.util.EnumChatFormatting;

import static com.lifeknight.hypixelbruhstal.mod.HypixelBruhStalMod.*;

public class Config {
	
	public static void writeInConfig(String text, String name) {
		try {
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("config/" + name + ".cfg"), "UTF-8"));
			
			writer.write(text);
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not write in config");
		}
	}
	
	public static boolean configExists(String name) {		
		File config = new File("config/" + name + ".cfg");

		return config.exists();
 	}
	
	public static void deleteConfig(String name) {
		File config = new File("config/" + name + ".cfg");
		config.delete();
	}

	public static boolean extractBooleanFromConfig(String prefix, String name) {
		String extractee = "";
		try {

			File config = new File("config/" + name + ".cfg");
			
			Scanner reader = new Scanner(config);
						
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				if (line.startsWith(prefix + " = ")) {
					extractee = line.substring(line.indexOf(prefix + " = ") + prefix.length() + 3);
					reader.close();
					return extractee.contentEquals("true");
				}
			}
			
			reader.close();
			Chat.queueChatMessageForConnection(EnumChatFormatting.RED + "An error occurred while extracting the value of \"" + prefix + "\" from the config; the value will be interpreted as FALSE.");
			updateConfigFromVariables();
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			Chat.queueChatMessageForConnection(EnumChatFormatting.RED + "An error occurred while extracting the value of \"" + prefix + "\" from the config; the value will be interpreted as FALSE.");
			updateConfigFromVariables();
			return false;
		}	
	}

	public static String extractStringFromConfig(String prefix, String name) {
		String extractee = "";
		try {

			File config = new File("config/" + name + ".cfg");

			Scanner reader = new Scanner(config);

			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				if (line.startsWith(prefix + " = ")) {
					extractee = line.substring(line.indexOf(prefix + " = ") + prefix.length() + 3);
					reader.close();
					return extractee;
				}
			}

			reader.close();
			Chat.queueChatMessageForConnection(EnumChatFormatting.RED + "An error occurred while extracting the value of \"" + prefix + "\" from the config; the value will be interpreted as NULL.");
			updateConfigFromVariables();
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			Chat.queueChatMessageForConnection(EnumChatFormatting.RED + "An error occurred while extracting the value of \"" + prefix + "\" from the config; the value will be interpreted as NULL.");
			updateConfigFromVariables();
			return null;
		}
	}

	public static void updateConfigFromVariables() {
		deleteConfig("hypixelbruhstal");

		String bruhModRunString = bruhModRun ? "bruhModRun = true" : "bruhModRun = false";
		String bruhModKillString = bruhModKill ? "bruhModKill = true" : "bruhModKill = false";
		String bruhModDieString = bruhModDie ? "bruhModDie = true" : "bruhModDie = false";
		String stalModRunString = stalModRun ? "stalModRun = true" : "stalModRun = false";

		writeInConfig(bruhModRunString + System.getProperty("line.separator") +
				bruhModKillString + System.getProperty("line.separator") +
				bruhModDieString + System.getProperty("line.separator") +
				stalModRunString + System.getProperty("line.separator") +
				"nick = " + nick, "hypixelbruhstal");

	}
}
