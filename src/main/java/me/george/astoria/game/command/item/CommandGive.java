package me.george.astoria.game.command.item;

import me.george.astoria.game.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandGive extends BaseCommand {

    public CommandGive() {
        super("give", "/give <item> or /give <item> <amount> or /give <player> <item> <amount> (optional)", "Gives yourself or a player an item of a specific amount.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || (sender instanceof Player && getInstanceOfPlayer((Player) sender).isStaff())) {
            if (args.length == 0) { // /give
                sender.sendMessage(ChatColor.RED + usage);
                return true;
            } else if (args.length == 1) { // /give <item>
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(ChatColor.RED + "Cannot execute this command as CONSOLE.");
                    return true;
                }
                Material material;
                try {
                    material = Material.valueOf(args[0].toUpperCase());
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Invalid Item.");
                    return true;
                }

                ItemStack item = new ItemStack(material);
                item.setAmount(64); // since he hasnt specified anything, default to 64.
                ((Player) sender).getInventory().addItem(item);
                sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GOLD + "64 " + ChatColor.RED + (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", "").toLowerCase())
                        + ChatColor.GREEN + " to your inventory.");
            } else if (args.length == 2) { // give <item> <amount>
                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(ChatColor.RED + "Cannot execute this command as CONSOLE.");
                    return true;
                }
                Material material;
                int amount;
                try {
                    material = Material.valueOf(args[0].toUpperCase());
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Invalid Item.");
                    return true;
                }

                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Invalid Number.");
                    return true;
                }

                ItemStack item = new ItemStack(material);
                item.setAmount(amount);
                ((Player) sender).getInventory().addItem(item);
                sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GOLD + amount + " " + ChatColor.RED + (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", "").toLowerCase())
                        + ChatColor.GREEN + " to your inventory.");
            } else if (args.length == 3) { // /give <player> <item> <amount>
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "That player is offline.");
                }
                Material material;
                int amount;
                try {
                    material = Material.valueOf(args[1].toUpperCase());
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Invalid Item.");
                    return true;
                }

                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Invalid Number");
                    return true;
                }

                ItemStack item = new ItemStack(material);
                item.setAmount(amount);
                target.getInventory().addItem(item);
                target.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "CONSOLE " + ChatColor.GREEN + "has given you " + ChatColor.GOLD + amount + " " + ChatColor.RED + (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", "").toLowerCase()));
                sender.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.GOLD + amount + " " + ChatColor.RED + (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", "").toLowerCase()) + ChatColor.GREEN + " to " + ChatColor.YELLOW + target.getDisplayName());
            } else {
                sender.sendMessage(ChatColor.RED + usage);
            }
        }
        return true;
    }
}
