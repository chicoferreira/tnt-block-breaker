package com.chicoferreira.tntblockbreaker;

import com.chicoferreira.tntblockbreaker.damage.item.listener.CheckBlockDamageListener;
import com.chicoferreira.tntblockbreaker.impulsetnt.listener.ImpulseTntListener;
import com.chicoferreira.tntblockbreaker.impulsetnt.command.ImpulseTntGiveCommand;
import com.chicoferreira.tntblockbreaker.listener.BlockDamageResetEvent;
import com.chicoferreira.tntblockbreaker.listener.TntExplosionEvent;
import com.chicoferreira.tntblockbreaker.stats.MaterialStats;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TntBlockBreaker extends JavaPlugin {

    private static TntBlockBreaker instance;

    public static TntBlockBreaker getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        synchronized (TntBlockBreaker.class) {
            instance = this;
        }

        saveDefaultConfig();

        MaterialStats.getInstance().load(this.getConfig());

        registerListener(new TntExplosionEvent());
        registerListener(new CheckBlockDamageListener());
        registerListener(new BlockDamageResetEvent());

        registerListener(new ImpulseTntListener());

        getCommand("giveimpulsetnt").setExecutor(new ImpulseTntGiveCommand());
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
