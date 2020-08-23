
/**
 * GlowCommand Class
 */

package net.orendigo.commandstands.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.lang.reflect.InvocationTargetException;
import net.orendigo.commandstands.CommandStands;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;


/**
 * @author Orendigo
 */
public class MarkCommand extends SubCommand implements Listener {

    private final CommandStands plugin;
    
    private Multimap<Player, ArmorStand> markMap;
    
    public MarkCommand() {
        this.markMap = ArrayListMultimap.create();
        this.plugin = CommandStands.getInstance();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
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
        
        if (player.hasMetadata("MarkStands")) {
            player.removeMetadata("MarkStands", this.plugin);
            for (ArmorStand targetEntity : markMap.get(player))
                glowStatus(player, targetEntity, "remove");
            this.markMap.removeAll(player);
        }
        else {
            for (Entity targetEntity : player.getNearbyEntities(20, 20, 20)) 
                if (targetEntity instanceof ArmorStand) {       
                    this.markMap.put(player, (ArmorStand) targetEntity);
                    glowStatus(player, (ArmorStand) targetEntity, "set");
                }
            if (this.markMap.get(player).size() > 0) {
                player.setMetadata("MarkStands", new FixedMetadataValue(this.plugin, "CommandStands"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    "&f" + this.markMap.get(player).size() + " &carmor stands marked!"
                ));
            }
            else 
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    "No armor stands have been found nearby."
                ));
        }
    }

    @Override
    public String name() {
        return this.plugin.commandManager.mark;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
        
    // Drkmaster83 : https://www.spigotmc.org/threads/simulating-potion-effect-glowing-with-protocollib.218828/
    private void glowStatus(Player player, ArmorStand targetEntity, String type) {
        PacketContainer packet = this.plugin.protocolLibManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, targetEntity.getEntityId()); //Set packet's entity id
        WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
        watcher.setEntity(targetEntity); //Set the new data watcher's target
        if (type.equals("set"))
            watcher.setObject(0, serializer, (byte) (0x40)); //Set status to glowing, found on protocol page
        else
            if (targetEntity.isVisible())
                watcher.setObject(0, serializer, (byte) (0));
            else if (targetEntity.getFireTicks() > 0)
                watcher.setObject(0, serializer, (byte) (0x01));
            else
                watcher.setObject(0, serializer, (byte) (0x20));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created
        try {
            this.plugin.protocolLibManager.protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {}
    }
    
}
