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

package com.sk89q.worldguard.bukkit.util.report;

import com.sk89q.worldedit.util.report.DataReport;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class ServerReport extends DataReport {

    public ServerReport() {
        super("Server Information");

        Server server = Bukkit.getServer();

        append("Bukkit версія", server.getBukkitVersion());
        append("Впровадження", server.getName() + " " + server.getVersion());
        append("Кількість осіб", "%d/%d", Bukkit.getOnlinePlayers().size(), server.getMaxPlayers());
        append("Джерело класу сервера", server.getClass().getProtectionDomain().getCodeSource().getLocation());

        DataReport onlineMode = new DataReport("Online режим");
        onlineMode.append("увімкнено?", server.getOnlineMode());
        if (PaperLib.isSpigot()) {
            onlineMode.append("BungeeCord підтримка?", Bukkit.spigot().getConfig().getBoolean("settings.bungeecord", false));
        }
        if (PaperLib.isPaper()) {
            onlineMode.append("Velocity підтримка?", Bukkit.spigot().getPaperConfig().getBoolean("proxies.velocity.enabled", false));
        }
        append(onlineMode.getTitle(), onlineMode);

        DataReport spawning = new DataReport("Спавн");
        spawning.append("Обмеження появи в оточенні", server.getAmbientSpawnLimit());
        spawning.append("Обмеження на появу тварин", server.getAnimalSpawnLimit());
        spawning.append("Обмеження на появу монстрів", server.getMonsterSpawnLimit());
        spawning.append("Тіків на спавн тварини", server.getTicksPerAnimalSpawns());
        spawning.append("Тіків на спавн монстра", server.getTicksPerMonsterSpawns());
        append(spawning.getTitle(), spawning);

        DataReport config = new DataReport("Конфігурація");
        config.append("Пекло увімкнено?", server.getAllowNether());
        config.append("Енд увімкнено?", server.getAllowEnd());
        config.append("Генерація структур?", server.getGenerateStructures());
        config.append("Політ дозволено?", server.getAllowFlight());
        config.append("Регулятор підключення", server.getConnectionThrottle());
        config.append("Час бездіяльності", server.getIdleTimeout());
        config.append("Повідомлення про вимкнення", server.getShutdownMessage());
        config.append("Стандартний режим гри", server.getDefaultGameMode());
        config.append("Основний тип світу", server.getWorldType());
        config.append("Дистанція огляду", server.getViewDistance());
        append(config.getTitle(), config);

        DataReport protection = new DataReport("Захист");
        protection.append("Спавн радіус", server.getSpawnRadius());
        append(protection.getTitle(), protection);
    }

}
