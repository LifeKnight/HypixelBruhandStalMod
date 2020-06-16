package com.lifeknight.hypixelbruhstal.mod;

import com.lifeknight.hypixelbruhstal.utilities.Chat;
import com.lifeknight.hypixelbruhstal.utilities.Logic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

import static com.lifeknight.hypixelbruhstal.mod.HypixelBruhStalMod.nick;
import static com.lifeknight.hypixelbruhstal.mod.HypixelBruhStalMod.stalModRun;
import static com.lifeknight.hypixelbruhstal.utilities.Config.updateConfigFromVariables;

public class StalCommand extends CommandBase {

	@Override
	public List addTabCompletionOptions(ICommandSender arg0, String[] arg1, BlockPos arg2) {
		ArrayList<String> options = new ArrayList<String>();
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
		return "stalmod";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName();
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		if (arg1.length == 0) {
			stalModRun = !stalModRun;
			String status = stalModRun ? EnumChatFormatting.GREEN + "ENABLED" : EnumChatFormatting.RED + "DISABLED";
			Chat.sendChatMessage(EnumChatFormatting.YELLOW + "StalMod status: " + status);
		} else {
			if (arg1[0].equalsIgnoreCase("nick")) {
				if (arg1.length > 1) {
					nick = arg1[1];
					Chat.sendChatMessage(EnumChatFormatting.YELLOW + "Nick changed to: " + EnumChatFormatting.AQUA + nick + EnumChatFormatting.YELLOW + ".");
				} else {
					Chat.sendChatMessage(EnumChatFormatting.DARK_GREEN + "/stalmod nick [your nick]");
				}
			}
		}
		updateConfigFromVariables();
	}

}
