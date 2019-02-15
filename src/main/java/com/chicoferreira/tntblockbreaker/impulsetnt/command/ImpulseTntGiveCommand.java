package com.chicoferreira.tntblockbreaker.impulsetnt.command;

import com.chicoferreira.tntblockbreaker.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ImpulseTntGiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission(Settings.IMPULSE_TNT_COMMAND_PERMISSION) || commandSender.isOp()) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;

                player.getInventory().addItem(Settings.IMPULSE_TNT_ITEM);
            }
        }
        return false;
    }

}
