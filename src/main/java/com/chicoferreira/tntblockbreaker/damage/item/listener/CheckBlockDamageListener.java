package com.chicoferreira.tntblockbreaker.damage.item.listener;

import com.chicoferreira.tntblockbreaker.Settings;
import com.chicoferreira.tntblockbreaker.damage.BlockDamage;
import com.chicoferreira.tntblockbreaker.damage.BlockDamageController;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.DecimalFormat;

public class CheckBlockDamageListener implements Listener {

    private final DecimalFormat decimalFormat;

    public CheckBlockDamageListener() {
        decimalFormat = new DecimalFormat("0.00");
    }

    @EventHandler
    public void clickEvent(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock != null && event.getItem() != null && event.getItem().getType() == Settings.ITEM_CHECK_BLOCK_DAMAGE) {
            final BlockDamage blockDamage = BlockDamageController.getInstance().get(clickedBlock.getLocation());

            String regeneratingColor = blockDamage.isRegenerating() ? "§a" : "";
            String formattedBlockHealth = regeneratingColor + decimalFormat.format(blockDamage.getHealth());
            String formattedBlockMaxHealth = decimalFormat.format(blockDamage.getMaxHealth());
            event.getPlayer().sendMessage(String.format(Settings.CHECK_BLOCK_DAMAGE_MESSAGE, formattedBlockHealth, formattedBlockMaxHealth));
            event.setCancelled(true);
        }
    }
}
