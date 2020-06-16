package com.lifeknight.hypixelbruhstal.mod;

import java.util.ArrayList;
import java.util.List;

import com.lifeknight.hypixelbruhstal.utilities.Chat;
import com.lifeknight.hypixelbruhstal.utilities.Logic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import static com.lifeknight.hypixelbruhstal.mod.HypixelBruhStalMod.*;
import static com.lifeknight.hypixelbruhstal.utilities.Config.updateConfigFromVariables;

public class BruhCommand extends CommandBase {

	@Override
	public List addTabCompletionOptions(ICommandSender arg0, String[] arg1, BlockPos arg2) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("die");
		options.add("kill");
		options.add("nick");
		if (arg1.length > 0) {
			return Logic.returnStartingEntries(options, arg1[0]);
		} else {
			return options;
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return true;
	}

	@Override
	public String getCommandName() {
		return "bruhmod";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName();
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		if (arg1.length == 0) {
			bruhModRun = !bruhModRun;
			String status = bruhModRun ? EnumChatFormatting.GREEN + "ENABLED" : EnumChatFormatting.RED + "DISABLED";
			Chat.sendChatMessage(EnumChatFormatting.YELLOW + "BruhMod status: " + status);
		} else {
			if (arg1[0].equalsIgnoreCase("kill")) {
				bruhModKill = !bruhModKill;
				String status = bruhModKill ? EnumChatFormatting.GREEN + "ENABLED" : EnumChatFormatting.RED + "DISABLED";
				Chat.sendChatMessage(EnumChatFormatting.YELLOW + "BruhMod Kill status: " + status);
			} else if (arg1[0].equalsIgnoreCase("die")) {
				bruhModDie = !bruhModDie;
				String status = bruhModDie ? EnumChatFormatting.GREEN + "ENABLED" : EnumChatFormatting.RED + "DISABLED";
				Chat.sendChatMessage(EnumChatFormatting.YELLOW + "BruhMod Die status: " + status);
			} else if (arg1[0].equalsIgnoreCase("nick")) {
				if (arg1.length > 1) {
					nick = arg1[1];
					Chat.sendChatMessage(EnumChatFormatting.YELLOW + "Nick changed to: " + EnumChatFormatting.AQUA + nick + EnumChatFormatting.YELLOW + ".");
				} else {
					Chat.sendChatMessage(EnumChatFormatting.DARK_GREEN + "/bruhmod nick [your nick]");
				}
			} else {
				Chat.sendChatMessage(EnumChatFormatting.DARK_GREEN + "/bruhmod, /bruhmod kill, /bruhmod die, /bruhmod nick");
			}
		}
		updateConfigFromVariables();
	}
}
