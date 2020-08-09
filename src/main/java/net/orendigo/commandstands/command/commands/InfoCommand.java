
/**
 * InfoCommand Class
 */

package net.orendigo.commandstands.command.commands;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

/**
 * @author Orendigo
 */
public class InfoCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public InfoCommand() {
        this.plugin = CommandStands.getInstance();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            targetEntity = Util.getTarget((Player) sender);
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
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3------ " + this.plugin.getMessagesConfig().getString("Messages.prefix") + " &3------"));
        Util.getRotationInfo(player, "Head", targetEntity.getHeadPose());
        Util.getRotationInfo(player, "Body", targetEntity.getBodyPose());
        Util.getRotationInfo(player, "RightArm", targetEntity.getRightArmPose());
        Util.getRotationInfo(player, "LeftArm", targetEntity.getLeftArmPose());
        Util.getRotationInfo(player, "RightLeg", targetEntity.getRightLegPose());
        Util.getRotationInfo(player, "LeftLeg", targetEntity.getLeftLegPose());
    }

    @Override
    public String name() {
        return this.plugin.commandManager.info;
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
