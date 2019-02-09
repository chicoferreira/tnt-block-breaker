package com.chicoferreira.tntblockbreaker.damage;

import com.chicoferreira.tntblockbreaker.stats.MaterialStats;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class BlockDamageController {

    private static BlockDamageController instance;
    private Map<Location, BlockDamage> blockDamageMap;

    private BlockDamageController() {
        blockDamageMap = new HashMap<>();
    }

    public static BlockDamageController getInstance() {
        if (instance == null) {
            instance = new BlockDamageController();
        }
        return instance;
    }

    public BlockDamage get(Location location) {
        deleteUnused();

        BlockDamage blockDamage = blockDamageMap.get(location);
        if (blockDamage == null) {
            blockDamage = create(location);
        }

        return blockDamage;
    }

    public void deleteUnused() {
        blockDamageMap.entrySet().removeIf(entry -> entry.getValue().isFullHealth());
    }

    private BlockDamage create(Location location) {
        double maxHealth = MaterialStats.getInstance().get(location.getBlock().getType());

        BlockDamage blockDamage = BlockDamage.of(location, maxHealth);
        blockDamageMap.put(location, blockDamage);
        return blockDamage;
    }

    public Map<Location, BlockDamage> getBlockDamageMap() {
        return blockDamageMap;
    }

    public void remove(Location location) {
        blockDamageMap.remove(location);
    }
}
