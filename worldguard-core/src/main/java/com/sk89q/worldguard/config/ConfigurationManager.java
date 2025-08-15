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

package com.sk89q.worldguard.config;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.storage.DriverType;
import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
import com.sk89q.worldguard.session.handler.WaterBreathing;
import com.sk89q.worldedit.util.report.Unreported;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Represents the global configuration and also delegates configuration
 * for individual worlds.
 *
 * @author sk89q
 * @author Michael
 */
public abstract class ConfigurationManager {

    protected static final Logger log = Logger.getLogger(ConfigurationManager.class.getCanonicalName());

    static final String CONFIG_HEADER = "#\r\n" +
            "# WorldGuard головний файл конфігурації\r\n" +
            "#\r\n" +
            "# Це глобальний файл конфігурації. Все, що розміщено тут, буде\r\n" +
            "# застосовувано до всіх світів. Однак кожен світ має свою власну конфігураційний\r\n" +
            "# файл, який дозволяє замінити більшість налаштувань тільки для цього світу.\r\n" +
            "#\r\n" +
            "# Про редагування цього файлу:\r\n" +
            "# - НЕ ВИКОРИСТОВУЙТЕ ТАБИ. Ви ПОВИННІ використовувати пробіли, інакше Bukkit видасть помилку. Якщо\r\n" +
            "#   ви використовуєте редактор, такий як Notepad++ (рекомендовано для користувачів Windows), ви\r\n" +
            "#   маєте налаштувати його на \"replace tabs with spaces.\" В Notepad++, це можна\r\n" +
            "#   змінити в Settings > Preferences > Language Menu.\r\n" +
            "# - Не видаляйте відступи. Вони мають відступ, тому що деякі записи є\r\n" +
            "#   в категоріях (наприклад \"enforce-single-session\" знаходиться в \"protection\"\r\n" +
            "#   категорії.\r\n" +
            "# - Якщо ви хочете перевірити формат цього файлу перед тим, як додати його\r\n" +
            "#   в WorldGuard, вставте його в http://yaml-online-parser.appspot.com/\r\n" +
            "#   і подивитися, чи це дає результат \"ERROR:\".\r\n" +
            "# - Рядки, що починаються з # це коментарі, тому вони ігноруються.\r\n" +
            "#\r\n";

    public boolean useRegionsCreatureSpawnEvent;
    public boolean activityHaltToggle = false;
    public boolean useGodPermission;
    public boolean useGodGroup;
    public boolean useAmphibiousGroup;
    public boolean usePlayerMove;
    public boolean usePlayerTeleports;
    public boolean deopOnJoin;
    public boolean blockInGameOp;
    public boolean migrateRegionsToUuid;
    public boolean keepUnresolvedNames;
    public boolean particleEffects;
    public boolean disablePermissionCache;
    public boolean disableDefaultBypass;
    public boolean announceBypassStatus;

    @Unreported public Map<String, String> hostKeys = new HashMap<>();
    public boolean hostKeysAllowFMLClients;

    /**
     * Region Storage Configuration method, and config values
     */
    @Unreported public RegionDriver selectedRegionStoreDriver;
    @Unreported public Map<DriverType, RegionDriver> regionStoreDriverMap;

    /**
     * Get the folder for storing data files and configuration.
     *
     * @return the data folder
     */
    public abstract File getDataFolder();

    /**
     * Get the folder for storing data files and configuration for each
     * world.
     *
     * @return the data folder
     */
    public File getWorldsDataFolder() {
        return new File(getDataFolder(), "worlds");
    }

    /**
     * Load the configuration.
     */
    public abstract void load();

    /**
     * Unload the configuration.
     */
    public abstract void unload();

    /**
     * Get the configuration for a world.
     *
     * @param world The world to get the configuration for
     * @return {@code world}'s configuration
     */
    public abstract WorldConfiguration get(World world);

    public abstract void disableUuidMigration();

    /**
     * Check to see if god mode is enabled for a player.
     *
     * @param player The player to check
     * @return Whether the player has godmode through WorldGuard or CommandBook
     */
    public boolean hasGodMode(LocalPlayer player) {
        return WorldGuard.getInstance().getPlatform().getSessionManager().get(player).isInvincible(player);
    }

    /**
     * Enable amphibious mode for a player.
     *
     * @param player The player to enable amphibious mode for
     */
    public void enableAmphibiousMode(LocalPlayer player) {
        WaterBreathing handler = WorldGuard.getInstance().getPlatform().getSessionManager().get(player).getHandler(WaterBreathing.class);
        if (handler != null) {
            handler.setWaterBreathing(true);
        }
    }

    /**
     * Disable amphibious mode  for a player.
     *
     * @param player The player to disable amphibious mode for
     */
    public void disableAmphibiousMode(LocalPlayer player) {
        WaterBreathing handler = WorldGuard.getInstance().getPlatform().getSessionManager().get(player).getHandler(WaterBreathing.class);
        if (handler != null) {
            handler.setWaterBreathing(false);
        }
    }

    /**
     * Check to see if amphibious mode is enabled for a player.
     *
     * @param player The player to check
     * @return Whether {@code player} has amphibious mode
     */
    public boolean hasAmphibiousMode(LocalPlayer player) {
        WaterBreathing handler = WorldGuard.getInstance().getPlatform().getSessionManager().get(player).getHandler(WaterBreathing.class);
        return handler != null && handler.hasWaterBreathing();
    }
}
