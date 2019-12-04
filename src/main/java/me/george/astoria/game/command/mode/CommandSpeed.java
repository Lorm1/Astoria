package me.george.astoria.game.command.mode;

import me.george.astoria.game.Rank;
import me.george.astoria.game.command.BaseCommand;
import me.george.astoria.game.player.APlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class CommandSpeed extends BaseCommand {

    public CommandSpeed() {
        super("/speed", "/speed <value> (0-10)", "Sets your flying speed.", Arrays.asList("sp"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        APlayer player = getInstanceOfPlayer(p);

        if (!player.getRank().equals(Rank.ADMIN)) return true;

        if (args.length <= 1) {
            p.sendMessage(ChatColor.RED + usage);
            return true;
        } else if (args.length == 2) {
            float speed;
            try {
                speed = Float.valueOf(args[1]);
            } catch (NumberFormatException e) {
                p.sendMessage(ChatColor.RED + usage);
                return true;
            }

            if (speed < 0.0F) {
                p.sendMessage(ChatColor.RED + "The speed value has to be between 0-10");
                return true;
            }
            if (speed > 10.0F){
                p.sendMessage(ChatColor.RED + "The speed value has to be between 0-10");
                return true;
            }

            speed = speed / 10.0F;

            if (args[0].equalsIgnoreCase("fly")) {
                p.setFlySpeed(speed);
                p.setAllowFlight(true);
                p.sendMessage(ChatColor.AQUA + "Set your flying speed to " + ChatColor.YELLOW + args[1]);
            } else if (args[0].equalsIgnoreCase("walk")) {
                p.setWalkSpeed(speed); // default: 0.2f
                p.sendMessage(ChatColor.AQUA + "Set your walking speed to " + ChatColor.YELLOW + args[1]);
            }
        } else {
            p.sendMessage(ChatColor.RED + usage);
        }
        return true;
    }
}
