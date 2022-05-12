package me.hapyl.mmu3.feature.statechanger;

import kz.hapyl.spigotutils.module.inventory.ItemBuilder;
import me.hapyl.mmu3.Main;
import me.hapyl.mmu3.utils.InjectListener;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StateChangerListener extends InjectListener {

    public StateChangerListener(Main main) {
        super(main);
    }

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent ev) {
        final Player player = ev.getPlayer();
        final ItemStack item = ev.getItem();
        final Action action = ev.getAction();
        final Block clickedBlock = ev.getClickedBlock();

        if (!player.isOp()
                || player.getGameMode() != GameMode.CREATIVE
                || clickedBlock == null
                || action == Action.PHYSICAL
                || item == null
                || !ItemBuilder.itemContainsId(item, "state_changer")) {
            return;
        }

        ev.setCancelled(true);
        stateChanger().openEditor(player, clickedBlock);

    }

    // temp for testing
    @EventHandler()
    public void handleInventoryClickEvent(InventoryClickEvent ev) {
        final ClickType click = ev.getClick();
        if (click == ClickType.SHIFT_RIGHT && ev.getWhoClicked().isOp()) {
            final int rawSlot = ev.getRawSlot();
            ev.setCancelled(true);
            ev.getWhoClicked().sendMessage("Clicked " + ChatColor.BOLD + rawSlot);
        }
    }

}
