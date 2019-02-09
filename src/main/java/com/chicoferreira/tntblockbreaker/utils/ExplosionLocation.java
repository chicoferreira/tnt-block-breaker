package com.chicoferreira.tntblockbreaker.utils;

import com.chicoferreira.tntblockbreaker.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExplosionLocation {

    private static final Random RANDOM = new Random();

    public static Map<Block, Double> computeBlockLocations(Location explosionLocation) {
        Map<Block, Double> blocks = new HashMap<>();

        int explosionX = explosionLocation.getBlockX();
        int explosionY = explosionLocation.getBlockY();
        int explosionZ = explosionLocation.getBlockZ();

        int radius = Settings.TNT_RADIUS;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Vector vector = new Vector(x, y, z);

                    if (vector.lengthSquared() <= Settings.TNT_RADIUS_SQUARED) {

                        Block block = explosionLocation.clone().add(vector).getBlock();
                        if (block.getType() != Material.AIR && block.getY() < 256 && block.getY() >= 0) {
                            double damage = 1;

                            if (block.isLiquid()) {
                                damage *= Settings.LIQUID_MULTIPLIER_DAMAGE;
                            }

                            damage = damage / (vector.length() * 0.65);

                            blocks.put(block, damage);
                        }
                    }
                }
            }
        }

        return blocks;
    }

}
