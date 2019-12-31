package me.george.astoria.game.command;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import me.george.astoria.Astoria;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandGUIHelp extends BaseCommand {

    protected final SmartInventory helpMenu = SmartInventory.builder()
            .id("helpMenu")
            .provider(new HelpMenu())
            .size(3, 9)
            .title(ChatColor.DARK_AQUA + "Help Menu")
            .closeable(true)
            .build();

    protected List<String> commands = Astoria.getInstance().getCommands();
    protected List<String> shownCommands = new ArrayList<>();

    private final int perPage = 54;

    public CommandGUIHelp() {
        super("guihelp", "/help <page>", "Displays a menu with help for all available commands.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(player, 1);
        } else if (args.length == 1) {
            int page;
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid Page.");
                return true;
            }

            if (page > 0 && page <= getTotalPages())
                sendHelp(player, page);
            else
                sender.sendMessage(ChatColor.RED + "Invalid Page.");
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid Usage.");
            sender.sendMessage(ChatColor.DARK_AQUA + usage);
        }
        return true;
    }

    private void sendHelp(Player player, int page) {
        int upperBound, displayPage = page - 1, lowerBound = perPage * page;

        if (lowerBound + perPage > commands.size())
            upperBound = commands.size();
        else
            upperBound = lowerBound + perPage;

        for (int i = lowerBound; i < upperBound; i++)
            shownCommands.add(commands.get(i));


    }

    private int getTotalPages() { return (int) Math.ceil((double) commands.size() / perPage); }

    public class HelpMenu implements InventoryProvider {

        private ItemStack helpItem = new ItemStack(Material.BOOK);

        @Override
        public void init(Player player, InventoryContents contents) {
            Pagination pagination = contents.pagination();

            ClickableItem[] items = new ClickableItem[shownCommands.size()];

            for (int i = 0; i < items.length; i++)  {
                helpItem.getItemMeta().setDisplayName(ChatColor.GRAY + shownCommands.get(i));
                items[i] = ClickableItem.empty(helpItem);
            }

            pagination.setItems(items);
            pagination.setItemsPerPage(27);

            pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));

            contents.set(2, 2, ClickableItem.of(new ItemStack(Material.ARROW),
                    e -> helpMenu.open(player, pagination.previous().getPage())));
            contents.set(2, 6, ClickableItem.of(new ItemStack(Material.ARROW),
                    e -> helpMenu.open(player, pagination.next().getPage())));
        }

        @Override
        public void update(Player player, InventoryContents inventoryContents) {}
    }
}
