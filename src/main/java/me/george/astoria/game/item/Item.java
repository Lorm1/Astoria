package me.george.astoria.game.item;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    @Getter
    protected ItemStack item;

    @Getter
    @Setter
    protected String name;

    @Getter
    protected List<String> lore;

    @Getter
    protected ItemMeta meta;

    public Item(ItemStack item) {
        this.item = item;
    }

    public Item(ItemStack item, String name) {
        this.item = item;
        this.name = name;
    }

    public Item(ItemStack item, String name, List<String> lore) {
        this.item = item;
        this.name = name;
        this.lore = lore;
    }

    public Item(ItemStack item, String name, ItemMeta meta, List<String> lore) {
        this.item = item;
        this.name = name;
        this.meta = meta;
        this.lore = lore;
    }

    public void addLore(String s) {
        if (this.lore == null) // Can't put above constructor as it will override any values set in loadItem
            this.lore = new ArrayList<>();

        String newLine = startsWithValidColor(s) ? s : ChatColor.GRAY + s;
        //Dont count for emtpy lines?
        if (!s.isEmpty() && this.lore.contains(newLine))
            return;

        if (newLine.contains("\n")) {
            //Add all lines?
            List<String> lines = Lists.newArrayList(newLine.split("\n"));
            for (String line : lines) {
                line = ChatColor.translateAlternateColorCodes('&', line);
                this.lore.add(startsWithValidColor(line) ? line : ChatColor.GRAY + line);
            }
        } else {
            this.lore.add(newLine);
        }
    }

    public boolean startsWithValidColor(String string) {
        if (string.startsWith(ChatColor.COLOR_CHAR + "")) {
            if (string.startsWith(ChatColor.ITALIC.toString()) || string.startsWith(ChatColor.UNDERLINE.toString())
                    || string.startsWith(ChatColor.STRIKETHROUGH.toString()) || string.startsWith(ChatColor.BOLD.toString()))
                return false;
            return true;
        }
        return false;
    }

    public void setCustomLore(List<String> string) {
        this.lore = string;
    }

    protected void removeLore(String startsWith) {
        if (this.lore == null || this.lore.isEmpty()) return;
        for (int i = 0; i < this.lore.size(); i++) {
            if (this.lore.get(i).startsWith(startsWith)) {
                this.lore.remove(i);
            }
        }
    }

    protected void saveMeta() {
        ItemStack withMeta = getItem().clone();
        getMeta().setLore(this.lore);
        withMeta.setItemMeta(getMeta());
        getItem().setItemMeta(getMeta());
    }
}
