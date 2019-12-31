package me.george.astoria.game.command.mode;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandGameMode extends BaseCommand {

    public CommandGameMode() {
        super("gamemode", "/gamemode <gamemode> or /gamemode <player> <gamemode>", "Changes your own or someone else's gamemode.", Arrays.asList("gm", "gmode", "gamem"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player && getInstanceOfPlayer((Player) sender).getRank().equals(Rank.ADMIN))) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + usage);
            } else if (args.length == 1) {
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("The console cannot execute this command.");
                    return true;
                }

                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("creative") || args[0].equals("1")) {
                    if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(ChatColor.AQUA + "Gamemode - " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "CREATIVE");
                    } else {
                        p.sendMessage(ChatColor.RED + "You are already in Creative Mode.");
                    }
                } else if (args[0].equalsIgnoreCase("survival") || args[0].equals("0")) {
                    if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(ChatColor.AQUA + "Gamemode - " + ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "SURVIVAL");
                    } else {
                        p.sendMessage(ChatColor.RED + "You are already in Survival Mode.");
                    }
                } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spec") || args[0].equals("2")) {
                    if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
                        p.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(ChatColor.AQUA + "Gamemode - " + ChatColor.YELLOW.toString() + ChatColor.BOLD + "SPECTATOR");
                    } else {
                        p.sendMessage(ChatColor.RED + "You are already in Spectator Mode.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid Gamemode.");
                    return true;
                }
            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "That player is offline.");
                    return true;
                }

                if (args[1].equalsIgnoreCase("creative") || args[1].equals("1")) {
                    if (!target.getGameMode().equals(GameMode.CREATIVE)) {
                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage((sender instanceof Player ? (getInstanceOfPlayer((Player) sender).getRank().getChatPrefix() +
                                getInstanceOfPlayer((Player) sender).getDisplayName()) : ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "CONSOLE")
                                + ChatColor.GREEN + " has set your Gamemode to " + ChatColor.LIGHT_PURPLE + "CREATIVE");
                        sender.sendMessage(ChatColor.AQUA + "Set " + ChatColor.YELLOW + target.getName() + ChatColor.AQUA + "'s Gamemode TO - " + ChatColor.LIGHT_PURPLE + "CREATIVE");
                    } else {
                        sender.sendMessage(ChatColor.RED + "That player is already in Creative Mode.");
                    }
                } else if (args[1].equalsIgnoreCase("survival") || args[1].equals("0")) {
                    if (!target.getGameMode().equals(GameMode.SURVIVAL)) {
                        target.setGameMode(GameMode.SURVIVAL);
                        target.sendMessage((sender instanceof Player ? (getInstanceOfPlayer((Player) sender).getRank().getChatPrefix() +
                                getInstanceOfPlayer((Player) sender).getDisplayName()) : ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "CONSOLE") + ChatColor.GREEN + " has set your Gamemode to " + ChatColor.DARK_GREEN + "SURVIVAL");
                        sender.sendMessage(ChatColor.AQUA + "Set " + ChatColor.YELLOW + target.getName() + ChatColor.AQUA + "'s Gamemode TO - " + ChatColor.DARK_GREEN + "SURVIVAL");
                    } else {
                        sender.sendMessage(ChatColor.RED + "That player is already in Survival Mode.");
                    }
                } else if (args[1].equalsIgnoreCase("spectator") || args[1].equalsIgnoreCase("spec") || args[0].equals("2")) {
                    if (!target.getGameMode().equals(GameMode.SPECTATOR)) {
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage((sender instanceof Player ? (getInstanceOfPlayer((Player) sender).getRank().getChatPrefix() +
                                getInstanceOfPlayer((Player) sender).getDisplayName()) : ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "CONSOLE") + ChatColor.GREEN + " has set your Gamemode to " + ChatColor.YELLOW + "SPECTATOR");
                        sender.sendMessage(ChatColor.AQUA + "Set " + ChatColor.YELLOW + target.getName() + ChatColor.AQUA + "'s Gamemode TO - " + ChatColor.YELLOW + "SPECTATOR");
                    } else {
                        sender.sendMessage(ChatColor.RED + "That player is already in Spectator Mode.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Gamemode.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + usage);
            }
        }
        return true;
    }
}
