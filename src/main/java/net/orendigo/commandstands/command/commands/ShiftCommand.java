
/**
 * ShiftCommand Class
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
public class ShiftCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public ShiftCommand() {
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
        // instantiating variable for try/catch
        Double coord = 0.0;
        
        // flooring all coordinates if 'set'
        final Location entitytLocation = targetEntity.getLocation();
        if (args[0].equalsIgnoreCase("set")) {
            entitytLocation.setX(Math.floor(entitytLocation.getX()));
            entitytLocation.setY(Math.floor(entitytLocation.getY()));
            entitytLocation.setZ(Math.floor(entitytLocation.getZ()));
        }
        
        // if value is not included in command
        if (args.length <= 2) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.axis-error")
            ));
            return;
        }
        
        // attmepting to parse as Double
        try { coord = Double.parseDouble(args[2]); }
        catch (NumberFormatException ex) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.syntax-error")
            ));
            return;
        }
        
        // prevent moving more than 1.5 blocks
        if (coord > 1.5) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.shift-error")
            ));
            return;
        }
        
        final String s = args[1];
        switch (s) {
            case "x": {
                entitytLocation.add(coord, 0.0, 0.0);
                break;
            }
            case "y": {
                entitytLocation.add(0.0, coord, 0.0);
                break;
            }
            case "z": {
                entitytLocation.add(0.0, 0.0, coord);
                break;
            }
            default: {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    this.plugin.getMessagesConfig().getString("Messages.syntax-error")
                ));
                return;
            }
        }
        targetEntity.teleport(entitytLocation);
    }

    @Override
    public String name() {
        return this.plugin.commandManager.shift;
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
