package me.george.astoria.game.command.mode;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandGod extends BaseCommand {
    public CommandGod() {
        super("god", "/god or /god <player>", "Enables god mode for yourself or a player.", Arrays.asList("godmode", "godm"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "The console cannot execute this command.");
                return true;
            }

            Player p = (Player) sender;
            APlayer player = getInstanceOfPlayer(p);

            if (!player.getRank().equals(Rank.ADMIN)) return true;

            if (!p.isInvulnerable()) {
                p.setInvulnerable(true);
                p.sendMessage(ChatColor.DARK_AQUA + "God Mode - " + ChatColor.GREEN + "Enabled");
                return true;
            } else {
                p.setInvulnerable(false);
                p.sendMessage(ChatColor.DARK_AQUA + "God Mode - " + ChatColor.RED + "Disabled");
            }
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "That player is not online.");
                return true;
            }

            if (!target.isInvulnerable()) {
                target.setInvulnerable(true);
                sender.sendMessage(ChatColor.GREEN + "Enabled God Mode for " + ChatColor.YELLOW + target.getName() + ChatColor.GREEN + ".");
                target.sendMessage(ChatColor.DARK_AQUA + "God Mode - " + ChatColor.GREEN + "Enabled");
            } else {
                target.setInvulnerable(false);
                sender.sendMessage(ChatColor.RED + "Disabled God Mode for " + ChatColor.YELLOW + target.getName() + ChatColor.RED + ".");
                target.sendMessage(ChatColor.DARK_AQUA + "God Mode - " + ChatColor.RED + "Disabled");
            }
        } else
            sender.sendMessage(ChatColor.RED + usage);
        return true;
    }
}
