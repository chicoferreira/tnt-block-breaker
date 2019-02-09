package com.chicoferreira.tntblockbreaker.impulsetnt.listener;

import com.chicoferreira.tntblockbreaker.Settings;
import com.chicoferreira.tntblockbreaker.TntBlockBreaker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class ImpulseTntListener implements Listener {

    private FixedMetadataValue metadataValue;

    public ImpulseTntListener() {
        metadataValue = new FixedMetadataValue(TntBlockBreaker.getInstance(), true);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.isSimilar(Settings.IMPULSE_TNT_ITEM)) {
            event.getBlock().setMetadata(Settings.IMPULSE_TNT_METADATA, metadataValue);
        }
    }

    @EventHandler
    public void clickWithFlintNSteel(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().hasMetadata(Settings.IMPULSE_TNT_METADATA)) {
                if (event.getPlayer().getItemInHand().getType() == Material.FLINT_AND_STEEL) {
                    event.setCancelled(true);
                    this.igniteBlock(event.getClickedBlock().getLocation());
                }
            }
        }
    }

    @EventHandler
    public void dispenseEvent(BlockDispenseEvent event) {
        ItemStack item = event.getItem();
        Block block = event.getBlock();

        if (item.isSimilar(Settings.IMPULSE_TNT_ITEM)) {
            event.setCancelled(true);
            if (block.getState() instanceof Dispenser) {
                ((Dispenser) block.getState()).getInventory().removeItem(item);
            }

            igniteBlock(event.getVelocity().toLocation(block.getWorld()).getBlock().getLocation(), false);
        }
    }

    @EventHandler
    public void redstoneCurrent(BlockRedstoneEvent event) {
        Block block = event.getBlock();
        if (event.getNewCurrent() >= 1) {
            checkNearby(block);
        }
    }

    @EventHandler
    public void placeRedstone(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getBlockPower() >= 1) {
            checkNearby(block);
        }
    }

    private void checkNearby(Block block) {
        for (BlockFace blockFace : BlockFace.values()) {
            Block relative = block.getRelative(blockFace);
            if (relative.getType() == Material.TNT && relative.hasMetadata(Settings.IMPULSE_TNT_METADATA)) {
                this.igniteBlock(relative.getLocation());
            }
        }
    }

    public void igniteBlock(Location location) {
        igniteBlock(location, true);
    }

    public void igniteBlock(Location location, boolean isTnt) {
        if (isTnt) {
            location.getBlock().removeMetadata(Settings.IMPULSE_TNT_METADATA, TntBlockBreaker.getInstance());
            location.getBlock().setType(Material.AIR);
        }

        TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location.add(0.5, 0.0, 0.5), EntityType.PRIMED_TNT);
        tnt.setVelocity(new Vector(0.02, 0.15, 0.02));
        tnt.setMetadata(Settings.IMPULSE_TNT_METADATA, metadataValue);
        location.getWorld().playSound(location, Sound.FIRE_IGNITE, -4.0f, 12.0f);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityExplode(EntityExplodeEvent event) {
        if (event.getEntity().hasMetadata(Settings.IMPULSE_TNT_METADATA)) {
            event.blockList().clear();
        }
    }
}
