
/**
 * OffHandCommand Class
 */

package net.orendigo.commandstands.command.commands;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Orendigo
 */
public class OffHandCommand extends SubCommand {
    
    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public OffHandCommand() {
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
        if (targetEntity.getEquipment().getItemInOffHand().getType() == Material.AIR) {
            final ItemStack itemInMainHand = new ItemStack(player.getInventory().getItemInMainHand());
            
            if (player.getInventory().getItemInMainHand().getAmount() > 1)
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            else
                player.getInventory().setItemInMainHand((ItemStack)null);
            
            itemInMainHand.setAmount(1);
            targetEntity.getEquipment().setItemInOffHand(itemInMainHand);
        }
        else {
            player.getInventory().addItem(new ItemStack[] { targetEntity.getEquipment().getItemInOffHand() });
            targetEntity.getEquipment().setItemInOffHand((ItemStack)null);
        }
    }

    @Override
    public String name() {
        return this.plugin.commandManager.offhand;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[]{"off"};
    }

}
