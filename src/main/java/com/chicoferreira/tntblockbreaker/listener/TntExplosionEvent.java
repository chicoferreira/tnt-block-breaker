package com.chicoferreira.tntblockbreaker.listener;

import com.chicoferreira.tntblockbreaker.Settings;
import com.chicoferreira.tntblockbreaker.damage.BlockDamage;
import com.chicoferreira.tntblockbreaker.damage.BlockDamageController;
import com.chicoferreira.tntblockbreaker.utils.ExplosionLocation;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class TntExplosionEvent implements Listener {

    private final BlockDamageController controller;

    public TntExplosionEvent() {
        controller = BlockDamageController.getInstance();
    }

    @EventHandler
    public void entityExplode(EntityExplodeEvent event) {
        Map<Block, Double> blockDoubleMap = ExplosionLocation.computeBlockLocations(event.getLocation());

        Iterator<Map.Entry<Block, Double>> iterator = blockDoubleMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Block, Double> entry = iterator.next();

            Block block = entry.getKey();
            double damage = entry.getValue();

            BlockDamage blockDamage = controller.get(block.getLocation());
            blockDamage.damage(damage);

            if (!blockDamage.isDead()) {
                iterator.remove();
            } else {
                controller.remove(block.getLocation());
            }
        }
        event.blockList().clear();
        if (!event.getEntity().hasMetadata(Settings.IMPULSE_TNT_METADATA)) {
            event.blockList().addAll(new ArrayList<>(blockDoubleMap.keySet()));
        }
    }
}
