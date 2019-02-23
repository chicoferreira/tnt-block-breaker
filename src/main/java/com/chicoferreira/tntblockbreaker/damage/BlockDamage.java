package com.chicoferreira.tntblockbreaker.damage;

import com.chicoferreira.tntblockbreaker.Settings;
import org.bukkit.Location;

public class BlockDamage {

    private Location location;
    private double health;
    private double maxHealth;
    private long lastHit;
    private long lastHealthUpdate;

    private BlockDamage(Location location, double maxHealth) {
        this.location = location;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.lastHit = System.currentTimeMillis();
        this.lastHealthUpdate = System.currentTimeMillis();
    }

    public static BlockDamage of(Location location, double maxHealth) {
        return new BlockDamage(location, maxHealth);
    }

    public Location getLocation() {
        return location;
    }

    public double getHealth() {
        updateHealth();
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void damage(double damage) {
        health -= damage;
    }

    public void damage() {
        damage(1);
    }

    public long getLastHit() {
        return lastHit;
    }

    public long getLastHealthUpdate() {
        return lastHealthUpdate;
    }

    public void updateLastHealthUpdate() {
        this.lastHealthUpdate = System.currentTimeMillis();
    }

    public void updateLastHit() {
        this.lastHit = System.currentTimeMillis();
    }

    public void updateHealth() {
        if (isRegenerating()) {
            long lastHitDif = System.currentTimeMillis() - this.getLastHit();
            double lastHitDifSeconds = lastHitDif / 1000D - Settings.HEALTH_REGENERATE_AFTER_SECONDS;

            long lastHealthUpdateDif = System.currentTimeMillis() - this.getLastHealthUpdate();
            double lastHealthUpdateDifSeconds = lastHealthUpdateDif / 1000D;

            double diff = Math.min(lastHealthUpdateDifSeconds, lastHitDifSeconds);

            double healthToAdd = diff * Settings.HEALTH_REGENERATION_BY_SECOND;
            if (healthToAdd + health > maxHealth) {
                healthToAdd = maxHealth - health;
            }

            this.damage(-healthToAdd);
            this.updateLastHealthUpdate();
        }
    }

    public boolean isRegenerating() {
        long lastHitDif = System.currentTimeMillis() - this.getLastHit();
        long lastHitDifSeconds = lastHitDif / 1000;
        return lastHitDifSeconds >= Settings.HEALTH_REGENERATE_AFTER_SECONDS && health != maxHealth;
    }

    public boolean isFullHealth() {
        return getHealth() == maxHealth;
    }

    public boolean isDead() {
        return getHealth() <= 0;
    }
}
