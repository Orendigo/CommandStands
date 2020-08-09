
/**
 * CloneCommand Class
 */

package net.orendigo.commandstands.command.commands;

import java.util.List;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

/**
 * @author Orendigo
 */
public class CloneCommand extends SubCommand {

    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public CloneCommand() {
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
        if (!player.hasPermission("as.clone")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.permission-error")
            ));
            return;
        }
        if (!targetEntity.hasMetadata("PickUpPlayer")) {
            final ArmorStand clone = (ArmorStand)player.getWorld().spawnEntity(targetEntity.getLocation(), EntityType.ARMOR_STAND);
            clone.setArms(targetEntity.hasArms());
            clone.setBasePlate(targetEntity.hasBasePlate());
            clone.setBodyPose(targetEntity.getBodyPose());
            clone.getEquipment().setBoots(targetEntity.getEquipment().getBoots());
            clone.getEquipment().setChestplate(targetEntity.getEquipment().getChestplate());
            clone.setHeadPose(targetEntity.getHeadPose());
            clone.getEquipment().setHelmet(targetEntity.getEquipment().getHelmet());
            clone.getEquipment().setItemInMainHand(targetEntity.getEquipment().getItemInMainHand());
            clone.getEquipment().setItemInOffHand(targetEntity.getEquipment().getItemInOffHand());
            clone.setLeftArmPose(targetEntity.getLeftArmPose());
            clone.setLeftLegPose(targetEntity.getLeftLegPose());
            clone.getEquipment().setLeggings(targetEntity.getEquipment().getLeggings());
            clone.setRightArmPose(targetEntity.getRightArmPose());
            clone.setRightLegPose(targetEntity.getRightLegPose());
            clone.setSmall(targetEntity.isSmall());
            clone.setVisible(targetEntity.isVisible());
            new MoveCommand().onCommand(player, clone);
            return;
        }
        final List<MetadataValue> pickupID = (List<MetadataValue>)targetEntity.getMetadata("PickUpPlayer");
        if (player.getUniqueId().toString().equals(pickupID.get(0).asString())) {
            new MoveCommand().removeMetaData(player, targetEntity);
            return;
        }
    }

    @Override
    public String name() {
        return this.plugin.commandManager.clone;
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
