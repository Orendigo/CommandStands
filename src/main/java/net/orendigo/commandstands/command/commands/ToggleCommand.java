
/**
 * ToggleCommand Class
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
public class ToggleCommand extends SubCommand {

    private final CommandStands plugin;
    private ArmorStand targetEntity;
    
    public ToggleCommand() {
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
        Boolean bolVal;
        try { bolVal = Boolean.parseBoolean(args[1]); }
        catch (Exception e) {
            bolVal = null;
        }
        if (args[0].toLowerCase().contains("arm"))
            if (bolVal != null)
                targetEntity.setArms(bolVal);
            else
                targetEntity.setArms(!targetEntity.hasArms());
        else if (args[0].toLowerCase().contains("small") || args[0].toLowerCase().contains("size")) 
            if (bolVal != null)
                targetEntity.setSmall(bolVal);
            else
                targetEntity.setSmall(!targetEntity.isSmall());
        else if (args[0].toLowerCase().contains("base") || args[0].toLowerCase().contains("plate"))
            if (bolVal != null)
                targetEntity.setBasePlate(bolVal);
            else
                targetEntity.setBasePlate(!targetEntity.hasBasePlate());
        else if (args[0].toLowerCase().contains("grav"))
            if (bolVal != null)
                targetEntity.setGravity(bolVal);
            else
                targetEntity.setGravity(!targetEntity.hasGravity());
        else if (args[0].toLowerCase().contains("visible")) 
            if (bolVal != null)
                targetEntity.setVisible(bolVal);
            else 
                targetEntity.setVisible(!targetEntity.isVisible());
        else if (args[0].toLowerCase().equalsIgnoreCase("vulnerable"))
            if (bolVal != null)
                targetEntity.setInvulnerable(!bolVal);
            else
                targetEntity.setInvulnerable(!targetEntity.isInvulnerable());
        else if (args[0].toLowerCase().equalsIgnoreCase("invulnerable")) 
            if (bolVal != null)
                targetEntity.setInvulnerable(bolVal);
            else
                targetEntity.setInvulnerable(!targetEntity.isInvulnerable());
        else if (args[0].toLowerCase().contains("fire")) {
            if (bolVal != null) {
                if (args[1].equalsIgnoreCase("true"))
                    targetEntity.setFireTicks(999999999);
                else if (args[1].equalsIgnoreCase("false"))
                    targetEntity.setFireTicks(0);
                }
            else
                if (targetEntity.getFireTicks() > 0)
                    targetEntity.setFireTicks(0);
                else {
                    targetEntity.setInvulnerable(true);
                    targetEntity.setFireTicks(999999999);
                }
        }
    }

    @Override
    public String name() {
        return this.plugin.commandManager.toggle;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[] {"arms", "small", "size", "base", "plate", "gravity", "grav", "visible", "invulnerable", "vulnerable", "fire"};
    }
    
}
