
/*
 * CommandManager Class
 */

package net.orendigo.commandstands.command;

import java.util.ArrayList;
import java.util.Arrays;

import net.orendigo.commandstands.CommandStands;
import net.orendigo.commandstands.command.commands.CenterCommand;
import net.orendigo.commandstands.command.commands.CloneCommand;
import net.orendigo.commandstands.command.commands.HatCommand;
import net.orendigo.commandstands.command.commands.HelpCommand;
import net.orendigo.commandstands.command.commands.InfoCommand;
import net.orendigo.commandstands.command.commands.MoveCommand;
import net.orendigo.commandstands.command.commands.NameCommand;
import net.orendigo.commandstands.command.commands.OffHandCommand;
import net.orendigo.commandstands.command.commands.ResetCommand;
import net.orendigo.commandstands.command.commands.RotateCommand;
import net.orendigo.commandstands.command.commands.ShiftCommand;
import net.orendigo.commandstands.command.commands.SubCommand;
import net.orendigo.commandstands.command.commands.ToggleCommand;
import net.orendigo.commandstands.utility.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * @author Orendigo
 */
public class CommandManager implements CommandExecutor {
    
    private final ArrayList<SubCommand> commands;
    private final CommandStands plugin;
    public String main;
    public String help;
    public String reset;
    public String center;
    public String hat;
    public String info;
    public String name;
    public String offhand;
    public String shift;
    public String rotate;
    public String move;
    public String clone;
    public String toggle;
    
    public CommandManager() {
        this.commands = new ArrayList<>();
        this.plugin = CommandStands.getInstance();
        this.main = "as";
        this.help = "help";
        this.reset = "reset";
        this.center = "center";
        this.hat = "hat";
        this.info = "info";
        this.name = "name";
        this.offhand = "offhand";
        this.shift = "shift";
        this.rotate = "rotate";
        this.move = "move";
        this.clone = "clone";
        this.toggle = "toggle";
    }
    
    public void setup() {
        this.plugin.getCommand(this.main).setExecutor(this);
        this.commands.add(new HelpCommand());
        this.commands.add(new ResetCommand());
        this.commands.add(new CenterCommand());
        this.commands.add(new HatCommand());
        this.commands.add(new InfoCommand());
        this.commands.add(new NameCommand());
        this.commands.add(new OffHandCommand());
        this.commands.add(new ShiftCommand());
        this.commands.add(new RotateCommand());
        this.commands.add(new MoveCommand());
        this.commands.add(new CloneCommand());
        this.commands.add(new ToggleCommand());
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
        if (command.getName().equalsIgnoreCase(this.main)) {
            // if input command is just main command
            if (args.length == 0) {
                new HelpCommand().onCommand(sender, new String[] {"as", "1"} );
                return true;
            }
            
            final SubCommand target = this.get(args[0]);
            // if sub command is invalid
            if (target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    this.plugin.getMessagesConfig().getString("Messages.invalid-command")
                ));
                return true;
            }
            
            if (!(target instanceof HelpCommand)) {
                final Entity targetEntity = Util.getTarget((Player) sender);
                // ensuring target is an armor stand
                if (!(targetEntity instanceof ArmorStand)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.plugin.getMessagesConfig().getString("Messages.prefix") +
                        this.plugin.getMessagesConfig().getString("Messages.armorstand-error")
                    ));
                    return true;
                }

                // ensuring target is within 3 blocks of player
                if (!Util.withinDistance((Player) sender, (ArmorStand) targetEntity)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.plugin.getMessagesConfig().getString("Messages.prefix") +
                        this.plugin.getMessagesConfig().getString("Messages.distance-error")
                    ));
                    return true;
                }

                // ensuring player can build if WorldGuard is enabled
                if (this.plugin.hasWorldGuard()) if (this.plugin.getWorldGuardManager().canBuild((Player) sender, targetEntity.getLocation()) == false) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.plugin.getMessagesConfig().getString("Messages.prefix") +
                        this.plugin.getMessagesConfig().getString("Messages.worldguard-error")
                    ));
                    return true;
                }
            }
            
            // attempt to run onCommand method from input
            final ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.addAll(Arrays.asList(args));
            arrayList.remove(0);
            try {
                target.onCommand(sender, args);
            }
            catch (Exception e) {
                sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                    this.plugin.getMessagesConfig().getString("Messages.prefix") +
                    this.plugin.getMessagesConfig().getString("Messages.error-occured"))
                );
                e.printStackTrace();
            }
        }
        return true;
    }
    
    private SubCommand get(final String name) {
        for (final SubCommand subCmd : this.commands) {
            if (subCmd.name().equalsIgnoreCase(name)) 
                return subCmd;
            
            for (String alias : subCmd.aliases()) 
                if (name.equalsIgnoreCase(alias)) 
                    return subCmd;
        }
        return null;
    }

    
}
