
/**
 * MoveCommand Class
 */

package net.orendigo.commandstands.command.commands;

import java.util.List;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 * @author Orendigo
 */
public class MoveCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public MoveCommand() {
        this.plugin = CommandStands.getInstance();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            this.targetEntity = Util.getTarget((Player) sender);
            this.onCommand((Player)sender, args);
        }
        else
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.console-error")
            ));
    }

    @Override
    public void onCommand(Player player, String[] args) {
        final List<MetadataValue> pickupID = (List<MetadataValue>)targetEntity.getMetadata("PickUpPlayer");
        if (!(pickupID.isEmpty())) {
            if (player.getUniqueId().toString().equals(pickupID.get(0).asString())) {
                this.removeMetaData(player, targetEntity);
                return;
            }
            if (!player.getUniqueId().toString().equals(pickupID.get(0).asString())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    this.plugin.getMessagesConfig().getString("Messages.move-error")
                ));
                return;
            }
        }
  
        if (!player.hasMetadata("PickUpStand")) {
            player.setMetadata("PickUpStand", new FixedMetadataValue(this.plugin, "CommandStands"));
            targetEntity.setGravity(false);
            targetEntity.setInvulnerable(true);
            targetEntity.setMetadata("PickUpPlayer", new FixedMetadataValue(this.plugin, player.getUniqueId()));
            targetEntity.setMetadata("MoveRotation", new FixedMetadataValue(this.plugin, targetEntity.getLocation().getYaw()));
        }
    }
    
    public void onCommand(Player player, ArmorStand targetEntity) {
        final List<MetadataValue> pickupID = (List<MetadataValue>)targetEntity.getMetadata("PickUpPlayer");
        if (!(pickupID.isEmpty())) {
            if (player.getUniqueId().toString().equals(pickupID.get(0).asString())) {
                this.removeMetaData(player, targetEntity);
                return;
            }
            if (!player.getUniqueId().toString().equals(pickupID.get(0).asString())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    this.plugin.getMessagesConfig().getString("Messages.move-error")
                ));
                return;
            }
        }
  
        if (!player.hasMetadata("PickUpStand")) {
            player.setMetadata("PickUpStand", new FixedMetadataValue(this.plugin, "CommandStands"));
            targetEntity.setGravity(false);
            targetEntity.setInvulnerable(true);
            targetEntity.setMetadata("PickUpPlayer", new FixedMetadataValue(this.plugin, player.getUniqueId()));
            targetEntity.setMetadata("MoveRotation", new FixedMetadataValue(this.plugin, targetEntity.getLocation().getYaw()));
        }
    }

    @Override
    public String name() {
        return this.plugin.commandManager.move;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
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
