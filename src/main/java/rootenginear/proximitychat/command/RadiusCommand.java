package rootenginear.proximitychat.command;

import net.minecraft.core.net.command.CommandError;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.ServerCommand;
import net.minecraft.server.MinecraftServer;
import rootenginear.proximitychat.ProximityChat;

import static rootenginear.proximitychat.store.PlayerChannelData.getPlayerChannelData;
import static rootenginear.proximitychat.store.PlayerChannelData.setPlayerRadius;

public class RadiusCommand extends ServerCommand {
	public RadiusCommand(MinecraftServer server) {
		super(server, "radius", "rad");
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
			sender.sendMessage(colorfulName + "'s proximity chat radius is: " + getPlayerChannelData(rawName).radius);
			return true;
		}

		int newRadius;
		try {
			newRadius = Integer.parseInt(args[0]);
		} catch (Exception e) {
			throw new CommandError("Not a number: \"" + args[0] + "\"");
		}

		if (newRadius > ProximityChat.MAX_RADIUS) {
			sender.sendMessage("Your radius (" + newRadius + ") exceeds the limit (" + ProximityChat.MAX_RADIUS + ").");
			newRadius = ProximityChat.MAX_RADIUS;
		}

		setPlayerRadius(rawName, newRadius);
		sender.sendMessage("Changed " + colorfulName + "'s proximity chat radius to: " + newRadius);
		return true;
	}

	@Override
	public boolean opRequired(String[] args) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("/radius");
		sender.sendMessage("/radius <number>");
	}
}
