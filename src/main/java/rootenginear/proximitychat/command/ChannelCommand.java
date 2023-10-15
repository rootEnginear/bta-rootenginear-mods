package rootenginear.proximitychat.command;

import net.minecraft.core.net.command.CommandError;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.ServerCommand;
import net.minecraft.server.MinecraftServer;

import static rootenginear.proximitychat.store.PlayerChannelData.getPlayerChannelData;
import static rootenginear.proximitychat.store.PlayerChannelData.setPlayerChannel;

public class ChannelCommand extends ServerCommand {
	public ChannelCommand(MinecraftServer server) {
		super(server, "channel", "ch");
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {
		if (!sender.isPlayer()) {
			throw new CommandError("Must be used by a player!");
		}
		if (args.length > 1) {
			return false;
		}

		String colorfulName = sender.getName() + "§r";
		String rawName = sender.getName().substring(2);

		if (args.length == 0) {
			sender.sendMessage(colorfulName + "'s current channel is: " + (getPlayerChannelData(rawName).isGlobal ? "Global" : "Proximity"));
			return true;
		}

		switch (args[0]) {
			case "global":
			case "proximity":
			case "prox":
				setPlayerChannel(rawName, args[0]);
				sender.sendMessage("Changed " + colorfulName + "'s channel to: " + (args[0].equals("global") ? "Global" : "Proximity"));
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean opRequired(String[] args) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("/channel");
		sender.sendMessage("/channel <global|prox>");
	}
}
