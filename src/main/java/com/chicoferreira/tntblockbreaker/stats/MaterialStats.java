package com.chicoferreira.tntblockbreaker.stats;

import com.chicoferreira.tntblockbreaker.Settings;
import com.chicoferreira.tntblockbreaker.stats.loader.MaterialStatsLoader;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MaterialStats {

    private static MaterialStats instance;

    private Map<Material, Double> materialDoubleMap;

    private MaterialStats() {
        this.materialDoubleMap = new HashMap<>();
    }

    public static MaterialStats getInstance() {
        if (instance == null) {
            instance = new MaterialStats();
        }
        return instance;
    }

    public void load(FileConfiguration fileConfiguration) {
        materialDoubleMap.putAll(MaterialStatsLoader.load(fileConfiguration));
    }

    public double get(Material material) {
        return materialDoubleMap.getOrDefault(material, Settings.DEFAULT_MAX_HEALTH);
    }

}
