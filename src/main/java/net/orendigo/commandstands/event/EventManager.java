/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.orendigo.commandstands.event;

import java.util.List;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import static org.bukkit.Bukkit.getServer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

/**
 * @author Orendigo
 */
public class EventManager implements Listener {
    
    private final CommandStands plugin;
    
    public EventManager() { 
        this.plugin = CommandStands.getInstance();
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player player = e.getPlayer();
        if (player.hasMetadata("PickUpStand")) {
            final Entity nearEntity = Util.eventTargetFinder(player);
            if (nearEntity != null) {
                final Location twoInFront = player.getLocation().add(player.getLocation().getDirection().multiply(2)).add(0.0, 1.0, 0.0);
                nearEntity.teleport(twoInFront);
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        final Player player = e.getEntity();
        if (player.hasMetadata("PickUpStand")) {
            final ArmorStand nearEntity = Util.eventTargetFinder(player);
            if (nearEntity != null) {
                this.removeMetaData(player, nearEntity);
            }
        }
    }
    
    @EventHandler
    public void onWorldChange(final PlayerChangedWorldEvent e) {
        final Player player = e.getPlayer();
        if (player.hasMetadata("PickUpStand")) {
            final ArmorStand nearEntity = Util.eventTargetFinder(player);
            if (nearEntity != null) {
                this.removeMetaData(player, nearEntity);
            }
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        if (player.hasMetadata("PickUpStand")) {
            final ArmorStand nearEntity = Util.eventTargetFinder(player);
            if (nearEntity != null) {
                this.removeMetaData(player, nearEntity);
            }
        }
    }
    
    @EventHandler
    public void onFishingEvent(final PlayerFishEvent e) {
        final Player player = e.getPlayer();
        try {
            if (e.getCaught().hasMetadata("PickUpPlayer")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    this.plugin.getMessagesConfig().getString("Messages.move-error")
                ));
                e.setCancelled(true);
            }
            else {
                if (this.plugin.hasWorldGuard()) if (!this.plugin.getWorldGuardManager().canBuild(player, e.getCaught().getLocation())) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.plugin.getMessagesConfig().getString("Messages.prefix") +
                        this.plugin.getMessagesConfig().getString("Messages.worldguard-error")
                    ));
                    e.setCancelled(true);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {
        final Entity eventEntity = e.getEntity();
        if (eventEntity.hasMetadata("PickUpPlayer")) {
            final List<MetadataValue> playerData = (List<MetadataValue>)eventEntity.getMetadata("PickUpPlayer");
            for (final Player player : getServer().getOnlinePlayers()) {
                if (player.getUniqueId().toString().equals(playerData.get(0).asString())) {
                    player.removeMetadata("PickUpStand", this.plugin);
                    eventEntity.removeMetadata("PickUpPlayer", this.plugin);
                    eventEntity.removeMetadata("moveRotation", this.plugin);
                }
            }
        }
    }
    
    public void removeMetaData(final Player player, final ArmorStand targetEntity) {
        final List<MetadataValue> rotationData = (List<MetadataValue>)targetEntity.getMetadata("MoveRotation");
        final Float oldYaw = rotationData.get(0).asFloat();
        final Location newLocation = targetEntity.getLocation();
        newLocation.setYaw((float)oldYaw);
        targetEntity.teleport(newLocation);
        player.removeMetadata("PickUpStand", this.plugin);
        targetEntity.removeMetadata("PickUpPlayer", this.plugin);
        targetEntity.removeMetadata("MoveRotation", this.plugin);
    }
    
}
