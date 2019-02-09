package com.chicoferreira.tntblockbreaker.stats.loader;

import com.chicoferreira.tntblockbreaker.TntBlockBreaker;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MaterialStatsLoader {

    public static Map<Material, Double> load(FileConfiguration fileConfiguration) {
        Map<Material, Double> materialDoubleMap = new HashMap<>();

        ConfigurationSection configurationSection = fileConfiguration.getConfigurationSection("Health");
        for (String key : configurationSection.getKeys(false)) {
            Material material = Material.getMaterial(key.toUpperCase());
            if (material == null) {
                TntBlockBreaker.getInstance().getLogger().severe("Material '" + key + "' does not exist.");
                continue;
            }
            double health = fileConfiguration.getDouble("Health." + key);

            materialDoubleMap.put(material, health);
        }

        return materialDoubleMap;
    }

}
