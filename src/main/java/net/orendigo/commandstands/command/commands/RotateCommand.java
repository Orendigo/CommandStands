
/**
 * RotateCommand Class
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
public class RotateCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public RotateCommand() {
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
        // checking if appropriate amount of args are given
        if (args.length < 2) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.rotation-error")
            ));
            return;
        }
        final Location entitytLocation = targetEntity.getLocation();
        
        Float rotateAmount = entitytLocation.getYaw();
        
        try { rotateAmount = Float.parseFloat(args[1]); }
        catch (NumberFormatException ex) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.syntax-error")
            ));
            return;
        };
        
        if (args[1].contains("+") || args[1].contains("-"))
            entitytLocation.setYaw(entitytLocation.getYaw() + rotateAmount);
        else
            entitytLocation.setYaw(rotateAmount);
        targetEntity.teleport(entitytLocation);
    }

    @Override
    public String name() {
        return this.plugin.commandManager.rotate;
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
