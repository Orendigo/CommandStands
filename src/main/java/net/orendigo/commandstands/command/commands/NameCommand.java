
/**
 * NameCommand Class
 */

package net.orendigo.commandstands.command.commands;

import java.util.Arrays;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

/**
 * @author Orendigo
 */
public class NameCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public NameCommand() {
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
        if (args.length == 1)
            targetEntity.setCustomNameVisible(false);
        else {
            targetEntity.setCustomNameVisible(true);
            targetEntity.setCustomName(ChatColor.translateAlternateColorCodes('&', String.join(" ", Arrays.copyOfRange(args, 1, args.length))));
        }
    }

    @Override
    public String name() {
        return this.plugin.commandManager.name;
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
