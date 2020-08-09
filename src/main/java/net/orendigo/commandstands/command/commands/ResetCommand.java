
/**
 * ResetCommand Class
 */

package net.orendigo.commandstands.command.commands;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

/**
 * @author Orendigo
 */
public class ResetCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public ResetCommand() {
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
        final Location entitytLocation = targetEntity.getLocation();
        entitytLocation.setPitch(0.0f);
        entitytLocation.setYaw(0.0f);
        targetEntity.setArms(true);
        targetEntity.setHeadPose(EulerAngle.ZERO);
        targetEntity.setBodyPose(EulerAngle.ZERO);
        targetEntity.setRightArmPose(EulerAngle.ZERO);
        targetEntity.setLeftArmPose(EulerAngle.ZERO);
        targetEntity.setRightLegPose(EulerAngle.ZERO);
        targetEntity.setRightLegPose(EulerAngle.ZERO);
        targetEntity.teleport(entitytLocation);
    }

    @Override
    public String name() {
        return this.plugin.commandManager.reset;
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
