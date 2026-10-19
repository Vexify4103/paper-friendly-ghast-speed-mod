package com.example.happyghast;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.EntitiesLoadEvent;

/**
 * Applies the configured speed whenever a happy ghast spawns, is loaded from a chunk, or is mounted.
 * Together these cover every ghast, so changing the speed never has to scan every entity in every
 * world, and ghasts that were in unloaded chunks when the speed changed still get the new speed.
 */
public class HappyGhastSpawnListener implements Listener {

    private final HappyGhastSpeedPlugin plugin;

    public HappyGhastSpawnListener(HappyGhastSpeedPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent event) {
        // Apply speed to newly spawned happy ghasts
        plugin.applySpeed(event.getEntity());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        // Ghasts saved in a chunk keep the speed they had; bring them up to the current setting
        for (Entity entity : event.getEntities()) {
            plugin.applySpeed(entity);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityMount(EntityMountEvent event) {
        // The speed only matters while a ghast is ridden, so make sure it is current then
        plugin.applySpeed(event.getMount());
    }
}
