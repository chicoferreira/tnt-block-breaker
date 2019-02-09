package com.chicoferreira.tntblockbreaker;

import com.chicoferreira.tntblockbreaker.utils.GlowApplier;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Settings {

    public static final String IMPULSE_TNT_METADATA;
    public static final ItemStack IMPULSE_TNT_ITEM;
    public static final String IMPULSE_TNT_COMMAND_PERMISSION;
    public static int TNT_RADIUS;
    public static int TNT_RADIUS_SQUARED;

    public static double HEALTH_REGENERATION_BY_SECOND;
    public static int HEALTH_REGENERATE_AFTER_SECONDS;

    public static Material ITEM_CHECK_BLOCK_DAMAGE;
    public static String CHECK_BLOCK_DAMAGE_MESSAGE;
    public static double LIQUID_MULTIPLIER_DAMAGE;
    public static double DEFAULT_MAX_HEALTH;

    static {
        IMPULSE_TNT_COMMAND_PERMISSION = "tntblockbreaker.give";
        IMPULSE_TNT_METADATA = "ImpulseTnt";

        FileConfiguration config = TntBlockBreaker.getInstance().getConfig();

        TNT_RADIUS = config.getInt("TntRadius");
        TNT_RADIUS_SQUARED = TNT_RADIUS * TNT_RADIUS;

        HEALTH_REGENERATION_BY_SECOND = config.getDouble("HealthRegenerationBySecond");
        HEALTH_REGENERATE_AFTER_SECONDS = config.getInt("HealthRegenerateAfter");

        Material material = Material.getMaterial(config.getString("CheckItem").toUpperCase());
        if (material == null) {
            material = Material.STICK;
        }

        ITEM_CHECK_BLOCK_DAMAGE = material;
        CHECK_BLOCK_DAMAGE_MESSAGE = config.getString("CheckBlockDamageMessage").replaceAll("<(.*?)>", "%s");

        LIQUID_MULTIPLIER_DAMAGE = config.getDouble("LiquidMultiplier");

        DEFAULT_MAX_HEALTH = config.getDouble("Health.DEFAULT");

        ConfigurationSection configurationSection = config.getConfigurationSection("ImpulseItem");

        Material impulseMaterial = Material.getMaterial(configurationSection.getString("Type").toUpperCase());
        if (impulseMaterial == null) {
            impulseMaterial = Material.TNT;
        }

        ItemStack impulseTnt = new ItemStack(impulseMaterial);
        ItemMeta itemMeta = impulseTnt.getItemMeta();
        itemMeta.setDisplayName(configurationSection.getString("DisplayName"));
        itemMeta.setLore(configurationSection.getStringList("Lore"));
        impulseTnt.setItemMeta(itemMeta);
        if (configurationSection.getBoolean("Glow")) {
            impulseTnt = GlowApplier.addGlow(impulseTnt);
        }

        IMPULSE_TNT_ITEM = impulseTnt;
    }
}
