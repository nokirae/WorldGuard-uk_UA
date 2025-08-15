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
import org.bukkit.plugin.Plugin;

public class PluginReport extends DataReport {

    public PluginReport() {
        super("Plugins");

        Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();

        append("Кількість плагінів", plugins.length);

        for (Plugin plugin : plugins) {
            DataReport report = new DataReport("Plugin: " + plugin.getName());
            report.append("Увімкнено?", plugin.isEnabled());
            report.append("Повна назва", plugin.getDescription().getFullName());
            report.append("Версія", plugin.getDescription().getVersion());
            report.append("Веб-сторінка", plugin.getDescription().getWebsite());
            report.append("Опис", plugin.getDescription().getDescription());
            report.append("Автори", plugin.getDescription().getAuthors());
            report.append("Завантажено раніше", plugin.getDescription().getLoadBefore());
            report.append("Залежності", plugin.getDescription().getDepend());
            report.append("М'які залежності", plugin.getDescription().getSoftDepend());
            report.append("Директорія", plugin.getDataFolder().getAbsoluteFile());
            report.append("Точка входу", plugin.getDescription().getMain());
            report.append("Клас", plugin.getClass().getName());
            report.append("Джерело класу", plugin.getClass().getProtectionDomain().getCodeSource().getLocation());
            append(report.getTitle(), report);
        }
    }

}
