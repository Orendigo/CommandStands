
/**
 * CenterCommand Class
 */

package net.orendigo.commandstands.command.commands;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

/**
 * @author Orendigo
 */
public class CenterCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public CenterCommand() {
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
        final Location newLocation = targetEntity.getLocation();
        newLocation.setX(Math.floor(newLocation.getX()) + 0.4999999999);
        newLocation.setY(Math.floor(newLocation.getY()));
        newLocation.setZ(Math.floor(newLocation.getZ()) + 0.4999999999);
        targetEntity.teleport(newLocation);
    }

    @Override
    public String name() {
        return this.plugin.commandManager.center;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
