
/*
 * HelpCommand Class
 */

package net.orendigo.commandstands.command.commands;

import net.orendigo.commandstands.CommandStands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Orendigo
 */
public class HelpCommand extends SubCommand {
    
    private final CommandStands plugin;
    
    public HelpCommand() {
        this.plugin = CommandStands.getInstance();
    }

    @Override
    public void onCommand(final CommandSender sender, final String[] args) {
        if (sender instanceof Player)
            this.onCommand((Player)sender, args);
        else
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.plugin.getMessagesConfig().getString("Messages.prefix") +
                this.plugin.getMessagesConfig().getString("Messages.console-error")
            ));
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 1 || args[1].equalsIgnoreCase("1")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3------ " + this.plugin.getMessagesConfig().getString("Messages.prefix") + " &3--- Page &91&3/3 ---"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as <&9arms&3|&9plate&3|&9small&3|&9gravity&3|&9visible&3> <&9true&3|&9false&3>"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "  &9Example: &3/as &9arms true &3| /as &9gravity false"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "  &9Leave out the true/false to toggle the current state."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as <(&9right&3|&9left&3)(&9arm&3|&9leg&3)|&9head&3|&9body&3> <&9x&3|&9y&3|&9z&3> <&9degree&3>"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "  &9Example: &3/as &9rightarm x 360 &3| /as &9leftleg z 56 &3| /as &9head x 7"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9rotate &3<&9degree&3>" + " &9--- Rotates the entire armor stand."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "  &9Example: &3/as &9rotate 180 &3| /as &9rotate +45 &3| /as &9rotate -2"));
        }
        else if (args[1].equalsIgnoreCase("2")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3------ " + this.plugin.getMessagesConfig().getString("Messages.prefix") + " &3--- Page &92&3/3 ---"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9center" + " &9--- Centers the armor stand on the block it's on."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9reset" + " &9--- Resets all position data of an armor stand."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9move" + " &9--- Picks-up an armor stand, moving it around."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9<shift&3|&9set&3> &3<&9x&3|&9y&3|&9z&3> <&9number&3>"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "  &9Shift will nudge an armor stand from its current position."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "  &9Set will set the coordinate relative to its current position"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "  &9Example: /as &9shift x 0.69302; /as set y 0.234"));
        }
        else if (args[1].equalsIgnoreCase("3")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3------ " + this.plugin.getMessagesConfig().getString("Messages.prefix") + " &3--- Page &93&3/3 ---"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9name &3[<&9tex&3t>]" + " &9--- Leave [<text>] blank to remove the name."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9offhand" + " &9--- Sets/takes an armour stand's offhand item."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9hat" + " &9--- Sets helmet slot to held item."));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3/as &9info" + " &9--- Displays body part rotation info."));
        }
        else
            this.onCommand(player, args);
    }

    @Override
    public String name() {
        return this.plugin.commandManager.help;
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
