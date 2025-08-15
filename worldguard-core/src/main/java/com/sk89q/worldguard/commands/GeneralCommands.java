/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.commands;

import com.google.common.collect.Lists;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.auth.AuthorizationException;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.GodMode;

public class GeneralCommands {
    private final WorldGuard worldGuard;

    public GeneralCommands(WorldGuard worldGuard) {
        this.worldGuard = worldGuard;
    }
    
    @Command(aliases = {"god"}, usage = "[player]",
            desc = "Увімкнути режим бога для особи", flags = "s", max = 1)
    public void god(CommandContext args, Actor sender) throws CommandException, AuthorizationException {
        Iterable<? extends LocalPlayer> targets = null;
        boolean included = false;
        
        // Detect arguments based on the number of arguments provided
        if (args.argsLength() == 0) {
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(worldGuard.checkPlayer(sender));
            
            // Check permissions!
            sender.checkPermission("worldguard.god");
        } else {
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(sender, args.getString(0));
            
            // Check permissions!
            sender.checkPermission("worldguard.god.other");
        }

        for (LocalPlayer player : targets) {
            Session session = WorldGuard.getInstance().getPlatform().getSessionManager().get(player);

            if (GodMode.set(player, session, true)) {
                player.setFireTicks(0);

                // Tell the user
                if (player.equals(sender)) {
                    player.print("Режим бога увімкнено! Використовуйте /ungod щоб вимкнути.");

                    // Keep track of this
                    included = true;
                } else if (!args.hasFlag('s')) {
                    player.print("Режим бога увімкнено " + sender.getDisplayName() + ".");

                }
            }
        }
        
        // The player didn't receive any items, then we need to send the
        // user a message so s/he know that something is indeed working
        if (!included) {
            sender.print("Особи тепер мають режим бога.");
        }
    }
    
    @Command(aliases = {"ungod"}, usage = "[player]",
            desc = "Вимкнути режим бога для особи", flags = "s", max = 1)
    public void ungod(CommandContext args, Actor sender) throws CommandException, AuthorizationException {
        Iterable<? extends LocalPlayer> targets;
        boolean included = false;
        
        // Detect arguments based on the number of arguments provided
        if (args.argsLength() == 0) {
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(worldGuard.checkPlayer(sender));
            
            // Check permissions!
            sender.checkPermission("worldguard.god");
        } else {
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(sender, args.getString(0));
            
            // Check permissions!
            sender.checkPermission("worldguard.god.other");
        }

        for (LocalPlayer player : targets) {
            Session session = WorldGuard.getInstance().getPlatform().getSessionManager().get(player);

            if (GodMode.set(player, session, false)) {
                // Tell the user
                if (player.equals(sender)) {
                    player.print("Режим бога вимкнено!");

                    // Keep track of this
                    included = true;
                } else if (!args.hasFlag('s')) {
                    player.print("Режим бога вимкнено " + sender.getDisplayName() + ".");

                }
            }
        }
        
        // The player didn't receive any items, then we need to send the
        // user a message so s/he know that something is indeed working
        if (!included) {
            sender.print("Особи більше не мають режиму бога.");
        }
    }
    
    @Command(aliases = {"heal"}, usage = "[player]", desc = "Вилікувати особу", flags = "s", max = 1)
    public void heal(CommandContext args, Actor sender) throws CommandException, AuthorizationException {

        Iterable<? extends LocalPlayer> targets = null;
        boolean included = false;
        
        // Detect arguments based on the number of arguments provided
        if (args.argsLength() == 0) {
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(worldGuard.checkPlayer(sender));
            
            // Check permissions!
            sender.checkPermission("worldguard.heal");
        } else if (args.argsLength() == 1) {            
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(sender, args.getString(0));
            
            // Check permissions!
            sender.checkPermission("worldguard.heal.other");
        }

        for (LocalPlayer player : targets) {
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.setExhaustion(0);
            
            // Tell the user
            if (player.equals(sender)) {
                player.print("Зцілено!");
                
                // Keep track of this
                included = true;
            } else if (!args.hasFlag('s')) {
                player.print("Зцілено " + sender.getDisplayName() + ".");
                
            }
        }
        
        // The player didn't receive any items, then we need to send the
        // user a message so s/he know that something is indeed working
        if (!included) {
            sender.print("Особа вилікувана.");
        }
    }
    
    @Command(aliases = {"slay"}, usage = "[player]", desc = "Вбити особу", flags = "s", max = 1)
    public void slay(CommandContext args, Actor sender) throws CommandException, AuthorizationException {
        
        Iterable<? extends LocalPlayer> targets = Lists.newArrayList();
        boolean included = false;
        
        // Detect arguments based on the number of arguments provided
        if (args.argsLength() == 0) {
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(worldGuard.checkPlayer(sender));
            
            // Check permissions!
            sender.checkPermission("worldguard.slay");
        } else if (args.argsLength() == 1) {            
            targets = worldGuard.getPlatform().getMatcher().matchPlayers(sender, args.getString(0));
            
            // Check permissions!
            sender.checkPermission("worldguard.slay.other");
        }

        for (LocalPlayer player : targets) {
            player.setHealth(0);
            
            // Tell the user
            if (player.equals(sender)) {
                player.print("Вбито!");
                
                // Keep track of this
                included = true;
            } else if (!args.hasFlag('s')) {
                player.print("Вбито " + sender.getDisplayName() + ".");
                
            }
        }
        
        // The player didn't receive any items, then we need to send the
        // user a message so s/he know that something is indeed working
        if (!included) {
            sender.print("Вбито осіб.");
        }
    }
    
    @Command(aliases = {"locate"}, usage = "[player]", desc = "Знайти особу", max = 1)
    @CommandPermissions({"worldguard.locate"})
    public void locate(CommandContext args, Actor sender) throws CommandException {
        LocalPlayer player = worldGuard.checkPlayer(sender);
        
        if (args.argsLength() == 0) {
            player.setCompassTarget(new Location(player.getWorld(), player.getWorld().getSpawnPosition().toVector3()));
            
            sender.print("Компас скинуто до місця відродження.");
        } else {
            LocalPlayer target = worldGuard.getPlatform().getMatcher().matchSinglePlayer(sender, args.getString(0));
            player.setCompassTarget(target.getLocation());
            
            sender.print("Компас перенаправлено.");
        }
    }
    
    @SuppressWarnings("removal")
    @Command(aliases = {"stack", ";"}, usage = "", desc = "Скласти предмети", max = 0)
    @CommandPermissions({"worldguard.stack"})
    public void stack(CommandContext args, Actor sender) throws CommandException {
        LocalPlayer player = worldGuard.checkPlayer(sender);

        WorldGuard.getInstance().getPlatform().stackPlayerInventory(player);

        player.print("Предмети складені в стаки!");
    }
}
