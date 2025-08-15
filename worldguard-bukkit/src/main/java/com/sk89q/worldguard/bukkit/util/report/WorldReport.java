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
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WorldReport extends DataReport {

    public WorldReport() {
        super("Worlds");

        List<World> worlds = Bukkit.getServer().getWorlds();

        append("Кількість світів", worlds.size());

        for (World world : worlds) {
            DataReport report = new DataReport("Світ: " + world.getName());
            report.append("UUID", world.getUID());
            report.append("Тип світу", world.getWorldType());
            report.append("Оточення", world.getEnvironment());
            ChunkGenerator generator = world.getGenerator();
            report.append("Генератор чанків", generator != null ? generator.getClass().getName() : "<Default>");

            DataReport spawning = new DataReport("Spawning");
            spawning.append("Тварини?", world.getAllowAnimals());
            spawning.append("Монстри?", world.getAllowMonsters());
            spawning.append("Обмеження появи в оточенні", world.getAmbientSpawnLimit());
            spawning.append("Обмеження появи тварин", world.getAnimalSpawnLimit());
            spawning.append("Обмеження появи монстрів", world.getMonsterSpawnLimit());
            spawning.append("Обмеження появи водних істот", world.getWaterAnimalSpawnLimit());
            report.append(spawning.getTitle(), spawning);

            DataReport config = new DataReport("Конфігурація");
            config.append("Складність", world.getDifficulty());
            config.append("Максимальна висота", world.getMaxHeight());
            config.append("Рівень моря", world.getSeaLevel());
            report.append(config.getTitle(), config);

            DataReport state = new DataReport("Стан");
            state.append("Розміщення спавну", world.getSpawnLocation());
            state.append("Повний час", world.getFullTime());
            state.append("Тривалість погоди", world.getWeatherDuration());
            state.append("Тривалість блискавки", world.getThunderDuration());
            report.append(state.getTitle(), state);

            DataReport protection = new DataReport("Захист");
            protection.append("PVP?", world.getPVP());
            protection.append("Ігрові правила", Arrays.stream(world.getGameRules())
                    .map(name -> name + "=" + world.getGameRuleValue(name))
                    .collect(Collectors.joining(", ")));
            report.append(protection.getTitle(), protection);

            append(report.getTitle(), report);
        }
    }
}
