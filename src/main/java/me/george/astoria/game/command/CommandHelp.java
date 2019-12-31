package me.george.astoria.game.command;

import me.george.astoria.Astoria;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandHelp extends BaseCommand {

    private List<String> commands = Astoria.getInstance().getCommands();

    private final int perPage = 6;

    public CommandHelp() {
        super("help", "/help <page>", "Display help for all available commands.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender, 1);
        } else if (args.length == 1) {
            int page;
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid Page.");
                return true;
            }

            if (page > 0 && page <= getTotalPages())
                sendHelp(sender, page);
            else
                sender.sendMessage(ChatColor.RED + "Invalid Page.");
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid Usage.");
            sender.sendMessage(ChatColor.DARK_AQUA + usage);
        }
        return true;
    }

    private void sendHelp(CommandSender sender, int page) {
        List<String> shownCommands = new ArrayList<>();
        int upperBound, displayPage = page - 1, lowerBound = perPage * page;

        if (lowerBound + perPage > commands.size())
            upperBound = commands.size();
        else
            upperBound = lowerBound + perPage;

        for (int i = lowerBound; i < upperBound; i++)
            shownCommands.add(commands.get(i));

        shownCommands.forEach(cmd -> {
            sender.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "     Astoria (" + ChatColor.GOLD + displayPage + "/" + getTotalPages() + ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + ")    ");
            sender.sendMessage(ChatColor.GRAY + " - " + cmd);
        });
    }

    private int getTotalPages() { return (int) Math.ceil((double) commands.size() / perPage); }
}
