package com.chicoferreira.tntblockbreaker.listener;

import com.chicoferreira.tntblockbreaker.damage.BlockDamageController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockDamageResetEvent implements Listener {

    private final BlockDamageController controller;

    public BlockDamageResetEvent() {
        controller = BlockDamageController.getInstance();
    }

    @EventHandler
    public void blockbreakEvent(BlockBreakEvent event) {
        controller.remove(event.getBlock().getLocation());
    }

    @EventHandler
    public void blockplaceEvent(BlockPlaceEvent event) {
        controller.remove(event.getBlock().getLocation());
    }

}
