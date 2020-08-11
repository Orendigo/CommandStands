
/**
 * PartsCommand Class
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
public class PartsCommand extends SubCommand {

    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    private Double modifyValue;
    private Boolean add;
    
    public PartsCommand() {
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
        this.rotateParts(player, targetEntity, args);
    }

    @Override
    public String name() {
        return this.plugin.commandManager.parts;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[] {"rightarm", "rarm", "leftarm", "larm", "rightleg", "rleg", "leftleg", "lleg", "head", "body"};
    }
    
    private void rotateParts(final Player player, final ArmorStand targetEntity, final String[] argsInfo) {
        try {modifyValue = Double.parseDouble(argsInfo[2]);}
        catch(Exception e) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.syntax-error")
            ));
            return;
        }
        add = argsInfo[2].contains("+") || argsInfo[2].contains("-");
        
        if (argsInfo[0].toLowerCase().contains("arm")) {
            if (argsInfo[0].toLowerCase().contains("right")) {
                targetEntity.setRightArmPose(Util.modifyStand(player, targetEntity.getRightArmPose(), modifyValue, argsInfo[1], add));
            }
            if (argsInfo[0].toLowerCase().contains("left")) {
                targetEntity.setLeftArmPose(Util.modifyStand(player, targetEntity.getLeftArmPose(), modifyValue, argsInfo[1], add));
            }
        }
        if (argsInfo[0].toLowerCase().contains("leg")) {
            if (argsInfo[0].toLowerCase().contains("right")) {
                targetEntity.setRightLegPose(Util.modifyStand(player, targetEntity.getRightLegPose(), modifyValue, argsInfo[1], add));
            }
            if (argsInfo[0].toLowerCase().contains("left")) {
                targetEntity.setLeftLegPose(Util.modifyStand(player, targetEntity.getRightLegPose(), modifyValue, argsInfo[1], add));
            }
        }
        if (argsInfo[0].toLowerCase().contains("head")) {
            targetEntity.setHeadPose(Util.modifyStand(player, targetEntity.getHeadPose(), modifyValue, argsInfo[1], add));
        }
        if (argsInfo[0].toLowerCase().contains("body")) {
            targetEntity.setBodyPose(Util.modifyStand(player, targetEntity.getBodyPose(), modifyValue, argsInfo[1], add));
        }
    
    }

}
