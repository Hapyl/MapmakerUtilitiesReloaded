package me.hapyl.mmu3.feature.itemcreator.gui;

import me.hapyl.mmu3.Main;
import me.hapyl.mmu3.Message;
import me.hapyl.mmu3.feature.itemcreator.ItemCreator;
import me.hapyl.mmu3.utils.PanelGUI;
import org.bukkit.entity.Player;

public abstract class ICSubGUI extends PanelGUI {

    public ICSubGUI(Player player, String name, Size size) {
        this(player, name, size, true);
    }

    public ICSubGUI(Player player, String name, Size size, boolean updateAndOpen) {
        super(player, "Item Creator %s %s".formatted(Message.ARROW, name), size);
        if (updateAndOpen) {
            updateInventory();
            openInventory();
        }
    }

    public final ItemCreator creator() {
        return Main.getItemCreator().getCreator(getPlayer());
    }

}
