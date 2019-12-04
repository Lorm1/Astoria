package me.george.astoria.game.mechanic.profession;

import lombok.Getter;
import me.george.astoria.game.item.Item;
import org.bukkit.inventory.ItemStack;

public class ProfessionItem extends Item {

    @Getter
    private int level;

    @Getter
    private int XP;

    public ProfessionItem(ItemStack item) {
        super(item);
    }

    public ProfessionItem(ItemStack item, int level) {
        super(item);
        setLevel(level);
    }

    public ProfessionItem setLevel(int level) {
        this.level = level;
        return this;
    }

    public void levelUp() {

    }

    /**
     * Return the XP needed to level up.
     */
    public int getNeededXP() {
        return getNeededXP(getLevel());
    }

    private static int getNeededXP(int level) {
        if (level <= 1)
            return 100;

        if (level == 100)
            return 0;

        int lastLevel = level - 1;
        return (int) (Math.pow(lastLevel, 2) + (lastLevel * 20) + 150 + (lastLevel * 4) + getNeededXP(lastLevel));
    }

    public int getNextTierLevel() {
        return Math.min(((getLevel() / 20) + 1) * 20, 100);
    }
}
