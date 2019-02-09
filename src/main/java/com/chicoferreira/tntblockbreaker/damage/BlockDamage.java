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
//        updateHealth();
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

    public void updateLastHit() {
        this.lastHit = System.currentTimeMillis();
    }

//    public void updateHealth() {
//        if (isRegenerating()) {
//            System.out.println("lastHealthUpdate: " + lastHealthUpdate);
//            long lastHealthUpdateInSeconds = (System.currentTimeMillis() - lastHealthUpdate) / 1000;
//            System.out.println("lastHealthUpdateInSeconds: " + lastHealthUpdateInSeconds);
//            long timeToRegenerate = lastHealthUpdateInSeconds - Settings.HEALTH_REGENERATE_AFTER_SECONDS;
//            System.out.println("timeToRegenerate: " + timeToRegenerate);
//            double healthToRegenerate = timeToRegenerate * Settings.HEALTH_REGENERATION_BY_SECOND;
//
//            health = Math.min(health + healthToRegenerate, maxHealth);
//            this.lastHealthUpdate = System.currentTimeMillis();
//        }
//    }

    public boolean isRegenerating() {
//        long lastHitDelay = System.currentTimeMillis() - lastHit;
//        long lastHitDelayInSeconds = lastHitDelay / 1000;
//        return lastHitDelayInSeconds >= Settings.HEALTH_REGENERATE_AFTER_SECONDS && health != maxHealth;
        return false;
    }

    public boolean isFullHealth() {
        return getHealth() == maxHealth;
    }

    public boolean isDead() {
        return getHealth() <= 0;
    }
}
