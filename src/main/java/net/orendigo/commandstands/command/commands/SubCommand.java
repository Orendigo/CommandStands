/*
 * This command structure is based off of the structure of RFChairs.
 * https://github.com/RifleDLuffy/RFChairs
 */

package net.orendigo.commandstands.command.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public abstract class SubCommand
{
    public abstract void onCommand(final CommandSender sender, final String[] args);
    
    public abstract void onCommand(final Player player, final String[] args);
    
    public abstract String name();
    
    public abstract String info();
    
    public abstract String[] aliases();
}